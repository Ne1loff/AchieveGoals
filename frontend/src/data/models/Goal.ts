class Subtasks {
    total: number;
    completed: number;
}

interface Parent {
    id: number,
    title: string
}

type Priority = 1 | 2 | 3 | 4;

class Goal {

    id: number = 0;

    title: string = '';

    description?: string = '';

    isDone: boolean = false;

    gid?: number = null;

    subtasks: Subtasks = new Subtasks();

    priority: Priority = 4;

    createdAt: Date = new Date();

    updatedAt: Date = new Date();

    deadline: Date = new Date();


    clone = () => {
        const goal = new Goal();
        goal.id = this.id;
        goal.title = this.title;
        goal.description = this.description;
        goal.isDone = this.isDone;
        goal.gid = this.gid;

        goal.subtasks = new Subtasks();
        if (this.subtasks != null) {
            goal.subtasks.total = this.subtasks.total;
            goal.subtasks.completed = this.subtasks.completed;
        }

        goal.priority = this.priority;
        goal.createdAt = new Date(this.createdAt);
        goal.updatedAt = new Date(this.updatedAt);
        goal.deadline = new Date(this.deadline);
        return goal;
    }
}

export default Goal;
export {Subtasks};
export type {Parent, Priority};
