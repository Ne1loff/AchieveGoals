<script>
    import Icon from "@iconify/svelte"
    import Scheduler from "./Scheduler.svelte";
    import GoalMenu from "./GoalMenu.svelte";
    import {createEventDispatcher} from 'svelte';
    import dayjs from 'dayjs';

    const dispatch = createEventDispatcher();

    export let goal = {
        id: 1,
        title: 'Убраться дома',
        description: '',
        isDone: false,
        gid: null,
        subtasks: {
            total: 1,
            completed: 1
        },
        priority: 1,
        createdAt: 11,
        updatedAt: 11,
        deadline: '11 январь'
    };
    export let indent = 1;
    export let showSub = true;
    export let fromGoalCard = true;

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

    let showSubtasks = false;
    let hoverCheckBtn = false;
    let showActions = false;
    let active = false;
    let showScheduler = false;

    let diff;
    let color;
    $: {
        diff = dayjs(goal.deadline).diff(dayjs(), 'day');
        color = diff === 0 ? "#06c03a" : diff === 1 ? "#d97c01" : diff > 1 && diff < 8 ? "#246fe0" : diff < 0 || isNaN(diff) ? "#dc231f" : "#838383"
    }
    let schedulerBounding;
    let menuBounding;

</script>

<div class="goal-body-{indent > 5 || indent < 1 ? 1 : indent}"
     on:mouseenter={() => active = true}
     on:mouseleave={() => active = false}>
    <div class="body-container">
        {#if goal.subtasks.total > 0 && showSub}
            <button class="container-actions-left" on:click={() => {
                showSubtasks = !showSubtasks;
                dispatch('showSub', goal.id);
            }}>
                <Icon class="action-left-btn" icon="uil:angle-right-b" rotate="{showSubtasks ? '45' : '0'}"/>
            </button>
        {/if}
        <div class="container-checkbox"
             style="background: {priorityColors[goal.priority].background}; border-color: {priorityColors[goal.priority].icon}"
             on:mouseenter={() => hoverCheckBtn = true}
             on:mouseleave={() => hoverCheckBtn = false}
             on:click={() => {
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
        <div class="container-content" on:click>
            <div class="content-title">{goal.title}</div>
            <div class="content-info-tags">
                {#if goal.subtasks.total > 0}
                    <div class="info-tags-subtasks">
                        <div class="info-tags-icon">
                            <Icon icon="ant-design:node-index-outlined" style="width: 16px; height: 16px"/>
                        </div>
                        <div class="info-tags-text">{goal.subtasks.completed + '/' + goal.subtasks.total}</div>
                    </div>
                {/if}
                <div class="info-tags-date">
                    <div class="info-tags-icon">
                        <Icon class="action-icons" icon="bi:calendar-event" style="width: 12px; height: 12px"/>
                    </div>
                    <div class="info-tags-text" style="color: {color}">{dayjs(goal.deadline).format('DD dd ') + goal.deadlineTime}</div>
                </div>
            </div>
        </div>
        <div class="container-actions-right{active? '--active' : ''}">
            <button class="action-btn" on:click={editGoal}>
                <Icon class="action-icons" icon="feather:edit-3"/>
            </button>
            <button class="action-btn" bind:this={schedulerBounding} on:click={() => showScheduler = true}>
                <Icon class="action-icons" icon="bi:calendar-event"/>
            </button>
            <button class="action-btn" bind:this={menuBounding} on:click={() => showActions = true}>
                <Icon class="action-icons" icon="bi:three-dots"/>
            </button>
        </div>
    </div>
    {#if showActions}
        <GoalMenu bind:bounding={menuBounding} bind:goal fromGoalCard={fromGoalCard}
                  on:newGoal={createGoal}
                  on:newSub={createSubtask}
                  on:edit={editGoal}
                  on:close={() => {showActions = false; active = false; updateGoal()}}
                  on:delete={deleteGoal}/>
    {/if}
    {#if showScheduler}
        <Scheduler bind:bounding={schedulerBounding} bind:goal isCreate={false} fromGoalCard={fromGoalCard}
                   on:close={() => {showScheduler = false; active = false; updateGoal()}}/>
    {/if}

</div>

<style>

    .goal-body-1, .goal-body-2, .goal-body-3, .goal-body-4, .goal-body-5 {
        min-width: 400px;
        width: 100%;
        border-bottom: 1px solid #f4f4f4;
        user-select: none;
    }

    .goal-body-2 {
        width: calc(100% - (28px * 1));
        margin-left: 28px;
    }

    .goal-body-3 {
        width: calc(100% - (28px * 2));
        margin-left: calc(28px * 2);
    }

    .goal-body-4 {
        width: calc(100% - (28px * 3));
        margin-left: calc(28px * 3);
    }

    .goal-body-5 {
        width: calc(100% - (28px * 4));
        margin-left: calc(28px * 4);
    }

    .body-container {
        display: flex;
        position: relative;
        align-items: stretch;
    }

    .container-actions-left {
        top: 5px;
        left: -28px;
        width: 24px;
        height: 24px;

        position: absolute;

        margin: 0;
        padding: 0;
        display: flex;
        align-items: center;
        justify-content: center;


        background: #fff;
        border: none;
    }

    .container-actions-left:hover {
        background: #f0f0f0;
    }

    .container-checkbox {
        height: 16px;
        width: 16px;
        cursor: pointer;
        background: #faeceb;

        margin: 8px 6px 0 -3px;
        z-index: 1;

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

    .container-actions-right, .container-actions-right--active {
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

    .container-actions-right--active {
        opacity: 1;
    }

    .action-btn {
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
        background: #f0f0f0;
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