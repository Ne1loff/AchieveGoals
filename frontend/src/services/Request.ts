import {base} from "../resources/config";
import {DataType} from "../data/enums/_enums";
import ApiResponse from "../data/api/ApiResponse";
import type ApiError from "../data/api/ApiError";

enum Method {
    GET = 'GET',
    POST = 'POST',
    PUT = 'PUT',
    DELETE = 'DELETE'
}

class RequestData<T> {
    dataType: DataType;
    data: T;

    constructor(dataType: DataType, data: T) {
        this.dataType = dataType;
        this.data = data;
    }
}

export default class Request {
    private static INSTANCE: Request;

    private constructor() {
    };

    static getInstance = () => this.INSTANCE ?? new Request();

    private static generatePropsString(props: {}): string {
        let propsStr = '?';
        for (let key in props) {
            propsStr += `${key}=${props[key]}&`;
        }
        return propsStr.slice(0, -1);
    }

    private async doRequest<T>(url: string, method: Method, requestData?: RequestData<T>): Promise<ApiResponse<T>> {
        let request;

        if (method === Method.GET) {
            request = fetch(`${base}/${url}`)
                .catch((reason: TypeError) => {
                    throw new ApiResponse(0, {
                        status: 503,
                        error: 'Service Unavailable',
                        message: reason.message,
                        timestamp: new Date()
                    } as ApiError);
                });
        } else {
            request = fetch(`${base}/${url}`, {
                method: method,
                headers: {
                    "Content-Type": requestData.dataType
                },
                body: requestData.dataType === DataType.JSON ?
                    JSON.stringify(requestData.data) : (requestData.data as any)
            }).catch((reason: TypeError) => {
                throw new ApiResponse(0, {
                    status: 503,
                    error: 'Service Unavailable',
                    message: reason.message,
                    timestamp: new Date()
                } as ApiError);
            });
        }

        return request.then(async (response: Response) => {
            const data = await response.json();
            if (response.ok)
                return new ApiResponse(response.status, data as T);
            else
                throw new ApiResponse(response.status, data as ApiError)
        });
    }

    async get<T>(url: string, props: {} | null = null): Promise<ApiResponse<T>> {
        if (props !== null) {
            url += Request.generatePropsString(props);
        }
        return this.doRequest<T>(url, Method.GET);
    }

    async post<T>(url: string, props: {} | null = null, data: any, dataType: DataType): Promise<ApiResponse<T>> {
        if (props !== null) {
            url += Request.generatePropsString(props);
        }
        return this.doRequest<T>(url, Method.POST, new RequestData<T>(dataType, data));
    }

    async put<T>(url: string, props: {} | null = null, data: any, dataType: DataType): Promise<ApiResponse<T>> {
        if (props !== null) {
            url += Request.generatePropsString(props);
        }
        return this.doRequest<T>(url, Method.PUT, new RequestData<T>(dataType, data));
    }

    async delete<T>(url: string, props: {} | null = null, data: any, dataType: DataType): Promise<ApiResponse<T>> {
        if (props !== null) {
            url += Request.generatePropsString(props);
        }
        return this.doRequest<T>(url, Method.DELETE, new RequestData<T>(dataType, data));
    }
}