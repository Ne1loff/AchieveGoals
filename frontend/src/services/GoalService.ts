// @ts-ignore
import Goal from "../data/models/Goal";
import Request from "./Request";

export default class GoalService {
    private static INSTANCE: GoalService;
    private request: Request;

    private constructor() {
        this.request = Request.getInstance();
    }

    static getInstance = () => this.INSTANCE ?? new GoalService();

    async getUserGoals(): Promise<Goal[]> {
        return this.request.get<Goal[]>('api/goals/', null)
            .then((goals) => goals.data as Goal[])
    }
}