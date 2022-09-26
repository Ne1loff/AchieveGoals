<script lang="ts">
    import OverflowMenuVertical from "carbon-icons-svelte/lib/OverflowMenuVertical.svelte";
    import Icon from "@iconify/svelte";
    import Goal from "../../data/models/Goal";
    import AppHeadTitle from "../AppHeadTitle.svelte";
    import GoalComponent from "../goal-card/GoalComponent.svelte";
    import GoalCreator from "../goal-card/GoalCreator.svelte";
    import HomeComponentsHolder from "./component-holder/HomeComponentsHolder.svelte";
    import Button from "../button/Button.svelte";

    export let goals: Goal[] = [];
    export let goalProps: { [key: string]: any } = {};
    export let goalStyle: string[] = [];

    let showCompleted: boolean = false;

</script>

<AppHeadTitle text={'Goals'}/>
<HomeComponentsHolder {...$$props}>
    <svelte:fragment slot="head">
        <h3>Goals</h3>
        <div class="header-actions">
            <Button kind="ghost" size="small"
                    --ag-bnt-margin="0 .125rem 0 0"
                    --ag-bnt-border-radius=".5rem"
            >
                <span class="button-span-icon">
                    <Icon icon="akar-icons:settings-horizontal"
                          width="18" height="18" color="var(--cds-icon-01)"/>
                </span>
                <span class="button-span">Отображение</span>
            </Button>
            <Button kind="ghost" size="small"
                    --ag-bnt-padding="0 3px"
                    --ag-bnt-border-radius=".5rem"
            >
                <span class="button-span-icon">
                    <OverflowMenuVertical size="18"/>
                </span>
            </Button>
        </div>
    </svelte:fragment>
    <svelte:fragment>
        {#each goals.filter(it => !it.isDone) as goal (goal.id)}
            <GoalComponent bind:goal {...goalProps} style={goalStyle.join('')}/>
        {/each}
        <GoalCreator bind:taskStorage={goals}/>
        {#if showCompleted}
            {#each goals.filter(it => it.isDone) as goal (goal.id)}
                <GoalComponent bind:goal {...goalProps} style={goalStyle.join('')}/>
            {/each}
        {/if}
    </svelte:fragment>
</HomeComponentsHolder>


<style>

    :root {
        --holder-padding-left: 28px;
        --holder-padding-right: 55px;
    }

    .button-span {
        margin-left: 3px;
        margin-right: 6px;

        color: var(--cds-text-02);
    }

    .button-span-icon {
        height: 1.5rem;
        width: 1.5rem;
        display: flex;
        justify-content: center;
        align-items: center;
        color: var(--cds-icon-01);
    }

    .header-actions {
        display: flex;
    }

</style>