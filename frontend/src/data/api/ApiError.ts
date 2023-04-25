export default class ApiError {

    status?: number;

    error?: string;

    message?: string;

    timestamp?: Date;

    path?: string;

    constructor(message?: string, status?: number, error?: string,  timestamp?: Date, path?: string) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
    }
}