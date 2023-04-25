import {UserDto} from "../data/dto/Dtos";
import User from "../data/models/User";
import {AbstractMapper} from "./Mapper";


class UserMapper extends AbstractMapper<User, UserDto> {
    toDto(entity: User): UserDto {
        return this._toDto(entity, new UserDto());
    }

    toDtos(entities: User[]): UserDto[] {
        const dtos: UserDto[] = [];

        entities.forEach((it) => {
            dtos.push(this.toDto(it));
        });

        return dtos;
    }

    toEntities(dtos: UserDto[]): User[] {
        const users: User[] = [];

        dtos.forEach((it) => {
            users.push(this.toEntity(it));
        });

        return users;
    }

    toEntity(dto: UserDto): User {
        return this._toEntity(dto, new User());
    }
}

export default UserMapper;