class Subtasks {
    total: number;
    completed: number;
}

class Goal {

    id: number = 0;

    title: string = '';

    description?: string = '';

    isDone: boolean = false;

    gid?: number = null;

    subtasks: Subtasks = null;

    priority: 1 | 2 | 3 | 4 = 4;

    createdAt: Date = new Date();

    updatedAt: Date = new Date();

    deadline: Date = new Date();
}

export default Goal;
export {Subtasks};