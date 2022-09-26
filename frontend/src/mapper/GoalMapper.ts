import {GoalDto} from "../data/dto/Dtos";
import Goal from "../data/models/Goal";
import {getProperty, hasProperty, setProperty} from "../utils/objects";

class GoalMapper {

    toEntity(goalDto: GoalDto): Goal {

        const goal = new Goal();
        const keys = Object.keys(goal) as Array<keyof Goal>;

        keys.forEach((key) => {
            if (hasProperty(goalDto, key)) {
                let value = getProperty(goalDto, key);
                if (goal[key] instanceof Date && typeof value === 'string')
                    value = new Date(value);
                setProperty(goal, key, value);
            }
        })

        return goal;
    }

    toDto(goal: Goal): GoalDto {

        const goalDto = new GoalDto();
        const keys = Object.keys(goalDto) as Array<keyof GoalDto>;

        keys.forEach((key) => {
            if (hasProperty(goal, key)) {
                setProperty(goalDto, key, getProperty(goal, key));
            }
        });

        return goalDto;
    }

    toEntities(goalDtos: GoalDto[]): Goal[] {
        const goals: Goal[] = [];

        goalDtos.forEach((it) => {
            goals.push(this.toEntity(it));
        });

        return goals;
    }

    toDtos(goals: Goal[]): GoalDto[] {
        const dtos: GoalDto[] = [];

        goals.forEach((it) => {
            dtos.push(this.toDto(it));
        });

        return dtos;
    }
}

export default GoalMapper;