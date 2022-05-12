import {jsonProperty, Serializable} from "ts-serializable";

export default class User extends Serializable {

    @jsonProperty(String)
    username: string = '';

    @jsonProperty(String)
    name: string = '';

    @jsonProperty(String)
    surname: string = '';

    @jsonProperty(Boolean)
    male: boolean = false;

    @jsonProperty(String)
    email: string = '';

    @jsonProperty(Number)
    country: number = 0;

    @jsonProperty(String)
    avatar: string = '';
}