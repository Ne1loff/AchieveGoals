import type Request from "./Request";
import type User from "../data/models/User";
import {USER} from "../data/storage/storage";

export default class UserService {
    private request: Request;

    constructor(request: Request) {
        this.request = request;
    }

    async getCurrentUser(): Promise<User> {
        return this.request.get<User>('api/user/')
            .then((apiResponse) => {
                const user = apiResponse.data as User;
                USER.set(user);
                return user;
            });
    }
}