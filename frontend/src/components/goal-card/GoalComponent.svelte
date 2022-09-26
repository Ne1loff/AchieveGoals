<script lang="ts">
    import Icon from "@iconify/svelte"
    import {onDestroy, onMount} from 'svelte';
    import dayjs from 'dayjs';
    import {PRIORITY_COLORS} from "../../resources/constants";
    import Goal from '../../data/models/Goal'
    import InlineCalendar from "../date-picker/InlineCalendar.svelte";
    import {GOALS} from "../../data/storage/storage";
    import GoalCheckbox from "./GoalCheckbox.svelte";
    import Scheduler from "./SchedulerComponent.svelte";
    import GoalMenu from "./GoalMenuComponent.svelte";
    import GoalEditorComponent from "./GoalEditorComponent.svelte";
    import {popoverTrigger} from "../popover/global/Popover";
    import {navigate} from "svelte-routing";
    import {hrefs} from "../../resources/config";
    import {getColorFromDayDiff} from "./utils";
    import AnimationContainer from "./AnimationContainer.svelte";
    import PortablePopover from "../popover/global/PortablePopover.svelte";
    import type {Unsubscriber} from "svelte/store";
    import GoalService from "../../services/GoalService";
    import ServiceFactory from "../../services/ServiceFactory";

    export let style: string = "";
    export let goal: Goal;
    export let indent: 1 | 2 | 3 | 4 | 5 = 1;
    export let withoutSubs: boolean = false;
    export let animationDurationIn: number = 200;
    export let animationDurationOut: number = 200;
    export let root: boolean = true;

    let animDurationIn: number = animationDurationIn;
    let animDurationOut: number = animationDurationOut;

    let goalService: GoalService;

    let subs: Goal[] = [];

    const priorityColors = PRIORITY_COLORS;
    let showSubtasks = false;

    let edit: boolean = false;
    const editGoal = () => edit = true;

    let createOver: boolean = false;
    let createUnder: boolean = false;
    let newGoal: Goal;

    const createGoalOver = () => {
        createOver = true;
        newGoal = new Goal();
    }
    const createGoalUnder = () => {
        createUnder = true;
        newGoal = new Goal();
        newGoal.gid = goal.id;
    }

    const saveNewGoal = () => {
        goalService.createGoal(newGoal)
            .then((_) => {
            });
    }

    const deleteGoal = () => {
        let isCancel: boolean = false;
        $GOALS = $GOALS.filter(it => it.id !== goal.id);
        ServiceFactory.INSTANCE.toastService.dialog('Task was successfully deleted.',
            {
                accept: () => {
                    if (isCancel) return;
                    goalService.deleteTask(goal.id).then(() => {
                        ServiceFactory.INSTANCE.toastService.success('Success', 'Task was successfully deleted.')
                    });
                },
                reject: () => {
                    isCancel = true;
                    const goals = $GOALS;
                    goals.push(goal);
                    $GOALS = goals;
                }
            }
        );

    }

    $: if (goal) {
        goal.subtasks.total = subs.length;
        goal.subtasks.completed = subs.filter(it => it.isDone).length;
    }

    let unsubscribe: Unsubscriber;

    onMount(() => {
        if (!withoutSubs) {
            subs = $GOALS.filter(it => it.gid === goal.id);
            unsubscribe = GOALS.subscribe((g) => subs = g.filter(it => it.gid === goal.id));
        }
        goalService = ServiceFactory.INSTANCE.goalService;
    });

    onDestroy(() => {
        if (typeof unsubscribe === 'function')
            unsubscribe()
    });

</script>


<AnimationContainer animate show={createOver}>
    <GoalEditorComponent bind:goal={newGoal} {indent}
                         on:save={saveNewGoal}
                         on:done={() => createOver = false}
    />
