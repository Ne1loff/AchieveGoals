import type ApiError from "./ApiError";

export default class ApiResponse<T> {

    status: number;
    data: T | ApiError;

    constructor(status: number, data: T | ApiError) {
        this.status = status;
        this.data = data;
    }
}
