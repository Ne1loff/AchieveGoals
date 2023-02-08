import type TaskLabel from "./TaskLabel";

class Subtasks {
    total?: number;
    completed?: number;
}

interface Parent {
    id: number,
    title: string
}

type Priority = 1 | 2 | 3 | 4;

class Task {

    id: number = 0;
    title: string = '';
    description?: string = '';
    isDone: boolean = false;
    gid?: number = undefined;
    subtasks: Subtasks = new Subtasks();
    priority: Priority = 4;
    labels: TaskLabel[] = [];
    createdAt: Date = new Date();
    updatedAt: Date = new Date();
    deadline: Date = new Date();


    clone() {
        const task = new Task();
        task.id = this.id;
        task.title = this.title;
        task.description = this.description;
        task.isDone = this.isDone;
        task.gid = this.gid;

        task.subtasks = new Subtasks();
        if (this.subtasks != null) {
            task.subtasks.total = this.subtasks.total;
            task.subtasks.completed = this.subtasks.completed;
        }

        task.priority = this.priority;

        task.labels = Object.assign(task.labels, this.labels);

        task.createdAt = new Date(this.createdAt);
        task.updatedAt = new Date(this.updatedAt);
        task.deadline = new Date(this.deadline);
        return task;
    }
}

export default Task;
export {Subtasks};
export type {Parent, Priority};
