<script lang="ts">

    import CollapsibleTaskHolder from "../../home-components/CollapsibleTaskHolder.svelte";
    import Goal from "../../../data/models/Goal";
    import Button from "../../button/Button.svelte";
    import GoalComponent from "../GoalComponent.svelte";
    import GoalCreator from "../GoalCreator.svelte";
    import SubtaskCreator from "./SubtaskCreator.svelte";

    export let goals: Goal[] = [];
    export let hideGoalCreator: boolean = false;

    let completedGoals: Goal[];
    let showCompleted: boolean = false;
</script>

{#if goals.length > 0}
    <CollapsibleTaskHolder {goals} showOnlyUncompleted title="Подзадачи">
        <svelte:fragment slot="action" let:completedGoalsLength>
            {#if (completedGoalsLength > 0)}
                <Button kind="ghost" size="small"
                        --ag-bnt-border-radius=".5rem"
                        on:click={() => showCompleted = !showCompleted}
                >
                    <span>{showCompleted ? "Скрыть" : "Показать"} выполненные</span>
                </Button>
            {/if}
        </svelte:fragment>
        <svelte:fragment slot="after-main-goals" let:completedGoals={completedGoals}>
            {#if (!hideGoalCreator)}
                <GoalCreator bind:taskStorage={goals}/>
            {/if}
            {#if (showCompleted)}
                {#each completedGoals as goal}
                    <GoalComponent bind:goal withoutSubs
                                   style="--own-component-border-width: calc(100% + 55px);
                           --own-component-border-right: 28px;"
                    />
                {/each}
            {/if}
        </svelte:fragment>
    </CollapsibleTaskHolder>
{:else if (!hideGoalCreator)}
    <SubtaskCreator/>
{/if}

