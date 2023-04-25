<script lang="ts">

    import {link} from "svelte-routing"

    export let title: string = '';
    export let taskCount: number = 0;
    export let href: string = undefined;
    export let selected: boolean = false;

</script>

{#if href}
    <a class="side-bar-button" class:selected href={href} use:link>
        <span class="btn-icon">
            <slot name="icon"/>
        </span>
        <span class="btn-title">{title}</span>
        {#if taskCount !== 0}
            <span class="btn-tasks-count">{taskCount}</span>
        {/if}
    </a>
{:else}
    <button class="side-bar-button" class:selected on:click>
        <span class="btn-icon">
            <slot name="icon"/>
        </span>
        <span class="btn-title">{title}</span>
        {#if taskCount !== 0}
            <span class="btn-tasks-count">{taskCount}</span>
        {/if}
    </button>
{/if}

<style>

    :root {
        --sidebar-btn-width: 100%;
        --sidebar-btn-margin: 0;
        --sidebar-btn-padding: 0 .5rem;
        --sidebar-btn-task-count-color: var(--cds-text-05);
    }

    .side-bar-button {
        display: flex;
        align-items: center;
        justify-content: space-between;

        height: 2.125rem;

        width: var(--sidebar-btn-width);
        margin: var(--sidebar-btn-margin);
        padding: var(--sidebar-btn-padding);

        background: inherit;
        border: none;
        border-radius: 16px;


        color: var(--cds-text-01);
    }

    .side-bar-button.selected, .side-bar-button:hover, .side-bar-button:not(:disabled):active {
        background-color: var(--cds-layer-hover);
        text-decoration: none;
    }

    .btn-icon {
        display: flex;
        align-items: center;
        justify-content: center;

        position: relative;
    }

    .btn-title {
        margin-left: .75rem;
        text-align: left;
        flex-grow: 1;
    }

    .btn-tasks-count {
        display: flex;
        align-items: center;
        justify-content: center;

        font-size: 12px;

        padding-right: .5rem;
        color: var(--sidebar-btn-task-count-color);
    }


</style>