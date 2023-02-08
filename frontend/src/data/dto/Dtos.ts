class TaskDto {
    id: number = 0;
    title: string = '';
    description: string = '';
    isDone: boolean = false;
    gid?: number;
    priority: number = 0;
    labels: number[] = [];
    createdAt: Date = new Date();
    updatedAt: Date = new Date();
    deadline: Date = new Date();
}

class UserDto {
    id: number = 0;
    username: string = '';
    name: string = '';
    surname: string = '';
    male: boolean = false;
    email: string = '';
    country: number = 0;
    avatar: string = '';
}

export {TaskDto, UserDto};