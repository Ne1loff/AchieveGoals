<script lang="ts">
    import GoalInfoWindow from "../ModalWindows/GoalInfoWindow.svelte";
    import TextAreaViaAutosize from "./TextAreaViaAutosize.svelte";
    import Scheduler from "./Scheduler.svelte";
    import Goal from "./Goal.svelte"
    import Icon from "@iconify/svelte";
    import dayjs from "dayjs";
    import {navigate} from "svelte-routing";
    import {createEventDispatcher} from 'svelte';

    const dispatch = createEventDispatcher();

    export let goal;
    export let showInfo;
    export let userGoals;

    let goalCopy = JSON.parse(JSON.stringify(goal))

    const priorityColors = {
        1: {
            icon: '#de4c4a',
            background: '#faeceb'
        },
        2: {
            icon: '#f49c18',
            background: '#fdf3e6'
        },
        3: {
            icon: '#3077e1',
            background: '#e9f1fc'
        },
        4: {
            icon: '#808080',
            background: 'none'
        }
    }

    let parentGoal;
    let subGoals;
    let bounding;
    let hoverCheckBtn = false;
    let edit = false;
    let showSub = false;
    let showScheduler = false;
    let activeBtn = false;

    const done = () => {
        goal.isDone = !goal.isDone;
        dispatch('done', goal);
        getChildrens();
    }
    const childDone = (g) => {
        dispatch('done', g);
        getChildrens();
    }
    const addSub = () => {
        dispatch('createSub', goal.id)
    }
    const update = () => {
        dispatch('update', goal)
    }
    const getParent = () => {
        if (goal.gid !== null && goal.gid !== undefined)
            parentGoal = userGoals.find(g => g.id === goal.gid)
        else parentGoal = null
    }
    const getChildrens = () => {
        subGoals = userGoals.filter(g => g.gid === goal.id)
        showSub = true;
    }
    const updateInfo = () => {
        if (subGoals) {
            goal.subtasks.total = subGoals.filter(g => g.gid === goal.id).length
            goal.subtasks.completed = subGoals.filter(g => g.gid === goal.id && g.isDone).length
        }
    }
    const save = () => {
        edit = false;
        update();
    }
    const cancel = () => {
        edit = false;
        goal = JSON.parse(JSON.stringify(goalCopy))
    }
    const close = () => {
        showInfo = false;
    }

    $: if (goal || userGoals) {
        getChildrens();
        getParent();
        updateInfo();
    }

</script>

