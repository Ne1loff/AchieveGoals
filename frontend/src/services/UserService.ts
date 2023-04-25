import type Request from "./Request";
import type User from "../data/models/User";
import {USER} from "../data/storage/storage";
import UserMapper from "../mapper/UserMapper";
import type {UserDto} from "../data/dto/Dtos";

export default class UserService {
    private request: Request;
    private mapper: UserMapper

    constructor(request: Request) {
        this.request = request;
        this.mapper = new UserMapper();
    }

    async getCurrentUser(): Promise<User> {
        return this.request.get<UserDto>('api/user/')
            .then((apiResponse) => {
                if (!apiResponse.data) throw Error("Empty request");

                const user = this.mapper.toEntity(apiResponse.data);
                USER.set(user);
                return user;
            });
    }
}