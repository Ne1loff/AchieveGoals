import {base, hrefs} from "../resources/config";
import {DataType} from "../data/enums/_enums";
import ApiResponse from "../data/api/ApiResponse";
import type ApiError from "../data/api/ApiError";
import {navigate} from "svelte-routing";
import {hasProperty} from "../utils/objects";

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

interface RequestProps {
    [k: PropertyKey]: string;
}

export default class Request {

    private static generatePropsString(props: RequestProps): string {
        let propsStr = '?';
        for (let key in props) {
            if (hasProperty(props, key))
                propsStr += `${key}=${props[key]}&`;
        }
        return propsStr.slice(0, -1);
    }

    private async doRequest<T>(url: string, method: Method, requestData?: RequestData<T>): Promise<ApiResponse<T>> {
        let request;

        if (method === Method.GET) {
            request = fetch(`${base}/${url}`)
                .catch((reason: TypeError) => {
                    throw new ApiResponse(0, null, {
                        status: 503,
                        error: 'Service Unavailable',
                        message: reason.message,
                        timestamp: new Date()
                    } as ApiError);
                });
        } else {
            if (!requestData) throw Error('Request data is required for methods != GET.')

            request = fetch(`${base}/${url}`, {
                method: method,
                headers: {
                    "Content-Type": requestData.dataType
                },
                body: requestData.data ? requestData.dataType === DataType.JSON ?
                    JSON.stringify(requestData.data) : (requestData.data as any) : undefined
            }).catch((reason: TypeError) => {
                throw new ApiResponse(503, null, {
                    status: 503,
                    error: 'Service Unavailable',
                    message: reason.message,
                    timestamp: new Date()
                } as ApiError);
            });
        }

        return request.then(async (response: Response) => {

            let data;
            try {
                data = await response.json();
            } catch (e: any) {
                data = undefined;
            }

            if (response.ok)
                return new ApiResponse(response.status, data as T);
            else if (response.status === 403 || response.status === 401) {
                navigate(hrefs.login);
                throw new ApiResponse(response.status, null, data as ApiError)
            } else
                throw new ApiResponse(response.status, null, data as ApiError)
        });
    }

    async get<T>(url: string, props?: RequestProps): Promise<ApiResponse<T>> {

        if (props) url += Request.generatePropsString(props);

        return this.doRequest<T>(url, Method.GET);
    }

    async post<T>(url: string, data: any, dataType: DataType, props?: RequestProps): Promise<ApiResponse<T>> {

        if (props) url += Request.generatePropsString(props);

        return this.doRequest<T>(url, Method.POST, new RequestData<T>(dataType, data));
    }

    async put<T>(url: string, data: any, dataType: DataType, props?: RequestProps): Promise<ApiResponse<T>> {

        if (props) url += Request.generatePropsString(props);

        return this.doRequest<T>(url, Method.PUT, new RequestData<T>(dataType, data));
    }

    async delete<T>(url: string, data: any, dataType: DataType, props?: RequestProps): Promise<ApiResponse<T>> {

        if (props) url += Request.generatePropsString(props);

        return this.doRequest<T>(url, Method.DELETE, new RequestData<T>(dataType, data));
    }
}