<style>
    .goal-info-parents {
        display: flex;
        align-items: center;
        justify-content: flex-start;
    }

    .root-parent {
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        cursor: pointer;

        color: #202020;
    }

    .root-parent-icon {
        margin-right: 8px;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .parent-separator {
        padding: 0 8px;
        cursor: default;
    }

    .parent-title {
        cursor: pointer;
    }

    .goal-info-about {
        margin: 8px 0;

        width: 100%;
        display: flex;
        flex-direction: row;
    }

    .container-checkbox {
        height: 16px;
        width: 16px;
        cursor: pointer;
        background: #faeceb;
        margin-right: 8px;
        z-index: 1;

        display: flex;
        align-items: center;
        justify-content: center;

        border-radius: 50%;
        border: 2px solid #fff;
    }

    .set-goal-window {
        width: 100%;
        background: white;
        border-radius: 10px;
        margin-bottom: 16px;
    }

    .save-btn, .cancel-btn {
        border-radius: 8px;
        cursor: pointer;
    }

    .save-btn {
        background: #d00;
        border-color: #d00;
        color: #fff;
    }

    .cancel-btn {
        background: #f8f8f8;
    }

    .cancel-btn:hover {
        background: #d0d0d0;
    }

    .goal-info-children {
        flex: 1;
    }

    .goal-info-children hr {
        margin-bottom: 0;
    }

    .info-children-title {
        padding: 4px;
        margin-bottom: 4px;

        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: flex-start;

        font-family: Arial, serif;
        font-size: 16px;
        font-weight: 500;
    }

    .info-children-title small {
        margin-left: 4px;
        font-weight: 400;
        font-size: 11px;
        align-items: center;
    }

    .info-children-content {
        padding: 8px 30px 0;

        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    .children-content-add {
        width: 100%;
    }

    .content-add-btn, .content-add-btn--active {
        background: #fff;
        border: none;
        display: flex;
        align-items: center;

        font-weight: 300;
    }

    .content-add-btn span, .content-add-btn--active span {
        margin-left: 8px;
    }

    .content-add-btn--active {
        color: #f00;
    }

    .set-goal-window-main, .set-goal-window-main--edit {
        box-sizing: border-box;
        background: white;
        flex: 1;
        border-radius: 10px;
        margin-bottom: 16px;
    }

    .set-goal-window-main--edit {
        padding: 8px;
        border: 1px solid #a0a0a0;
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

        font-size: 16px;
        font-weight: 500;
    }

    .set-goal-window-input:focus {
        outline: none;
    }

    .set-goal-window-buttons {
        padding: 8px 0 0 4px;
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

</style>

<GoalInfoWindow on:close="{close}">
    <div class="goal-info-parents" slot="parent">
        <div class="root-parent" on:click={() => {close(); navigate('/home')}}>
            <div class="root-parent-icon">
                <Icon icon="carbon:result-new"/>
            </div>
            <div class="root-parent-title">Цели</div>
        </div>
        {#if parentGoal}
            <div class="parent-separator"> ></div>
            <div class="parent-title" on:click={() => goal = parentGoal}>
                {parentGoal.title}
            </div>
        {/if}
    </div>
    <div class="goal-info-about" slot="about">
        {#if !edit}
            <div class="container-checkbox"
                 style="background: {priorityColors[goal.priority].background}; border-color: {priorityColors[goal.priority].icon}"
                 on:mouseenter={() => hoverCheckBtn = true}
                 on:mouseleave={() => hoverCheckBtn = false}
                 on:click={done}>
                {#if hoverCheckBtn && !goal.isDone}
                    <Icon class="done_btn" icon="akar-icons:circle-check"
                          style="color: {priorityColors[goal.priority].icon};"/>
                {:else if goal.isDone}
                    <Icon class="done_btn" icon="akar-icons:circle-check-fill"
                          style="color: {priorityColors[goal.priority].icon}"/>
                {/if}
            </div>
        {/if}
        <div class="set-goal-window">
            <div class="set-goal-window-main{edit ? '--edit' : ''}">
                <div class="set-goal-window-main-inner">
                    <input class="set-goal-window-input" type="text" placeholder="Цель p1" on:click={() => edit = true}
                           bind:value={goal.title}/>
                    <TextAreaViaAutosize bind:value={goal.description} on:click={() => edit = true}
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
            {#if edit}
                <div class="edit_buttons">
                    <button class="save-btn" on:click={save}>Сохранить</button>
                    <button class="cancel-btn" on:click={cancel}>Отменить</button>
                </div>
            {/if}
        </div>
    </div>
    <div class="goal-info-children" slot="children">
        <div class="info-children-title">
            Подзадачи
            <small>{goal.subtasks.total - goal.subtasks.completed}</small>
        </div>
        <hr>
        <div class="info-children-content">
            {#if showSub}
                {#each subGoals.filter(g => !g.isDone) as sub}
                    <Goal bind:goal={sub} fromGoalCard={false} showSub={false}
                          on:click={() => goal = sub}
                          on:done
                          on:createGoal
                          on:createSub
                          on:edit
                          on:update
                          on:delete
                    />
                {/each}
            {/if}
            <div class="children-content-add"
                 on:mouseenter={() => activeBtn = true}
                 on:mouseleave={() => activeBtn = false}>
                <button class="content-add-btn{activeBtn ? '--active' : ''}" on:click={addSub}>
                    <Icon icon="{activeBtn ? 'bi:plus-circle-fill' : 'bi:plus'}"
                          style="color: #f00; width: 24px; height: 24px"/>
                    <span>Добавить подзадачу</span>
                </button>
            </div>
            {#if showSub}
                {#each subGoals.filter(g => g.isDone) as sub}
                    <Goal bind:goal={sub} fromGoalCard={false} showSub={false}
                          on:click={() => goal = sub}
                          on:done
                          on:createGoal
                          on:createSub
                          on:edit
                          on:update
                          on:delete
                    />
                {/each}
            {/if}
        </div>
    </div>
</GoalInfoWindow>
{#if showScheduler}
    <Scheduler bind:bounding bind:goal isCreate={false} fromGoalCard={false}
               on:close={() => {showScheduler = false; update()}}/>
{/if}