<script lang="ts">
    import {slide} from 'svelte/transition';
    import Icon from "@iconify/svelte"
    import Scheduler from "./SchedulerComponent.svelte";
    import GoalMenu from "./GoalMenuComponent.svelte";
    import {createEventDispatcher} from 'svelte';
    import dayjs from 'dayjs';
    import {PRIORITY_COLORS} from "../../resources/constants";
    import Goal from '../../data/models/Goal'
    import Popover from "../popover/Popover.svelte";
    import InlineCalendar from "./InlineCalendar.svelte";

    const dispatch = createEventDispatcher();

    export let goal: Goal;
    export let subs: Goal[];
    export let indent = 1;
    export let showSub = true;
    export let fromGoalCard = true;

    const priorityColors = PRIORITY_COLORS;

    const createGoal = () => {
        dispatch('createGoal')
    }
    const createSubtask = () => {
        dispatch('createSub', goal.id);
    }
    const editGoal = () => {
        dispatch('edit', goal)
    }
    const updateGoal = () => {
        dispatch('update', goal)
    }
    const deleteGoal = () => {
        dispatch('delete', goal.id)
    }

    const onMenuClose = () => {
        active = false;
        updateGoal();
    }

    let showSubtasks = false;
    let hoverCheckBtn = false;
    let showActions = false;
    let active = false;
    let showScheduler = false;

    let diff;
    let color;

    $: {
        if (goal) {
            diff = dayjs(goal.deadline).diff(dayjs(), 'day');
            color = diff === 0 ? "#06c03a"
                : diff === 1 ? "#d97c01"
                    : diff > 1 && diff < 8 ? "#246fe0"
                        : diff < 0 || isNaN(diff) ? "#dc231f"
                            : "#838383";
        }
    }

</script>

