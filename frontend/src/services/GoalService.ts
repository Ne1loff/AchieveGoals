import type Goal from "../data/models/Goal";
import {Subtasks} from "../data/models/Goal";
import type Request from "./Request";
import type {GoalDto} from "../data/dto/Dtos";
import GoalMapper from "../mapper/GoalMapper";
import {GOALS} from "../data/storage/storage";
import {DataType} from "../data/enums/_enums";
import ApiError from "../data/api/ApiError";

export default class GoalService {
    private request: Request;
    private mapper: GoalMapper;

    constructor(request: Request) {
        this.request = request;
        this.mapper = new GoalMapper();
    }

    async getUserGoals(): Promise<Goal[]> {
        return this.request.get<GoalDto[]>('api/goals/')
            .then((response) => {

                if (!response.data) return [];

                const goals: Goal[] = this.mapper.toEntities(response.data);

                goals.forEach((goal) => {
                    const total = goals.filter((it) => it.gid === goal.id);

                    if (!goal.subtasks) goal.subtasks = new Subtasks();

                    goal.subtasks.total = total.length;
                    goal.subtasks.completed = total.filter(it => it.isDone).length;
                });

                GOALS.set(goals);

                return goals;
            });
    }

    async createGoal(data: Goal): Promise<Goal> {
        return this.request.post<GoalDto>('api/goals/', data, DataType.JSON)
            .then((response) => {

                if (!response.data) throw new ApiError();

                const goal = this.mapper.toEntity(response.data);
                GOALS.update((goals) => {
                    goals.push(goal);
                    return goals;
                })
                return goal;
            })

    }

    async deleteTask(id: number | string) {
        return this.request.delete<void>(`api/goals/${id}`, undefined, DataType.JSON)
            .then(() => GOALS.update((goals) => goals.filter(it => it.id !== id)));
    }
}