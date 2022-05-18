import type ApiError from "./ApiError";

export default class ApiResponse<T> {

    status: number;
    data?: T;
    error?: ApiError;

    constructor(status: number, data?: T, error?: ApiError) {
        this.status = status;
        this.data = data;
        this.error = error;
    }
}
