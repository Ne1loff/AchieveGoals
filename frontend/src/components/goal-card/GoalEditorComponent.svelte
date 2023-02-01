<script lang="ts">

    import {Button, ButtonSet} from "carbon-components-svelte";
    import Goal, {Priority} from "../../data/models/Goal";
    import Icon from "@iconify/svelte";
    import TextArea from "../inputs/TextArea.svelte";
    import dayjs from "dayjs";
    import {PRIORITIES} from "../../resources/constants";
    import Scheduler from "./SchedulerComponent.svelte";
    import {popoverTrigger} from "../popover/global/Popover";
    import {createEventDispatcher, onDestroy} from "svelte";
    import {writable} from "svelte/store";
    import type {Writable} from "svelte/types/runtime/store";
    import PriorityMenu from "./PriorityMenu.svelte";
    import {getColorFromDayDiff} from "./utils";

    const dispatch = createEventDispatcher();

    const priorities = PRIORITIES;

    export let goal: Goal;
    export let indent: 0 | 1 | 2 | 3 | 4 | 5 = 0;
    export let saveButtonTitle: string = 'Сохранить';

    let goalCopy: Goal | undefined;
    let goalStorage: Writable<Goal>;
    let priorityStorage: Writable<Priority>;

    const setupStorages = () => {
        goalCopy = goal.clone();

        goalStorage = writable(goalCopy);
        goalStorage.subscribe((g) => {
            if (goalCopy) goalCopy.deadline = g.deadline;
        });

        if (!priorityStorage) priorityStorage = writable(goalCopy.priority);
        else priorityStorage.set(goalCopy.priority);

        priorityStorage.subscribe((p) => {
            if (goalCopy) goalCopy.priority = p;
        });
    }

    const reset = () => {
        goalCopy = undefined;
    }

    const done = () => dispatch('done');
    const cancel = () => {
        dispatch('cancel');
        done();
    }
    const save = () => {
        if (!goalCopy) return;

        goal = goalCopy;
        dispatch('save');
        done();
    }

    $:if (goal) {
        setupStorages();
    } else {
        reset();
    }

    let saveBtnDisable: boolean;
    $: saveBtnDisable = !(goalCopy?.title.length > 0 && !isNaN(<number>goalCopy?.deadline.valueOf())) ?? true;

    onDestroy(() => dispatch('destroy'));

</script>

<div class="goal-editor" data-item-indent={indent > 5 || indent < 0 ? 0 : indent}>
    {#if goalCopy}
        <div class="set-goal-window-main">
            <div class="set-goal-window-main-inner">
                <input class="set-goal-window-input" type="text" placeholder="Цель p1"
                       bind:value={goalCopy.title}/>
                <TextArea bind:value={goalCopy.description}
                          placeholder="Описание"
                          minRows={2}
                          maxRows={64}/>
            </div>
            <div class="set-goal-window-buttons">
                <button class="calendar_button" type="button"
                        use:popoverTrigger={{
                            component: {
                                src: Scheduler,
                                props: {
                                    goalStorage: goalStorage,
                                    useGlobalStore: false
                                }
                            },
                            style: {
                                "--own-popover-border-radius": "16px"
                            },
                            classStyle: 'elevation-6'
                        }}
                >
                    <Icon class="action-icons" icon="bi:calendar-event"
                          width="16"
                          color={getColorFromDayDiff(goalCopy.deadline)}
                    />
                    <span class="calendar_button_span"
                          style="color: {getColorFromDayDiff(goalCopy.deadline)}"
                    >{dayjs(goalCopy.deadline).format('DD dd HH:mm')}</span>
                </button>
                <button class="property" type="button"
                        use:popoverTrigger={{
                            component: {
                                src: PriorityMenu,
                                props: {
                                    storage: priorityStorage,
                                    useStorage: true
                                }
                            },
                            style: {
                                "--own-popover-border-radius": "16px"
                            },
                            classStyle: 'elevation-6'
                        }}
                >
                    <Icon icon={priorities[goalCopy.priority - 1].icon}
                          width="20px" color={priorities[goalCopy.priority - 1].color}/>
                </button>
            </div>
        </div>
    {/if}
    <div class="submit-buttons">
        <ButtonSet>
            <Button size="small" kind="secondary" on:click={cancel}>Отмена</Button>
            <Button size="small" disabled={saveBtnDisable} on:click={save}>{saveButtonTitle}</Button>
        </ButtonSet>
    </div>
</div>

<style>

    .goal-editor {
        margin: .5rem .875rem;
    }

    .goal-editor[data-item-indent="0"] {
        width: 100%;
        margin-left: 0;
    }

    .goal-editor[data-item-indent="1"] {
        width: calc(100% - (28px * 1));
        margin-left: 28px;
    }

    .goal-editor[data-item-indent="2"] {
        width: calc(100% - (28px * 2));
        margin-left: calc(28px * 2);
    }

    .goal-editor[data-item-indent="3"] {
        width: calc(100% - (28px * 3));
        margin-left: calc(28px * 3);
    }

    .goal-editor[data-item-indent="4"] {
        width: calc(100% - (28px * 4));
        margin-left: calc(28px * 4);
    }

    .goal-editor[data-item-indent="5"] {
        width: calc(100% - (28px * 5));
        margin-left: calc(28px * 5);
    }

    .set-goal-window-main {
        width: 100%;
        background: inherit;
        margin-bottom: 1rem;
        padding: .5rem .875rem;

        border: 1px solid var(--cds-border-strong);
        border-radius: 16px;
    }

    .set-goal-window-main-inner {
        max-height: 200px;
        margin-bottom: 10px;
    }

    .set-goal-window-input {
        background-color: inherit;
        margin: 0 0 8px 0;
        padding: 0;
        width: 100%;

        border: none;
        color: var(--cds-text-01);

        font-size: var(--cds-body-short-02-font-size);
    }

    .set-goal-window-input:focus {
        outline: none;
    }

    .set-goal-window-buttons {
        display: flex;
        justify-content: space-between;
    }

    .calendar_button {
        background: inherit;
        font-size: 1.2em;
        cursor: pointer;

        height: 28px;

        display: flex;
        flex-shrink: 0;
        align-items: center;
        color: #555;

        border: 1px solid var(--cds-border-subtle);
        border-radius: 5px;

        padding: 0 8px;
    }

    .calendar_button:focus-visible {
        outline: var(--cds-focus) solid 1px;
        background-color: var(--cds-hover-ui);
    }

    .calendar_button:hover {
        background-color: var(--cds-hover-ui);
        color: var(--cds-hover-primary-text, #0043ce);
    }

    .property {
        height: 28px;
        width: 28px;
        display: flex;
        align-items: center;
        justify-content: center;
        border: none;
        padding: 0;
        margin: 0;
        background: inherit;
        border-radius: 5px;
    }

    .property:focus-visible {
        outline: var(--cds-focus) solid 1px;
        background-color: var(--cds-hover-ui);
    }

    .property:hover {
        background-color: var(--cds-hover-ui);
        color: var(--cds-hover-primary-text, #0043ce);
    }

    :global(.property[aria-expanded="true"]) {
        border: 1px solid var(--cds-border-subtle);
        border-radius: 5px;
    }

    :global(.calendar_button_icon) {
        width: 16px;
        height: 16px;
    }

    .calendar_button_span {
        margin: 0 8px;
    }

    :global(.bx--btn-set) {
        gap: .25rem;
    }

    :global(.bx--btn-set .bx--btn) {
        width: auto !important;
    }

    .submit-buttons {
        width: 100%;

        display: flex;
        align-items: center;
        justify-content: right;
    }

</style>