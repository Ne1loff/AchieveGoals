<script lang="ts">
    import Icon from "@iconify/svelte";
    import Goal from "../../data/models/Goal";
    import AppHeadTitle from "../AppHeadTitle.svelte";
    import GoalComponent from "../goal-card/GoalComponent.svelte";
    import GoalCreator from "../goal-card/GoalCreator.svelte";
    import HomeComponentsHolder from "./component-holder/HomeComponentsHolder.svelte";
    import {getDateTodayLastMin} from "../../utils/time-utils";
    import dayjs from "dayjs";
    import {GOALS} from "../../data/storage/storage";
    import OverdueTaskHolder from "./OverdueTaskHolder.svelte";
    import Button from "../button/Button.svelte";


    let todayDayjs = dayjs(getDateTodayLastMin());
    let yesterdayDayjs = todayDayjs.add(-1, 'day');

    let overdueTasks: Goal[];
    let tasksForToday: Goal[];

    $: overdueTasks = $GOALS.filter((it) => it.deadline <= yesterdayDayjs.toDate());
    $: tasksForToday = $GOALS.filter((it) =>
        it.deadline <= todayDayjs.toDate() && it.deadline > yesterdayDayjs.toDate());

</script>

<AppHeadTitle text={'Today'}/>
<HomeComponentsHolder>
    <svelte:fragment slot="head">
        <div class="head-title">
            <h3>Today</h3>
            <h5>{todayDayjs.format("dd D MMM")}</h5>
        </div>
        <div class="header-actions">
            <Button kind="ghost" size="small"
                    --ag-bnt-border-radius=".5rem"
            >
                <span class="button-span-icon">
                    <Icon icon="akar-icons:settings-horizontal"
                          width="18" height="18" color="var(--cds-icon-01)"/>
                </span>
                <span class="button-span">Отображение</span>
            </Button>
        </div>
    </svelte:fragment>
    <svelte:fragment>
        <div class="section">
            <OverdueTaskHolder goals={overdueTasks}/>
        </div>
        <div class="section">
            {#each tasksForToday as goal}
                <GoalComponent bind:goal/>
            {/each}
            <GoalCreator bind:taskStorage={tasksForToday}/>
        </div>
    </svelte:fragment>
</HomeComponentsHolder>


<style>

    :root {
        --holder-padding-left: 28px;
        --holder-padding-right: 55px;
    }

    .head-title {
        display: flex;
        align-items: baseline;
    }

    .head-title h5 {
        margin-left: .25rem;
        font-weight: inherit;
    }

    .section {
        margin: .5rem 0;
    }


    .button-span {
        margin-left: 3px;
        margin-right: 6px;

        color: var(--cds-text-01);
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