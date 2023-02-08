import {TaskDto} from "../data/dto/Dtos";
import Task from "../data/models/Task";
import {AbstractMapper} from "./Mapper";
import {TASK_LABELS} from "../data/storage/storage";
import type TaskLabel from "../data/models/TaskLabel";


class TaskMapper extends AbstractMapper<Task, TaskDto> {

    toDto(entity: Task): TaskDto {
        return this._toDto(entity, new TaskDto(), [
            {
                key: "labels",
                mapping: <T2 extends any>(value: Task): T2 => value.labels.map(it => it.id) as T2
            },
            {
                key: "createdAt",
                mapping: <T2 extends any>(value: Task): T2 => new Date(value.createdAt) as T2
            },
            {
                key: "updatedAt",
                mapping: <T2 extends any>(value: Task): T2 => new Date(value.createdAt) as T2
            },
            {
                key: "deadline",
                mapping: <T2 extends any>(value: Task): T2 => new Date(value.createdAt) as T2
            }
        ]);
    }

    toEntity(dto: TaskDto,): Task {
        return this._toEntity(dto, new Task(), [
            {
                key: "labels",
                mapping: <T2 extends any>(value: TaskDto): T2 => value.labels.map((id) => {
                    let labels: TaskLabel[] = [];
                    const unsubscribe = TASK_LABELS.subscribe((value) => labels = value);
                    unsubscribe();
                    return labels.find(it => it.id === id);
                }) as T2
            },
            {
                key: "createdAt",
                mapping: <T2 extends any>(value: TaskDto): T2 => new Date(value.createdAt) as T2
            },
            {
                key: "updatedAt",
                mapping: <T2 extends any>(value: TaskDto): T2 => new Date(value.createdAt) as T2
            },
            {
                key: "deadline",
                mapping: <T2 extends any>(value: TaskDto): T2 => new Date(value.createdAt) as T2
            }
        ]);
    }

    toEntities(taskDtos: TaskDto[]): Task[] {
        const tasks: Task[] = [];

        taskDtos.forEach((it) => {
            tasks.push(this.toEntity(it));
        });

        return tasks;
    }

    toDtos(tasks: Task[]): TaskDto[] {
        const dtos: TaskDto[] = [];

        tasks.forEach((it) => {
            dtos.push(this.toDto(it));
        });

        return dtos;
    }
}

export default TaskMapper;