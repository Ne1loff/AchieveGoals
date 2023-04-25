<script lang="ts">

    import CollapsibleTaskHolder from "../../home-components/CollapsibleTaskHolder.svelte";
    import Task from "../../../data/models/Task";
    import Button from "../../button/Button.svelte";
    import GoalComponent from "../GoalComponent.svelte";
    import GoalCreator from "../GoalCreator.svelte";
    import SubtaskCreator from "./SubtaskCreator.svelte";

    export let goals: Task[] = [];
    export let hideGoalCreator: boolean = false;

    let completedTasks: Task[] = [];
    let showCompleted: boolean = false;
</script>

{#if goals.length > 0}
    <CollapsibleTaskHolder {goals} bind:completedGoals={completedTasks} showOnlyUncompleted title="Подзадачи">
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
        <svelte:fragment slot="after-main-goals">
            {#if (!hideGoalCreator)}
                <GoalCreator bind:taskStorage={goals}/>
            {/if}
            {#if (showCompleted)}
                {#each completedTasks as goal (goal.id)}
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

