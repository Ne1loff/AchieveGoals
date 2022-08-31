<script lang="ts">

    import Goal from "../../data/models/Goal";
    import GoalEditorComponent from "./GoalEditorComponent.svelte";
    import Icon from "@iconify/svelte";

    export let taskStorage: Goal[];
    export let title: string = "Добавить цель";

    let newTask: Goal = undefined;

    const create = () => newTask = new Goal();

    const cancel = () => newTask = undefined;

    const save = () => {
        const storage = taskStorage;
        storage.push(newTask);
        taskStorage = storage;
        create();
    }

</script>

<div class:task-creator={true}>
    {#if newTask}
        <GoalEditorComponent bind:goal={newTask} on:save={save} on:cancel={cancel}/>
    {:else}
        <button class:creator-button={true} tabindex="1"
                on:click={create}>
            <span class="button-icon">
                <Icon class="add-icon" icon="carbon:add" width="24"/>
            </span>
            <span class="button-title">{title}</span>
        </button>
    {/if}
</div>

<style>

    .task-creator {
        margin-top: 2px;
        z-index: 1;
    }

    .creator-button {
        margin-left: 1.5rem;
        padding-left: .5rem;

        width: calc(100% - 1.5rem);
        height: 2rem;

        display: flex;
        align-items: center;
        justify-content: flex-start;

        background: inherit;
        color: var(--cds-text-placeholder);
        border: none;
        border-radius: 5px;
    }

    .creator-button:focus-visible {
        outline: var(--cds-focus) solid 1px;
        background-color: var(--cds-hover-ui);
    }

    .creator-button:hover .button-icon {
        background-color: var(--cds-danger);
        color: white;
    }

    .creator-button:hover .button-title {
        color: var(--cds-text-error);
    }

    .button-icon {
        margin-right: 8px;
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 50%;
        color: var(--cds-danger);
    }

</style>