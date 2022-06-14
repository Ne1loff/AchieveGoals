<script lang="ts">
    import Calendar from "./Calendar.svelte";
    import dayjs from "dayjs";
    import Time from "./time-picker/Time.svelte";
    import Goal from "../../data/models/Goal";
    import {GOALS} from "../../data/storage/storage";

    export let goalId: number | undefined = undefined;
    const goal: Goal | undefined = goalId ? $GOALS.find((it) => it.id === goalId) : undefined;

    export let value: Date = goal ? goal!.deadline : new Date();
    export let minDate: Date = dayjs().add(-1, 'day').toDate();
    export let withTime: boolean = false;

    const updateGoals = () => {
        const goals = $GOALS;
        goals[goals.indexOf(goal)] = goal;
        $GOALS = goals;
    }

    $:{
        if (goal) {
            goal!.deadline = value;
            updateGoals();
        }
    }

</script>

<style>

    .calendar-container {
        width: 100%;
        height: 100%;
        position: relative;
        background-color: var(--cds-field);
        display: flex;
        flex-direction: column;
        justify-content: center;

        user-select: none;
    }

</style>

<div class="calendar-container">
    <Calendar bind:value {minDate}/>
    {#if withTime}
        <Time bind:value />
    {/if}
</div>