<div class="goal-body" data-item-indent="{indent > 5 || indent < 1 ? 1 : indent}"
     on:mouseover={() => active = true}
     on:mouseout={() => active = false}
     on:focus={() => active = true}
     on:blur={() => active = false}
     transition:slide>
    <div class="body-container">
        <div class="body-container-left">
            {#if goal.subtasks.total > 0 && showSub}
                <div class="container-actions-left" on:click={() => {
                showSubtasks = !showSubtasks;
                dispatch('showSub', goal.id);
            }}>
                    <div class="btn-icon-container" data-item-rotate={showSubtasks ? '45' : '0'}>
                        <Icon class="action-left-btn" icon="uil:angle-right-b"/>
                    </div>
                </div>
            {/if}
            <div class="container-checkbox"
                 style="background: {priorityColors[goal.priority].background}; border-color: {priorityColors[goal.priority].icon}"
                 on:mouseover={() => hoverCheckBtn = true}
                 on:mouseout={() => hoverCheckBtn = false}
                 on:focus={() => hoverCheckBtn = true}
                 on:blur={() => hoverCheckBtn = false}
                 on:click={() => {
                     console.log(goal.isDone);
                 goal.isDone = !goal.isDone;
                 dispatch('done', goal);
             }}>
                {#if hoverCheckBtn && !goal.isDone}
                    <Icon class="done_btn" icon="akar-icons:circle-check"
                          style="color: {priorityColors[goal.priority].icon};"/>
                {:else if goal.isDone}
                    <Icon class="done_btn" icon="akar-icons:circle-check-fill"
                          style="color: {priorityColors[goal.priority].icon}"/>
                {/if}
            </div>
        </div>
        <div class="container-content" on:click>
            <div class="content-title" class:done={goal.isDone}>{goal.title}</div>
            <div class="content-info-tags">
                {#if goal.subtasks.total > 0}
                    <div class="info-tags-subtasks">
                        <div class="info-tags-icon">
                            <Icon icon="ant-design:node-index-outlined" style="width: 16px; height: 16px"/>
                        </div>
                        <div class="info-tags-text">{goal.subtasks.completed + '/' + goal.subtasks.total}</div>
                    </div>
                {/if}
                <Popover overlayColor={"rgba(0, 0, 0, 0.15)"} on:close={onMenuClose}>
                    <div slot="target" class="info-tags-date">
                        <div class="info-tags-icon">
                            <Icon class="action-icons" icon="bi:calendar-event" style="width: 12px; height: 12px"/>
                        </div>
                        <div class="info-tags-text" style="color:{color}">
                            {dayjs(goal.deadline).format('DD dddd HH:mm')}</div>
                    </div>
                    <InlineCalendar slot="content" bind:value={goal.deadline}/>
                </Popover>
            </div>
        </div>
        <div class="container-actions-right" class:active>
            <div class="action-btn" on:click={editGoal}>
                <Icon class="action-icons" icon="feather:edit-3"/>
            </div>
            <Popover on:close={onMenuClose}>
                <div slot="target" class="action-btn"
                     on:click={() => showScheduler = true}>
                    <Icon class="action-icons" icon="bi:calendar-event"/>
                </div>
                <Scheduler slot="content" bind:goal isCreate={false}
                           fromGoalCard={fromGoalCard}
                           on:close={onMenuClose}/>
            </Popover>
            <Popover on:close={onMenuClose}>
                <div slot="target" class="action-btn" on:click={() => showActions = true}>
                    <Icon class="action-icons" icon="bi:three-dots"/>
                </div>
                <GoalMenu slot="content" bind:goal fromGoalCard={fromGoalCard}
                          on:newGoal={createGoal}
                          on:newSub={createSubtask}
                          on:edit={editGoal}
                          on:close={onMenuClose}
                          on:delete={deleteGoal}/>
            </Popover>
        </div>
    </div>
</div>
{#if showSubtasks}
    {#each subs as goal}
        <svelte:self bind:goal indent={indent + 1} subs={subs.filter(it => it.gid === goal.id)}/>
    {/each}
{/if}

<style>

    :root {
        --own-btn-hover-color: #f0f0f0;
    }

    .goal-body {
        position: relative;
        min-width: 450px;
        width: 100%;
        border-bottom: 1px solid #f4f4f4;
        user-select: none;
    }

    .goal-body[data-item-indent="2"] {
        width: calc(100% - (28px * 1));
        margin-left: 28px;
    }

    .goal-body[data-item-indent="3"] {
        width: calc(100% - (28px * 2));
        margin-left: calc(28px * 2);
    }

    .goal-body[data-item-indent="4"] {
        width: calc(100% - (28px * 3));
        margin-left: calc(28px * 3);
    }

    .goal-body[data-item-indent="5"] {
        width: calc(100% - (28px * 4));
        margin-left: calc(28px * 4);
    }

    .body-container {
        display: flex;
        position: relative;
        align-items: stretch;
    }

    .body-container-left {
        padding: 6px 4px;
        box-sizing: border-box;
        margin-right: 4px;

        display: flex;
        justify-content: center;
        align-items: center;
    }

    .container-actions-left {
        width: 24px;
        height: 24px;

        margin: 0 4px 0 0;
        padding: 0;
        display: flex;
        align-items: center;
        justify-content: center;

        border-radius: 5px;


        background: #fff;
        border: none;
    }

    .container-actions-left:hover {
        background: var(--own-btn-hover-color);
    }

    .container-checkbox {
        height: 16px;
        width: 16px;
        cursor: pointer;
        background: #faeceb;

        display: flex;
        align-items: center;
        justify-content: center;

        border-radius: 50%;
        border: 2px solid #fff;
    }

    .container-content {
        flex: 1;
        display: flex;
        flex-direction: column;

        padding: 8px 0;
    }

    .content-title {
        margin-bottom: 3px;

        flex-grow: 1;
        font-size: 14px;
        line-height: 21px;
        word-wrap: break-word;
        word-break: break-word;
    }

    .content-title.done {
        text-decoration: line-through;
        color: #777
    }

    .content-info-tags {
        min-height: 16px;

        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        justify-content: flex-start;
        align-items: center;
    }

    .info-tags-subtasks, .info-tags-date {
        display: flex;
        justify-content: flex-start;
        align-items: center;
        margin-right: 8px;

        padding: 2px;
        box-sizing: border-box;
    }

    .info-tags-date {
        cursor: pointer;
    }

    .info-tags-date:hover {
        border-radius: 5px;
        background-color: var(--own-btn-hover-color);
    }

    .info-tags-icon {
        width: 16px;
        height: 16px;
        margin-right: 2px;

        display: flex;
        align-items: center;
        justify-content: center;
    }

    .info-tags-text {
        font-size: 12px;
        color: #838383;
    }

    .btn-icon-container {
        height: 100%;
        width: 100%;
        transition: transform .2s linear;

        display: flex;
        align-items: center;
        justify-content: center;
    }

    .btn-icon-container[data-item-rotate="45"] {
        transform: rotate(90deg);
    }

    .container-actions-right, .container-actions-right.active {
        display: flex;
        justify-content: flex-end;
        height: 24px;
        margin-right: -38px;
        margin-top: 8px;
        padding-left: 16px;
        position: absolute;
        right: 0;
        top: 0;
    }

    .container-actions-right {
        opacity: 0;
    }

    .container-actions-right.active {
        opacity: 1;
    }

    .action-btn {
        position: relative;

        border: none;
        border-radius: 3px;
        background: #fff;
        display: flex;
        justify-content: center;
        align-items: center;
        width: 24px;
        height: 24px;
        cursor: pointer;
        margin-left: 8px;
        padding: 0;
    }

    .action-btn:hover {
        background: var(--own-btn-hover-color);
    }

    :global(.action-left-btn) {
        height: 18px;
        width: 18px;
        color: gray;
    }

    :global(.action-icons) {
        height: 16px;
        width: 16px;
        color: gray;
    }

    :global(.done_btn) {
        height: 20px;
        width: 20px;
        overflow: visible;
    }
</style>