import {jsonProperty, Serializable} from "ts-serializable";

export default class Country extends Serializable{

    @jsonProperty(Number)
    id: number = 0;

    @jsonProperty(String)
    name: string = '';
}