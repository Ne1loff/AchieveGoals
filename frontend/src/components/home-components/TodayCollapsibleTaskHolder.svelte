<script lang="ts">
    import Accordion from "../accordion/Accordion.svelte";
    import Icon from "@iconify/svelte";
    import SvgProgressBar from "../svg/SvgProgressBar.svelte";
    import GoalComponent from "../goal-card/GoalComponent.svelte";
    import Goal from "../../data/models/Goal";

    export let contents: HTMLElement;
    export let goals: Goal[] = [];

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
                    <span>Просрочено</span>
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
                <button class="hide-completed-button">
                    <span>Перенести</span>
                </button>
            {/if}
        </div>
    </svelte:fragment>
    <svelte:fragment>
        {#each goals as goal}
            <GoalComponent bind:goal withoutSubs
                           --own-component-width="calc(100% - 55px)"
                           style="--own-component-border-width: calc(100% + 55px);
                           --own-component-border-right: 28px;"
            />
        {/each}
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