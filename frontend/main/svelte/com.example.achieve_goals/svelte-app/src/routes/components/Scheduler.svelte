<script lang="ts">
    import dayjs from 'dayjs';
    import Icon from "@iconify/svelte";
    import {DatePicker, localeFromDateFnsLocale} from 'date-picker-svelte';
    import {ru} from 'date-fns/locale';
    import SchedulerWindow from "../ModalWindows/SchedulerWindow.svelte";

    const daysName = (dayNum) => {
        let days = ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'];
        return days[dayNum];
    }
    const dates = {
        today: new Date(),
        todayNum: dayjs().format("DD"),
        tomorrow: dayjs().add(1, 'day').toDate(),
        weekends: dayjs().set('day', 6).toDate(),
        nextWeek: dayjs().add(1, 'week').toDate()
    }
    const locale = localeFromDateFnsLocale(ru)

    export let goal;
    export let bounding;
    export let isCreate;
    export let fromGoalCard = false;

</script>

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

        width: 100%;
    }

    .scheduler-suggestion-item:hover {
        background: #f1f1f1;
    }

    .scheduler-suggestion-item-icon {
        display: flex;
        position: relative;
        margin-right: 10px;
        color: grey;
    }

    .scheduler-suggestion-item-icon span {
        width: 18px;
        height: 18px;

        position: absolute;
        transform: translateY(-1px);

        color: #058527;
        font-size: 12px;
        font-weight: 400;
    }

    .scheduler-suggestion-item-label {
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
        margin-right: 4px;

        font-size: 13px;
        font-weight: 400;
        color: #202020;
    }

    .scheduler-suggestion-item-weekend {
        margin-left: auto;
        color: grey;
    }

    .scheduler-date-picker {
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .scheduler-action {
        display: flex;
        align-items: center;
        justify-content: space-between;

        padding: 8px 10px;
    }

    .scheduler-action-label {
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
        margin-right: 4px;

        font-size: 14px;
        font-weight: 400;
        color: #202020;
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

<SchedulerWindow on:close bind:bounding bind:fromGoalCard>
    <div class="scheduler-title" slot="header">
        <span>{dayjs(goal.deadline).format('DD dd ') + goal.deadlineTime}</span>
    </div>
    <div class="scheduler-suggestion" slot="suggestion">
        <button class="scheduler-suggestion-item" on:click={() => goal.deadline = dates.today}>
                        <span class="scheduler-suggestion-item-icon">
                            <Icon icon="bi:calendar" style="width: 18px; height: 18px; color: #058527;"/>
                            <span>{dates.todayNum}</span>
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
    <div class="scheduler-date-picker" slot="date">
        <DatePicker bind:value={goal.deadline} {locale}
                    min="{isCreate? dayjs().toDate() : dayjs(goal.deadline).isAfter(dayjs()) ? dayjs().toDate() : goal.deadline}"
                    max="{new Date(new Date().getFullYear() + 250, 11, 31)}"/>
    </div>
    <div class="scheduler-action" slot="time">
        <span class="scheduler-action-label">Время</span>
        <input type="time" bind:value={goal.deadlineTime}/>
    </div>
</SchedulerWindow>