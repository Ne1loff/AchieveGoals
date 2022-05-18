import GoalDto from "../data/dto/GoalDto";
import Goal from "../data/models/Goal";

class GoalMapper {

    fromDto(goalDto: GoalDto): Goal {
        const goal = new Goal();
        Object.keys(goalDto)
            .forEach(key => {
                goal[key] = goalDto[key];
            });
        return goal;
    }

    toDto(goals: Goal): GoalDto {
        const dto = new GoalDto();
        Object.keys(dto)
            .forEach(key => {
                dto[key] = goals[key];
            });
        return dto;
    }

    fromDtos(dtos: GoalDto[]): Goal[] {
        const goals: Goal[] = [];
        dtos.forEach(dto => {
            const goal = new Goal();
            Object.keys(dto)
                .forEach(key => {
                    goal[key] = dto[key];
                });
            goals.push(goal);
        });
        return goals;
    }

    toDtos(goals: Goal[]): GoalDto[] {
        const dtos: GoalDto[] = [];
        goals.forEach(goal => {
            const dto = new GoalDto();
            Object.keys(new GoalDto())
                .forEach(key => {
                    dto[key] = goal[key];
                });
            dtos.push(dto);
        });
        return dtos;
    }
}

export default GoalMapper;