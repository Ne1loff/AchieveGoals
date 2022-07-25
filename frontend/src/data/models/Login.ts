import type Registration from "./Registration";

export default class Login {
    login: string = '';
    password: string = '';
    rememberMe: boolean = false;

    fromRegistration(registration: Registration): Login {
        this.login = registration.username;
        this.password = registration.password;
        return this;
    }
}