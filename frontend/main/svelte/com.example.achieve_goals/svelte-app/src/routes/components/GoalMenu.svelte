<script lang="ts">
    import SchedulerWindow from "../ModalWindows/SchedulerWindow.svelte";
    import Scheduler from "./Scheduler.svelte";
    import Icon from "@iconify/svelte";
    import dayjs from "dayjs";

    import {createEventDispatcher} from 'svelte';

    const dispatch = createEventDispatcher();

    export let bounding;
    export let goal;
    export let fromGoalCard = true;

    let showScheduler;
    let menuBounding;

    const dates = {
        today: new Date(),
        todayNum: dayjs().format("DD"),
        tomorrow: dayjs().add(1, 'day').toDate(),
        weekends: dayjs().set('day', 6).toDate(),
        nextWeek: dayjs().add(1, 'week').toDate()
    }

    const newGoal = () => {
        dispatch('newGoal');
        close();
    }
    const newSub = () => {
        dispatch('newSub');
        close();
    }
    const edit = () => {
        dispatch('edit')
        close();
    }
    const deleteGoal = () => {
        dispatch('delete');
        close();
    }
    const close = () => {
        dispatch('close')
    }
</script>

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

        display: flex;
        align-items: center;

        background: #fff;
        border: none;

        color: #000;
        font-size: 14px;
        cursor: pointer;
    }

    .actions-btn:hover, .menu-delete-btn:hover {
        background: #f0f0f0;
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

    .parameters-holder-btn, .parameters-holder-btn--selected {
        width: 28px;
        height: 28px;

        padding: 0;
        margin: 0 12px 0 0;

        background: #fff;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .parameters-holder-btn--selected {
        border: 1px solid #e0e0e0;
    }

    .parameters-holder-btn--selected:hover, .parameters-holder-btn:hover {
        background: #f0f0f0;
    }

    .parameters-holder-btn--selected span, .parameters-holder-btn span {
        width: 18px;
        height: 18px;

        position: absolute;
        transform: translate(-18px, 5px);

        color: #058527;
        font-size: 12px;
        font-weight: 400;
    }

</style>

<SchedulerWindow on:close={close} bind:bounding bind:anotherModal={showScheduler} fromGoalCard={fromGoalCard}>
    <div class="menu-actions" slot="header">
        <button class="actions-btn" on:click={newGoal}>
            <Icon icon="bx:bx-arrow-to-top" style="width: 20px; height: 20px"/>
            <span>Доавить новую цель</span>
        </button>
        <button class="actions-btn" on:click={newSub}>
            <Icon icon="bx:bx-arrow-to-bottom" style="width: 20px; height: 20px"/>
            <span>Добавиь подзадачу</span>
        </button>
        <button class="actions-btn" on:click={edit}>
            <Icon icon="feather:edit-3" style="width: 16px; height: 16px"/>
            <span>Редактировать</span>
        </button>
    </div>
    <div class="menu-edit" slot="suggestion">
        <div class="menu-edit-parameters">
            <div class="edit-parameters-title">
                Срок
            </div>
            <div class="edit-parameters-holder">
                <button class="parameters-holder-btn" on:click={() => goal.deadline = dates.today}>
                    <Icon icon="bi:calendar" style="width: 20px; height: 20px; color: #058527;"/>
                    <span>{dayjs().format("DD")}</span>
                </button>
                <button class="parameters-holder-btn" on:click={() => goal.deadline = dates.tomorrow}>
                    <Icon icon="bi:sun" style="width: 22px; height: 22px; color: #ad6200;"/>
                </button>
                <button class="parameters-holder-btn" on:click={() => goal.deadline = dates.weekends}>
                    <Icon icon="mdi:sofa-outline" style="width: 24px; height: 24px; color: #246fe0;"/>
                </button>
                <button class="parameters-holder-btn" on:click={() => goal.deadline = dates.nextWeek}>
                    <Icon icon="gg:calendar-next" style="width: 24px; height: 24px; color: #692fc2;"/>
                </button>
                <div class="void"></div>
                <button class="parameters-holder-btn" bind:this={menuBounding} on:click={() => showScheduler = true}>
                    <Icon icon="entypo:dots-three-horizontal"/>
                </button>
            </div>
        </div>
        <div class="menu-edit-parameters">
            <div class="edit-parameters-title">
                Приоритет
            </div>
            <div class="edit-parameters-holder">
                <button class="parameters-holder-btn{goal.priority === 1 ? '--selected' : ''}"
                        on:click={() => goal.priority = 1}>
                    <Icon icon="bi:flag-fill" style="width: 20px; height: 20px; color: #de4c4a;"/>
                </button>
                <button class="parameters-holder-btn{goal.priority === 2 ? '--selected' : ''}"
                        on:click={() => goal.priority = 2}>
                    <Icon icon="bi:flag-fill" style="width: 20px; height: 20px; color: #f49c18;"/>
                </button>
                <button class="parameters-holder-btn{goal.priority === 3 ? '--selected' : ''}"
                        on:click={() => goal.priority = 3}>
                    <Icon icon="bi:flag-fill" style="width: 20px; height: 20px; color: #3077e1;"/>
                </button>
                <button class="parameters-holder-btn{goal.priority === 4 ? '--selected' : ''}"
                        on:click={() => goal.priority = 4}>
                    <Icon icon="bi:flag" style="width: 20px; height: 20px;"/>
                </button>
            </div>
        </div>
    </div>
    <div class="menu-delete" slot="time">
        <button class="menu-delete-btn"
                on:click={() => {
                    if(confirm("Вы действительно хотите удалить цель?")) deleteGoal();
                    else close();
                }}>
            <Icon icon="bi:trash"/>
            <span>Удалить цель</span>
        </button>
    </div>
</SchedulerWindow>
{#if showScheduler}
    <Scheduler bind:bounding={menuBounding} bind:goal isCreate={false} fromGoalCard={fromGoalCard}
               on:close={() => showScheduler = false}/>
{/if}
