<script lang="ts">
    import {Button} from "carbon-components-svelte";
    import Icon from "@iconify/svelte";
    import Goal from "../../data/models/Goal";
    import AppHeadTitle from "../AppHeadTitle.svelte";
    import GoalComponent from "../goal-card/GoalComponent.svelte";
    import GoalCreator from "../goal-card/GoalCreator.svelte";
    import HomeComponentsHolder from "./component-holder/HomeComponentsHolder.svelte";
    import {getDateTodayLastMin} from "../../utils/time-utils";
    import dayjs from "dayjs";
    import {GOALS} from "../../data/storage/storage";
    import TodayCollapsibleTaskHolder from "./TodayCollapsibleTaskHolder.svelte";


    let todayDayjs = dayjs(getDateTodayLastMin());
    let yesterdayDayjs = todayDayjs.add(-1, 'day');
    let tasksForToday: Goal[] = [];

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
            <Button kind="ghost" size="small" as let:props>
                <p {...props} class:button-p={true}>
                    <span class="button-span-icon">
                        <Icon icon="akar-icons:settings-horizontal"
                              width="18" height="18" color="var(--cds-icon-01)"/>
                    </span>
                    <span class="button-span">Отображение</span>
                </p>
            </Button>
        </div>
    </svelte:fragment>
    <svelte:fragment>
        <div class="section">
            <TodayCollapsibleTaskHolder goals={overdueTasks}/>
        </div>
        <div class="section">
            <div class="section-header">
                <div class="header-title">
                    Просрочено
                </div>
                <button>
                    Перенести
                </button>
            </div>
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

    .button-p {
        border-radius: 5px;
        min-width: 2rem;
        padding: 0 !important;
        justify-content: center;
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