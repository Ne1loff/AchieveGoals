import type Task from "../data/models/Task";
import {Subtasks} from "../data/models/Task";
import type Request from "./Request";
import type {TaskDto} from "../data/dto/Dtos";
import TaskMapper from "../mapper/TaskMapper";
import {TASKS} from "../data/storage/storage";
import {DataType} from "../data/enums/_enums";
import ApiError from "../data/api/ApiError";

export default class TaskService {
    private request: Request;
    private mapper: TaskMapper;

    constructor(request: Request) {
        this.request = request;
        this.mapper = new TaskMapper();
    }

    async getUserGoals(): Promise<Task[]> {
        return this.request.get<TaskDto[]>('api/tasks/')
            .then((response) => {

                if (!response.data) return [];

                const goals: Task[] = this.mapper.toEntities(response.data);

                goals.forEach((goal) => {
                    const total = goals.filter((it) => it.gid === goal.id);

                    if (!goal.subtasks) goal.subtasks = new Subtasks();

                    goal.subtasks.total = total.length;
                    goal.subtasks.completed = total.filter(it => it.isDone).length;
                });

                TASKS.set(goals);

                return goals;
            });
    }

    async createGoal(data: Task): Promise<Task> {
        return this.request.post<TaskDto>('api/tasks/', data, DataType.JSON)
            .then((response) => {

                if (!response.data) throw new ApiError();

                const task = this.mapper.toEntity(response.data);
                TASKS.update((tasks) => {
                    tasks.push(task);
                    return tasks;
                })
                return task;
            })

    }

    async editGoals(data: Task[]): Promise<Task[]> {
        return this.request.put<TaskDto[]>('api/tasks/', data, DataType.JSON)
            .then((response) => {
                if (!response.data) throw new ApiError();

                const updatedTasks = this.mapper.toEntities(response.data);
                let newTasks: Task[] = [];

                TASKS.update((tasks) => {
                    for (let i = 0; i < tasks.length; i++) {
                        const id = tasks[i].id;
                        const updatedTask = updatedTasks.find(it => it.id === id);
                        if (!updatedTask) continue;

                        tasks[i] = updatedTask;
                    }
                    newTasks = tasks;
                    return tasks;
                });
                return newTasks
            })
    }

    async deleteTask(id: number | string) {
        return this.request.delete<number>(`api/tasks/${id}`, undefined, DataType.JSON)
            .then((response) => {
                if (!response.data) throw new ApiError();

                const deletedTaskId = response.data;
                TASKS.update((goals) => goals.filter(it => it.id !== deletedTaskId))
            })
    }

    async deleteTasks(id: number[] | string[]) {
        return this.request.delete<number[]>(`api/tasks/`, id, DataType.JSON)
            .then((response) => {
                if (!response.data) throw new ApiError();

                const deletedTaskIds = response.data;
                TASKS.update((goals) => {
                    let updatedGoals = goals;
                    for (let i = 0; i < deletedTaskIds.length; i++) {
                        updatedGoals = goals.filter(it => it.id !== deletedTaskIds[i])
                    }
                    return updatedGoals
                })
            })
    }
}