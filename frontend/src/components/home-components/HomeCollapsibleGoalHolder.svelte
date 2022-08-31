<script lang="ts">
    import Accordion from "../accordion/Accordion.svelte";
    import Icon from "@iconify/svelte";
    import SvgProgressBar from "../svg/SvgProgressBar.svelte";
    import GoalComponent from "../goal-card/GoalComponent.svelte";
    import GoalCreator from "../goal-card/GoalCreator.svelte";
    import Goal from "../../data/models/Goal";

    export let contents: HTMLElement;

    export let goals: Goal[] = [];
    export let goalProps: { [key: string]: any } = {};
    export let goalStyle: string[] = [];

    let showCompleted: boolean;
    let totalGoals: number;
    let completedGoals: number;

    $:totalGoals = goals.length;
    $:completedGoals = goals.filter(it => it.isDone).length;

    let expanded: boolean = true;

</script>

<Accordion bind:open={expanded}>
    <svelte:fragment slot="header" let:toggle>
        <div class="action-buttons">
            <button class="collapse-button"
                    on:click={toggle}>
                <span>
                    <span class="icon-holder" data-item-rotate={expanded ? '90' : '0'}>
                        <Icon icon="uil:angle-right-b"
                              width="24" height="24"
                              color="color: var(--cds-icon-01)"
                        />
                    </span>
                    <span>Подзадачи</span>
                    <span class="subtask-info">
                        {#if completedGoals !== 0}
                            <span class="progress">
                                <SvgProgressBar value={completedGoals / totalGoals * 100}
                                                size={16}
                                                innerRadius={90}
                                                outerRadius={100}
                                                --progress-bar-outer-stroke-width="0"
                                                --progress-bar-inner-color="var(--cds-border-subtle)"
                                />
                            </span>
                        {/if}
                        <span>{completedGoals}/{totalGoals}</span>
                    </span>
                </span>
            </button>
            {#if completedGoals > 0 && expanded}
                <button class="hide-completed-button" on:click={() => showCompleted = !showCompleted}>
                    <span>{showCompleted ? 'Скрыть' : 'Показать'} выполненные</span>
                </button>
            {/if}
        </div>
    </svelte:fragment>
    <svelte:fragment>
        <div class="content-wrapper">
            <div class="goals-content">
                <div class="content-inner" bind:this={contents}>
                    {#each goals.filter(it => !it.isDone) as goal}
                        <GoalComponent bind:goal {...goalProps} style={goalStyle.join('')}/>
                    {/each}
                    <GoalCreator bind:taskStorage={goals}/>
                    {#if showCompleted}
                        {#each goals.filter(it => it.isDone) as goal}
                            <GoalComponent bind:goal {...goalProps} style={goalStyle.join('')}/>
                        {/each}
                    {/if}
                </div>
            </div>
        </div>
    </svelte:fragment>
</Accordion>

<style>

    .action-buttons {
        position: relative;
        width: 100%;
        margin: .25rem 0;

        display: flex;
        align-items: center;
        justify-content: space-between;


        color: var(--cds-text-01);
        background-color: inherit;
    }

    .icon-holder {
        transition: transform .1s linear;

        display: flex;
        align-items: center;
        justify-content: center;
    }

    .icon-holder[data-item-rotate="90"] {
        transform: rotate(90deg);
    }

    .action-buttons::after {
        position: absolute;
        left: 0;
        bottom: 0;
        content: "";
        margin-left: 29px;

        width: calc(100% - 29px);
        border: 0;
        border-bottom: 1px solid var(--cds-border-subtle);
    }

    .subtask-info {
        margin-left: .25rem;
        color: var(--cds-text-05);
    }

    .progress {
        border-radius: 50%;
        border: 1px solid var(--cds-border-subtle);
        padding: 0;
        margin: 0 .25rem 0 .25rem;
    }

    .collapse-button {
        flex-grow: 1;
        height: 40px;
    }

    .collapse-button span {
        display: flex;
        align-items: center;
        justify-content: flex-start;
    }

    .collapse-button, .hide-completed-button {
        cursor: pointer;

        border: none;
        color: var(--cds-text-01);
        background-color: inherit;
    }

    .hide-completed-button {
        padding: .5rem;
        min-width: 11rem;
        border-radius: 5px;
    }

    .hide-completed-button:hover {
        background-color: var(--cds-field-hover);
    }

</style>