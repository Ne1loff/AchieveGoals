export default class User {

    id: number = 0;
    username: string = '';
    name: string = '';
    surname: string = '';
    male: boolean = false;
    email: string = '';
    country: number = 0;
    avatar: string = '';

    clone(): User {
        const user = new User();
        user.id = this.id;
        user.username = this.username;
        user.name = this.name;
        user.surname = this.surname;
        user.male = this.male;
        user.email = this.email;
        user.country = this.country;
        user.avatar = this.avatar;
        return user;
    }
}