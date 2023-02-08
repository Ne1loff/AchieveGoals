<script lang="ts">
    import Calendar from "./Calendar.svelte";
    import dayjs from "dayjs";
    import Time from "./time-picker/Time.svelte";
    import Task from "../../data/models/Task";
    import {TASKS} from "../../data/storage/storage";

    export let goalId: number;
    const goal: Task = goalId ? $TASKS.find((it) => it.id === goalId) : undefined;

    export let value: Date = goal ? goal.deadline : new Date();
    export let minDate: Date = dayjs().add(-1, 'day').toDate();
    export let withTime: boolean = false;

    const updateGoals = () => {
        const goals = $TASKS;
        goals[goals.indexOf(goal)] = goal;
        $TASKS = goals;
    }

    $:{
        if (goal) {
            goal.deadline = value;
            updateGoals();
        }
    }

</script>

<style>

    :root {
        --calendar-padding: .3rem;
    }

    .calendar-container {
        width: 100%;
        height: 100%;
        position: relative;
        background-color: var(--cds-field);
        display: flex;
        flex-direction: column;
        justify-content: center;

        padding: var(--calendar-padding);
        border-radius: 16px;
        user-select: none;
    }

</style>

<div class="calendar-container">
    <Calendar bind:value {minDate}/>
    {#if withTime}
        <Time bind:value />
    {/if}
</div>