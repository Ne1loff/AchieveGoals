<script lang="ts">
    import SchedulerModal from "../modals/SchedulerModal.svelte";
    import Scheduler from "./SchedulerComponent.svelte";
    import Icon from "@iconify/svelte";
    import dayjs from "dayjs";
    import {popoverTrigger} from "../popover/global/Popover";
    import Goal from "../../data/models/Goal";
    import {GOALS} from "../../data/storage/storage";

    export let goalId: number;

    const goal: Goal = $GOALS.find((it) => it.id === goalId);

    const dates = {
        today: new Date(),
        todayNum: dayjs().format("DD"),
        tomorrow: dayjs().add(1, 'day').toDate(),
        weekends: dayjs().set('day', 6).toDate(),
        nextWeek: dayjs().add(7, 'day').toDate(),
    }

    const updateGoals = () => {
        const goals = $GOALS;
        goals[goals.indexOf(goal)] = goal;
        $GOALS = goals;
    }

    $:{
        if (goal) {
            updateGoals();
        }
    }

</script>

<SchedulerModal on:close={close}>
    <div class="menu-actions" slot="header">
        <button class="actions-btn">
            <Icon icon="bx:bx-arrow-to-top" width="20" height="20"/>
            <span>Доавить новую цель</span>
        </button>
        <button class="actions-btn">
            <Icon icon="bx:bx-arrow-to-bottom" width="20" height="20"/>
            <span>Добавиь подзадачу</span>
        </button>
        <button class="actions-btn">
            <Icon icon="feather:edit-3" width="16" height="16"/>
            <span>Редактировать</span>
        </button>
    </div>
    <div class="menu-edit" slot="content">
        <div class="menu-edit-parameters">
            <div class="edit-parameters-title">
                <h6>Срок</h6>
            </div>
            <div class="edit-parameters-holder">
                <button class="parameters-holder-btn" on:click={() => goal.deadline = dates.today}>
                    <Icon icon="bi:calendar"
                          style="width: 20px; height: 20px; color: var(--cds-support-02);"/>
                    <span>{dayjs().format("DD")}</span>
                </button>
                <button class="parameters-holder-btn" on:click={() => goal.deadline = dates.tomorrow}>
                    <Icon icon="bi:sun"
                          style="width: 22px; height: 22px; color: var(--cds-support-03);"/>
                </button>
                <button class="parameters-holder-btn" on:click={() => goal.deadline = dates.weekends}>
                    <Icon icon="mdi:sofa-outline"
                          style="width: 24px; height: 24px; color: var(--cds-link-inverse);"/>
                </button>
                <button class="parameters-holder-btn" on:click={() => goal.deadline = dates.nextWeek}>
                    <Icon icon="gg:calendar-next"
                          style="width: 24px; height: 24px; color: var(--cds-link-visited);"/>
                </button>
                <div class="void"></div>
                <div class="parameters-holder-btn"
                     use:popoverTrigger={{
                        component: {
                            src: Scheduler,
                            props: {
                                goalId: goal.id
                            }
                        },
                        classStyle: 'elevation-6'
                    }}
                >
                    <Icon icon="entypo:dots-three-horizontal"
                          color="var(--cds-icon-01)"
                    />
                </div>
            </div>
        </div>
        <div class="menu-edit-parameters">
            <div class="edit-parameters-title">
                <h6>Приоритет</h6>
            </div>
            <div class="edit-parameters-holder">
                <button class="parameters-holder-btn" class:selected={goal.priority === 1}
                        on:click={() => goal.priority = 1}>
                    <Icon icon="bi:flag-fill"
                          style="width: 20px; height: 20px; color: var(--cds-inverse-support-01);"/>
                </button>
                <button class="parameters-holder-btn" class:selected={goal.priority === 2}
                        on:click={() => goal.priority = 2}>
                    <Icon icon="bi:flag-fill"
                          style="width: 20px; height: 20px; color: var(--cds-inverse-support-03);"/>
                </button>
                <button class="parameters-holder-btn" class:selected={goal.priority === 3}
                        on:click={() => goal.priority = 3}>
                    <Icon icon="bi:flag-fill"
                          style="width: 20px; height: 20px; color: var(--cds-inverse-support-04)"/>
                </button>
                <button class="parameters-holder-btn" class:selected={goal.priority === 4}
                        on:click={() => goal.priority = 4}>
                    <Icon icon="bi:flag"
                          style="width: 20px; height: 20px; color: var(--cds-icon-01);"/>
                </button>
            </div>
        </div>
    </div>
    <div class="menu-delete" slot="footer">
        <button class="menu-delete-btn">
            <Icon icon="bi:trash"/>
            <span>Удалить цель</span>
        </button>
    </div>
</SchedulerModal>

<style>
    .menu-actions, .menu-delete {
        margin: 4px 0;
        width: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
    }

    .actions-btn, .menu-delete-btn {
        width: 100%;
        margin: 0;
        padding: 8px;
        box-sizing: border-box;

        background: var(--cds-field);

        display: flex;
        align-items: center;
        border: none;

        color: var(--cds-text-01);
        cursor: pointer;
    }

    .actions-btn:hover, .menu-delete-btn:hover {
        background: var(--cds-field-hover);
    }

    .menu-delete-btn:hover {
        color: #e00
    }

    .actions-btn span, .menu-delete-btn span {
        margin-left: 8px;
    }

    .menu-edit {
        width: 100%;

        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .menu-edit-parameters {
        width: 100%;
        margin: 4px 0;
        padding: 0 8px;
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
    }

    .edit-parameters-title {
        margin-bottom: 8px;
        font-size: var(--cds-label-01-font-size);
    }

    .edit-parameters-holder {
        margin: 0;
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: flex-start;
        padding: 5px;
        box-sizing: border-box;
    }

    .parameters-holder-btn, .parameters-holder-btn.selected {
        width: 28px;
        height: 28px;

        padding: 0;
        margin: 0 12px 0 0;

        background: var(--cds-field);

        display: flex;
        justify-content: center;
        align-items: center;
        position: relative;

        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .parameters-holder-btn.selected {
        border-color: var(--cds-focus, #0f62fe);
        box-shadow: inset 0 0 0 1px var(--cds-focus, #0f62fe), inset 0 0 0 2px var(--cds-ui-background, #ffffff);
    }

    .parameters-holder-btn.selected:hover, .parameters-holder-btn:hover {
        background: var(--cds-field-hover);
    }


    .parameters-holder-btn.selected span, .parameters-holder-btn span {
        width: 18px;
        height: 18px;

        position: absolute;

        top: 50%;
        left: 50%;

        transform: translate(-46%, -35%);

        color: #058527;
        font-size: 12px;
        font-weight: 400;
    }

</style>