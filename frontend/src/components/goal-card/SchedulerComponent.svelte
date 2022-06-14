<script lang="ts">
    import dayjs from 'dayjs';
    import Icon from "@iconify/svelte";
    import SchedulerModal from "../modals/SchedulerModal.svelte";
    import Goal from "../../data/models/Goal";
    import InlineCalendar from "../date-picker/InlineCalendar.svelte";
    import {GOALS} from "../../data/storage/storage";

    export let goalId: number;

    const goal: Goal = $GOALS.find((it) => it.id === goalId);

    const daysName = (dayNum) => {
        let days = ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'];
        return days[dayNum];
    }
    const dates = {
        today: new Date(),
        todayNum: dayjs().format("DD"),
        tomorrow: dayjs().add(1, 'day').toDate(),
        weekends: dayjs().set('day', 6).toDate(),
        nextWeek: dayjs().add(7, 'day').toDate()
    }

    const updateGoals = () => {
        const goals = $GOALS;
        goals[goals.indexOf(goal)] = goal;
        $GOALS = goals;
    }

    $:{
        if (goal) {
            updateGoals();
        }
    }

</script>


<SchedulerModal>
    <div class="scheduler-title" slot="header">
        <span>{dayjs(goal.deadline).format('DD dd HH:mm')}</span>
    </div>
    <div class="scheduler-suggestion" slot="content">
        <button class="scheduler-suggestion-item" on:click={() => goal.deadline = dates.today}>
                        <span class="scheduler-suggestion-item-icon">
                            <Icon icon="bi:calendar" style="width: 18px; height: 18px; color: #058527;"/>
                            <span class="icon-badge">
                                <span>{dates.todayNum}</span>
                            </span>
                        </span>
            <span class="scheduler-suggestion-item-label">Сегодня</span>
            <span class="scheduler-suggestion-item-weekend">{daysName(dayjs().day())}</span>
        </button>
        <button class="scheduler-suggestion-item" on:click={() => goal.deadline = dates.tomorrow}>
                        <span class="scheduler-suggestion-item-icon">
                            <Icon icon="bi:sun" style="width: 18px; height: 18px; color: #ad6200;"/>
                        </span>
            <span class="scheduler-suggestion-item-label">Завтра</span>
            <span class="scheduler-suggestion-item-weekend">{daysName(dayjs().add(1, 'day').day())}</span>
        </button>
        <button class="scheduler-suggestion-item" on:click={() => goal.deadline = dates.weekends}>
                        <span class="scheduler-suggestion-item-icon">
                            <Icon icon="mdi:sofa-outline" style="width: 18px; height: 18px; color: #246fe0;"/>
                        </span>
            <span class="scheduler-suggestion-item-label">На выходных</span>
            <span class="scheduler-suggestion-item-weekend">Сб</span>
        </button>
        <button class="scheduler-suggestion-item" on:click={() => goal.deadline = dates.nextWeek}>
                        <span class="scheduler-suggestion-item-icon">
                            <Icon icon="gg:calendar-next" style="width: 18px; height: 18px; color: #692fc2;"/>
                        </span>
            <span class="scheduler-suggestion-item-label">След.неделя</span>
            <span class="scheduler-suggestion-item-weekend">{daysName(dayjs().add(1, 'week').day())}</span>
        </button>
    </div>
    <div class="scheduler-date-picker" slot="footer">
        <InlineCalendar bind:value={goal.deadline} withTime/>
    </div>
</SchedulerModal>


<style>

    button {
        margin: 0;
    }

    .scheduler-title {
        display: flex;
        align-items: center;
        justify-content: flex-start;

        padding: 8px 10px;;
    }

    .scheduler-suggestion {
        display: flex;
        flex-direction: column;

        padding: 4px 0;

        color: var(--cds-text-01);
    }

    .scheduler-suggestion-item {
        border: 0;
        background: none;
        display: flex;
        flex-direction: row;
        align-items: center;

        padding: 4px 10px;
        line-height: 24px;
        outline: none;

        color: var(--cds-text-01);

        width: 100%;
    }

    .scheduler-suggestion-item:hover {
        background: var(--cds-hover-ui);
    }

    .scheduler-suggestion-item-icon {
        display: flex;
        position: relative;
        margin-right: 10px;
        color: var(--cds-icon-01);
    }

    .icon-badge {
        width: 18px;
        height: 18px;

        position: absolute;
        top: 50%;
        left: 50%;

        transform: translate(-50%, -50%);

        color: #058527;
        font-size: 12px;
        font-weight: 400;

        display: flex;
        align-items: center;
        justify-content: center;
    }

    .icon-badge span {
        width: 100%;
        height: 100%;
        transform: translateY(-1px);
    }

    .scheduler-suggestion-item-label {
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
        margin-right: 4px;

        font-size: 13px;
        font-weight: 400;
    }

    .scheduler-suggestion-item-weekend {
        margin-left: auto;
        color: var(--cds-text-02);
    }

    .scheduler-date-picker {
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .scheduler-action input {
        line-height: 22px;
        padding: 0 4px;
        margin: 0;
        border-radius: 3px;
        border: 1px solid #ccc;
        outline: none;
        box-sizing: border-box;
    }

</style>