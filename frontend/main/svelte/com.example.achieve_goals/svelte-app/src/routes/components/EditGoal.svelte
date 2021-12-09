<script lang="ts">
    import SetGoalWindow from "../ModalWindows/SetGoalWindow.svelte";
    import TextAreaViaAutosize from "./TextAreaViaAutosize.svelte";
    import Icon from "@iconify/svelte";

    import {createEventDispatcher} from 'svelte';
    import dayjs from "dayjs";

    export let showSetGoalWindow;
    export let showScheduler;
    export let goal;
    export let bounding;
    export let create;

    const dispatch = createEventDispatcher();

    const updateGoal = () => {
      dispatch('update', goal)
    }
    const clearGoal = () => {
        dispatch('clear')
    }
    const getGoals = () => {
      dispatch('getGoals')
    }
    const setGoal = () => {
        let regx = new RegExp(' p[1-4]', 'gm');
        let match = regx.exec(goal.title);
        goal.priority = match ? match[0].substr(2, 1) : goal.priority ? goal.priority : 4;
        goal.title = goal.title.slice(0, match ? match.index : goal.title.length);
        if (goal.deadlineTime === '') goal.deadlineTime = '23:59'
        goal.deadline = dayjs(goal.deadline)
            .set('h', Number(goal.deadlineTime.substr(0, 2)))
            .set('m', Number(goal.deadlineTime.substr(3, 2)))
            .toDate();

        showSetGoalWindow = false;

        let rest;
        if (create) {
            rest = goal.gid ? '/api/goals/sub-goals' : '/api/goals/';
        } else {
            updateGoal()
            clearGoal()
            return
        }

        fetch(rest, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(goal, (key, value) => key === 'deadlineTime' ? undefined : value)
        }).then(response => {
            if (response.status !== 201) alert('Goal doesn\'t set');
            console.log(response)
            getGoals();
        })

        clearGoal();
    }

</script>

<style>

    .set-goal-window-main {
        width: 100%;
        background: white;
        border-radius: 10px;
        margin-bottom: 16px;
    }

    .set-goal-window-main-inner {
        max-height: 200px;
        margin-bottom: 10px;
    }

    .set-goal-window-input {
        margin: 0 0 8px 0;
        padding: 0;
        width: 100%;
        border: none;
    }

    .set-goal-window-input:focus {
        outline: none;
    }

    .set-goal-window-buttons {
        display: flex;
    }

    .calendar_button {
        background: #fff;
        font-size: 1.2em;
        cursor: pointer;

        height: 28px;

        display: flex;
        flex-shrink: 0;
        align-items: center;
        color: #555;

        border: 1px solid #ccc;
        border-radius: 5px;

        padding: 0 8px;
    }

    .calendar_button:hover {
        background: #eee;
    }

    :global(.calendar_button_icon) {
        width: 16px;
        height: 16px;
    }

    .calendar_button_span {
        margin: 0 8px;
    }

    .set_goal_button {
        margin: 12px 0 0 12px;
        background: #db4c3f;
        border: 1px solid rgba(0, 0, 0, .1);

        line-height: 18px;
        padding: 6px 10px;
        height: 32px;

        font-weight: 500;
        font-size: 14px;
        border-radius: 5px;
        box-sizing: border-box;

        color: white;
    }

    .set_goal_button:disabled {
        opacity: 0.4;
        cursor: not-allowed;
    }

</style>

<SetGoalWindow on:close="{() => {showSetGoalWindow = false; clearGoal(); getGoals(); create = true;}}"
               bind:anotherModal={showScheduler}>
    <div class="set-goal-window-main">
        <div class="set-goal-window-main-inner">
            <input class="set-goal-window-input" type="text" placeholder="Цель p1"
                   bind:value={goal.title}/>
            <TextAreaViaAutosize bind:value={goal.description}
                                 placeholder="Описание"
                                 minRows="2"
                                 maxRows="64"/>
        </div>
        <div class="set-goal-window-buttons">
            <button class="calendar_button" type="button" bind:this={bounding}
                    on:click={() => showScheduler = true}>
                <Icon class="calendar_button_icon" icon="bi:calendar-week"/>
                <span class="calendar_button_span">{dayjs(goal.deadline).format('DD dd ') + goal.deadlineTime}</span>
            </button>
        </div>
    </div>
    <button class="set_goal_button" slot="button" disabled={goal.title === ''} on:click={setGoal}>
        {create ? 'Поставить цель' : 'Обновить цель'}
    </button>
</SetGoalWindow>