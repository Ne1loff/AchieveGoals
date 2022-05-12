import type Registration from "./Registration";

export default class Login {
    login: string = '';
    password: string = '';

    fromRegistration(registration: Registration): Login {
        this.login = registration.username;
        this.password = registration.password;
        return this;
    }
}