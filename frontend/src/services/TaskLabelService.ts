import type Request from "./Request";
import type TaskLabel from "../data/models/TaskLabel";
import ApiError from "../data/api/ApiError";
import {TASK_LABELS} from "../data/storage/storage";
import {DataType} from "../data/enums/_enums";
import type Task from "../data/models/Task";
import type {TaskDto} from "../data/dto/Dtos";
import TaskMapper from "../mapper/TaskMapper";

export default class TaskLabelService {
    private request: Request;
    private mapper: TaskMapper;

    constructor(request: Request) {
        this.request = request;
        this.mapper = new TaskMapper();
    }

    async getLabels(): Promise<TaskLabel[]> {
        return this.request.get<TaskLabel[]>('api/labels/')
            .then((response) => {
                const labels = response.data;
                if (!labels) throw new ApiError();

                TASK_LABELS.set(labels);
                return labels;
            })
    }

    async createLabel(label: TaskLabel): Promise<TaskLabel> {
        return this.request.post<TaskLabel>('api/labels/', label, DataType.JSON)
            .then((response) => {
                const createdLabel = response.data;
                if (!createdLabel) throw new ApiError();

                TASK_LABELS.update((it) => {
                    it.push(createdLabel)
                    return it
                })
                return createdLabel;
            })
    }

    async editLabel(label: TaskLabel): Promise<void> {
        return this.request.put<TaskLabel>('api/labels/', label, DataType.JSON)
            .then((response) => {
                const updatedLabel = response.data;
                if (!updatedLabel) throw new ApiError();

                TASK_LABELS.update((labels) => {
                    const index = labels.findIndex((it) => it.id === updatedLabel.id);
                    if (index < 0) return labels;
                    labels[index] = updatedLabel;
                    return labels;
                })
            })
    }

    async deleteLabel(id: number): Promise<void> {
        return this.request.delete<number>(`api/labels/${id}`, undefined, DataType.JSON)
            .then((response) => {
                const deletedId = response.data;
                if (!deletedId) throw new ApiError();

                TASK_LABELS.update((labels) => labels.filter(it => it.id !== deletedId))
            })
    }

    async getTaskByLabel(id: number): Promise<Task[]> {
        return this.request.get<TaskDto[]>(`api/labels/${id}/tasks`)
            .then((response) => {
                if (!response.data) throw new ApiError();
                return this.mapper.toEntities(response.data);
            })
    }
}