<script lang="ts">
    import SchedulerWindow from "../ModalWindows/SchedulerWindow.svelte";
    import Icon from "@iconify/svelte";
    import {createEventDispatcher} from 'svelte';

    const dispatch = createEventDispatcher();

    export let priority;
    export let bounding;

    let priorities = [
        {
            priority: 1, active: false, icon: "bi:flag-fill", color: "#de4c4a"
        },
        {
            priority: 2, active: false, icon: "bi:flag-fill", color: "#f49c18"
        },
        {
            priority: 3, active: false, icon: "bi:flag-fill", color: "#3077e1"
        },
        {
            priority: 4, active: false, icon: "bi:flag", color: ""
        },
    ]
    $: {
        priorities.forEach(e => e.active = false)
        priorities[priority - 1].active = true;
    }

</script>

<style>

    .main-container {
        display: flex;
        flex-direction: column;
    }

    .filter-btn, .filter-btn--active {
        padding: 8px;
        margin: 0;

        width: 100%;

        display: flex;
        flex-direction: row;
        align-items: start;
        justify-content: space-around;

        background: #fff;
        border: none;
        cursor: pointer;
    }

    .filter-btn--active {
        background: #7db8ef;
    }

    .filter-btn span, .filter-btn--active span {
        margin-left: 8px;
    }

    .filter-btn:hover {
        background: #dddddd;
    }

</style>

<SchedulerWindow on:close bind:bounding fromGoalCard={false}>
    <div class="main-container" slot="time">
        {#each priorities as p, i}
            <button class="filter-btn{p.active ? '--active' : ''}"
                    on:click={() => {priority = i + 1; dispatch('close')}}>
                <Icon icon={p.icon} style="width: 20px; height: 20px; color: {p.color};"/>
                <span>{"Приоритет - " + p.priority}</span>
            </button>
        {/each}
    </div>
</SchedulerWindow>
