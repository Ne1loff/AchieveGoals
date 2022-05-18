export default class ApiError {

    status?: number = null;

    error?: string = null;

    message?: string = null;

    timestamp?: Date = null;

    path?: string = null;

    constructor(message?: string, status?: number, error?: string,  timestamp?: Date, path?: string) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
    }
}