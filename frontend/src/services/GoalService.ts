// @ts-ignore
import Goal, {Subtasks} from "../data/models/Goal";
import type Request from "./Request";
import type GoalDto from "../data/dto/GoalDto";
import GoalMapper from "../mapper/GoalMapper";
import {GOALS} from "../data/storage/storage";

export default class GoalService {
    private request: Request;
    private mapper: GoalMapper;

    constructor(request: Request) {
        this.request = request;
        this.mapper = new GoalMapper();
    }

    async getUserGoals(): Promise<Goal[]> {
        return this.request.get<GoalDto[]>('api/goals/', null)
            .then((response) => {
                const goals: Goal[] = this.mapper.fromDtos(response.data);
                goals.forEach((goal) => {
                    const total = goals.filter((it) => it.gid === goal.id);
                    goal.subtasks = new Subtasks();
                    goal.subtasks.total = total.length;
                    goal.subtasks.completed = total.filter(it => it.isDone).length;
                });
                GOALS.set(goals);
                return goals;
            });
    }
}