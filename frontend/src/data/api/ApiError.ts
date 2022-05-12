import {jsonProperty, Serializable} from "ts-serializable";

export default class ApiError extends Serializable {

    @jsonProperty(Number, null)
    status?: number;

    @jsonProperty(String, null)
    error?: string;

    @jsonProperty(String, null)
    message?: string;

    @jsonProperty(Date, null)
    timestamp?: Date;

    @jsonProperty(String, null)
    path?: string;


    constructor(message?: string, status?: number, error?: string,  timestamp?: Date, path?: string) {
        super();
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
    }
}