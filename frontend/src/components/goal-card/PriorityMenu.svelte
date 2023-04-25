<script lang="ts">

    import {PRIORITIES} from "../../resources/constants";
    import Icon from "@iconify/svelte";
    import type {Writable} from "svelte/types/runtime/store";
    import type {Priority} from "../../data/models/Task";

    export let selected: number;
    export let useStorage: boolean = false;
    export let storage: Writable<Priority>;

    let priorities = PRIORITIES;

    const select = (index: Priority ) => {
        if (useStorage) {
            $storage = index;
        } else {
            selected = index;
        }
        $$props.closePopover();
    }

</script>

<div class="priority-menu">
    {#if selected || storage}
        {#each priorities as priority}
            <div class="menu-item" on:click={() => select(priority.priority)}>
                <div class="icon">
                    <Icon icon={priority.icon} color={priority.color} width="24"/>
                </div>
                <div class="title">
                    <span>Приоритет {priority.priority}</span>
                </div>
                {#if useStorage ? priority.priority === $storage : priority.priority === selected}
                    <div class="selected-icon">
                        <Icon icon="carbon:checkmark" color="#E8A236" width="24"/>
                    </div>
                {/if}
            </div>
        {/each}
    {/if}
</div>

<style>

    .priority-menu {
        width: 15rem;
        height: 10rem;

        background-color: var(--cds-field);

        display: flex;
        flex-direction: column;

        align-items: flex-start;

        border-radius: 16px;
        overflow: hidden;
    }

    .menu-item {
        width: 100%;
        height: 40px;

        display: flex;
        align-items: center;
        justify-content: space-between;

        cursor: pointer;
    }

    .menu-item:hover {
        background-color: var(--cds-field-hover);
    }

    .icon, .selected-icon {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .title {
        flex-grow: 1;

        display: flex;
        justify-content: flex-start;
        align-items: center;
    }

    .title span {
        margin-left: .5rem;
    }

    .icon {
        margin-left: .5rem;
    }

    .selected-icon {
        margin-right: .5rem;
    }

</style>