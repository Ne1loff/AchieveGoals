import type Request from "./Request";
import type Login from "../data/models/Login";
import type Registration from "../data/models/Registration";
import type UserService from "./UserService";
import {DataType} from "../data/enums/_enums";

export default class AuthenticationService {
    private request: Request;
    private userService: UserService;

    constructor(request: Request, userService: UserService) {
        this.request = request;
        this.userService = userService;
    }

    async logIn(login: Login): Promise<number> {
        return this.request.post<number>('api/login', login, DataType.JSON)
            .then((apiResponse) => {
                this.userService.getCurrentUser();
                return apiResponse.status;
            });
    }

    async signUp(registration: Registration): Promise<number> {
        return this.request.post<number>('api/registration', registration, DataType.JSON)
            .then((apiResponse) => apiResponse.status);
    }

    async logOut(): Promise<number> {
        return this.request.post<number>('api/logout', {}, DataType.JSON)
            .then((apiResponse) => {
                return apiResponse.status;
            });
    }
}