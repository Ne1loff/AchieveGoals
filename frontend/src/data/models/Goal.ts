import {jsonIgnore, jsonProperty, Serializable} from "ts-serializable";

class Subtasks extends Serializable {

    @jsonProperty(Number)
    total: number;

    @jsonProperty(Number)
    completed: number;
}

class Goal extends Serializable {

    @jsonProperty(Number)
    id: number = 0;

    @jsonProperty(String, null)
    title: string = '';

    @jsonProperty(String, null)
    description?: string = '';

    @jsonProperty(Boolean, null)
    isDone: boolean = false;

    @jsonProperty(Number, null)
    gid?: number = null;

    @jsonIgnore()
    @jsonProperty(Subtasks, null)
    subtasks: Subtasks = null;

    @jsonProperty(Number)
    priority: 1 | 2 | 3 | 4 = 4;

    @jsonProperty(Date, null)
    createdAt: Date = null;

    @jsonProperty(Date, null)
    updatedAt: Date = null;

    @jsonProperty(Date, null)
    deadline: Date = null;
}

export default Goal;