</AnimationContainer>
<AnimationContainer animate={!root}
                    animationDurationIn={animDurationIn}
                    animationDurationOut={animDurationOut}>
    {#if !edit}
        <div class="goal-body" data-item-indent="{indent > 5 || indent < 1 ? 1 : indent}"
             tabindex="1"
             style={style}
             on:click={() => navigate(hrefs.task(goal.id))}
        >
            <div class="body-container">
                <div class="body-container-left">
                    {#if !withoutSubs && goal.subtasks.total > 0}
                        <div class="container-actions-left"
                             on:click|stopPropagation={() => showSubtasks = !showSubtasks}>
                            <div class="btn-icon-container" data-item-rotate={showSubtasks ? '90' : '0'}>
                                <Icon class="action-left-btn"
                                      icon="uil:angle-right-b"
                                      width="32" height="32"
                                      color="color: var(--cds-icon-01)"
                                />
                            </div>
                        </div>
                    {:else}
                        <div style="width: 28px"></div>
                    {/if}
                    <GoalCheckbox
                            bind:done={goal.isDone}
                            round
                            --own-checkbox-size="1.5rem"
                            --own-checkbox-border-color={priorityColors[goal.priority].icon}
                            --own-checkbox-hover-border-color={priorityColors[goal.priority].icon}
                            --own-checkbox-done-border-color={priorityColors[goal.priority].icon}

                            --own-checkbox-bg-color={priorityColors[goal.priority].background}
                            --own-checkbox-hover-bg-color={priorityColors[goal.priority].background}
                            --own-checkbox-done-bg-color={priorityColors[goal.priority].icon}

                            --own-check-mark-bg-color={priorityColors[goal.priority].icon}
                            --own-check-mark-hover-bg-color={priorityColors[goal.priority].icon}
                    />
                </div>
                <div class="container-content">
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
                        <div class="info-tags-date"
                             use:popoverTrigger={{
                        component: {
                            src: InlineCalendar,
                            props: {
                                goalId: goal.id,
                                withTime: true
                            }
                        },
                        style: {
                            "--own-popover-border-radius": "16px"
                        },
                        stopPropagation: true,
                        classStyle: 'elevation-6'
                    }}>
                            <div class="info-tags-icon">
                                <Icon class="action-icons" icon="bi:calendar-event" style="width: 12px; height: 12px"/>
                            </div>
                            <div class="info-tags-text" style="color:{getColorFromDayDiff(goal.deadline)}">
                                {dayjs(goal.deadline).format('DD dddd HH:mm')}</div>
                        </div>
                    </div>
                </div>
                <div class="container-actions-right">
                    <div class="action-btn"
                         on:click|stopPropagation={editGoal}
                    >
                        <Icon class="action-icons" icon="feather:edit-3"
                              height="16" width="16"
                              color="var(--cds-icon-01)"
                        />
                    </div>
                    <div class="action-btn"
                         use:popoverTrigger={{
                            component: {
                                src: Scheduler,
                                props: {
                                    goalId: goal.id
                                }
                            },
                            style: {
                                    "--own-popover-border-radius": "16px"
                            },
                            stopPropagation: true,
                            classStyle: 'elevation-6'
                        }}
                    >
                        <Icon class="action-icons" icon="bi:calendar-event"
                              height="16" width="16"
                              color="var(--cds-icon-01)"
                        />
                    </div>
                    <PortablePopover options={{
                        stopPropagation: true,
                        classStyle: 'elevation-6'
                    }}>
                        <div slot="target" class="action-btn" let:toggle on:click|stopPropagation={toggle}>
                            <Icon class="action-icons" icon="bi:three-dots"
                                  height="16" width="16"
                                  color="var(--cds-icon-01)"
                            />
                        </div>
                        <GoalMenu slot="content" goalId={goal.id}
                                  let:close
                                  on:edit={() => {editGoal(); close();}}
                                  on:create-over={() => {createGoalOver(); close();}}
                                  on:create-under={() => {createGoalUnder(); close();}}
                                  on:delete={() => {deleteGoal(); close();}}
                        />
                    </PortablePopover>
                </div>
            </div>
        </div>
    {:else }
        <GoalEditorComponent bind:goal {indent}
                             on:done={() => edit = false}
        />
    {/if}
</AnimationContainer>
<AnimationContainer animate show={!withoutSubs && showSubtasks}>
    {#each subs as goal}
        <svelte:self bind:goal indent={indent + 1} {animationDurationIn} {animationDurationOut} root={false}/>
    {/each}
</AnimationContainer>
<AnimationContainer animate show={createUnder}>
    <GoalEditorComponent bind:goal={newGoal} indent={indent + 1}
                         on:save={saveNewGoal}
                         on:done={() => createUnder = false}
    />
</AnimationContainer>

<style>

    :root {
        --own-btn-hover-color: #f0f0f0;
        --own-component-border-width: calc(100% - 28px);
        --own-component-border-right: -28px;
        --own-component-width: 100%;
    }

    .goal-body {
        position: relative;
        min-width: 300px;
        width: var(--own-component-width);
        user-select: none;
        cursor: pointer;
    }

    .goal-body:focus-visible {
        outline: var(--cds-focus) solid 1px;
        background-color: var(--cds-hover-ui);
    }

    .goal-body:before {
        width: calc(100% - 28px);
        content: "";

        position: absolute;
        right: 0;
        bottom: 0;

        border-bottom: 1px solid var(--cds-ui-03);
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


        background: inherit;
        border: none;
    }

    .container-actions-left:hover {
        background-color: var(--cds-hover-ui);
        color: var(--cds-hover-primary-text, #0043ce);
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
        color: var(--cds-text-disabled);
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
        background-color: var(--cds-hover-ui);
        color: var(--cds-hover-primary-text, #0043ce);
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
        color: var(--cds-text-secondary);
    }

    .btn-icon-container {
        height: 100%;
        width: 100%;
        transition: transform .2s linear;

        display: flex;
        align-items: center;
        justify-content: center;
    }

    .btn-icon-container[data-item-rotate="90"] {
        transform: rotate(90deg);
    }

    .container-actions-right {
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

    .action-btn {
        position: relative;

        border: none;
        border-radius: 3px;
        background: inherit;
        display: flex;
        justify-content: center;
        align-items: center;
        width: 24px;
        height: 24px;
        cursor: pointer;
        margin-left: 8px;
        padding: 0;

        opacity: 0;
    }

    .action-btn:hover {
        background-color: var(--cds-hover-ui);
        color: var(--cds-hover-primary-text, #0043ce);
    }

    .goal-body:hover .container-actions-right .action-btn {
        opacity: 1;
    }

    :global(.action-btn[aria-expanded="true"]) {
        opacity: 1 !important;
    }

</style>