import {UserDto} from "../data/dto/Dtos";
import User from "../data/models/User";
import {getProperty, hasProperty, setProperty} from "../utils/objects";


class UserMapper {

    toEntity(userDto: UserDto): User {

        const user = new User();
        const keys = Object.keys(user) as Array<keyof User>;

        keys.forEach((key) => {
            if (hasProperty(userDto, key)) {
                setProperty(user, key, getProperty(userDto, key));
            }
        });

        return user;
    }

    toDto(user: User): UserDto {

        const userDto = new UserDto();
        const keys = Object.keys(userDto) as Array<keyof UserDto>;

        keys.forEach((key) => {
            if (hasProperty(user, key)) {
                setProperty(userDto, key, getProperty(user, key));
            }
        });

        return userDto;
    }

    toEntities(userDtos: UserDto[]): User[] {
        const goals: User[] = [];

        userDtos.forEach((it) => {
            goals.push(this.toEntity(it));
        });

        return goals;
    }

    toDtos(goals: User[]): UserDto[] {
        const dtos: UserDto[] = [];

        goals.forEach((it) => {
            dtos.push(this.toDto(it));
        });

        return dtos;
    }
}

export default UserMapper;