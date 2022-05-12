import Request from "./Request";
import type User from "../data/models/User";

export default class UserService {
    private static INSTANCE: UserService;
    private request: Request;

    private constructor() {
        this.request = Request.getInstance();
    }

    static getInstance = () => this.INSTANCE ?? new UserService();

    async getCurrentUser(): Promise<User> {
        return this.request.get<User>('api/user/')
            .then((apiResponse) => apiResponse.data as User);
    }
}