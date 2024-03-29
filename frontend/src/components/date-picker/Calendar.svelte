<script lang="ts">

    import AttractionsCalendar from "./attractions-calendar/AttractionsCalendar.svelte";
    import dayjs from "dayjs";
    import {ArrowLeft, ArrowRight} from "carbon-icons-svelte";
    import MonthYearComponent from "./date-picker/MonthYearComponent.svelte";
    import {onMount} from "svelte";
    import Button from "../button/Button.svelte";

    export let value: Date = new Date();
    export let changeMonthDuration: number = 200;
    export let disabledDates: Array<Date | { start?: Date, end?: Date }> = [{end: dayjs().add(-1, 'day').toDate()}];
    export let minDate: Date | null = null;
    export let maxDate: Date | null = null;

    let date: Date = value < minDate ? new Date() : value;
    let prev: Date;
    let next: Date;

    let showNext: boolean = false;
    let showPrev: boolean = false;

    let lastOnLeft: boolean;
    let lastOnRight: boolean;

    let changing: boolean = false;

    const dateEquals = (date1: Date, date2: Date): boolean => {
        return date1.getMonth() === date2.getMonth() && date1.getFullYear() === date2.getFullYear();
    }

    const checkLastMonths = () => {
        lastOnLeft = minDate !== null && dateEquals(date, minDate);
        lastOnRight = maxDate !== null && dateEquals(date, maxDate);
    }

    const changeMonth = (next: boolean) => {
        if (changing) return;
        changing = true;

        if (next && lastOnRight) return;
        if (!next && lastOnLeft) return;

        next ? showNext = true : showPrev = true;

        setTimeout(() => {
            date = dayjs(date).add(next ? 1 : -1, 'month').toDate();
            checkLastMonths();
            showNext = false;
            showPrev = false;
            changing = false;
        }, changeMonthDuration);
    }

    $:{
        prev = dayjs(date).add(-1, 'month').toDate();
        next = dayjs(date).add(1, 'month').toDate();
        checkLastMonths();
    }

    onMount(() => {
        date = value < minDate ? new Date() : value;
        checkLastMonths();
    })


</script>


<div class="calendar">
    <div class="calendar-header">
        <Button iconDescription={dayjs(prev).format("MMMM")}
                icon={ArrowLeft}
                on:click={() => changeMonth(false)}
                disabled={lastOnLeft}
                size="small"
                --ag-bnt-padding="0 7px"
        />
        <MonthYearComponent bind:value={date} minValue={minDate}/>
        <Button iconDescription={dayjs(next).format("MMMM")}
                icon={ArrowRight}
                on:click={() => changeMonth(true)}
                disabled={lastOnRight}
                size="small"
                --ag-bnt-padding="0 7px"
        />
    </div>
    <div class="calendar-holder">
        <div class="month-holder">
            <div class="month-inner" class:next={showNext} class:prev={showPrev}
                 style="--calendar-change-month-duration: {changeMonthDuration}ms">
                <div class="calendar-month">
                    <AttractionsCalendar month={prev.getMonth()} year={prev.getFullYear()}
                                         {disabledDates}
                    />
                </div>
                <div class="calendar-month">
                    <AttractionsCalendar month={date.getMonth()} year={date.getFullYear()}
                                         {disabledDates}
                                         bind:selected={value}
                    />
                </div>
                <div class="calendar-month">
                    <AttractionsCalendar month={next.getMonth()} year={next.getFullYear()}
                                         {disabledDates}
                    />
                </div>
            </div>
        </div>
    </div>
</div>


<style>

    :root {
        --calendar-header-height: 2rem;
        --calendar-header-btn-size: 1.5rem;
        --calendar-change-month-duration: .2s;
    }

    .calendar {
        width: 100%;
        height: 100%;
        max-width: 274px;
    }

    .calendar-header {
        width: 100%;
        height: var(--calendar-header-height);

        display: flex;
        justify-content: space-around;
        align-items: center;
    }

    .calendar-holder {
        display: flex;
        flex-direction: row;

        position: relative;

        width: 100%;
        height: calc(100% - var(--calendar-header-height));
    }

    .month-holder {
        width: 100%;
        height: 100%;

        overflow: hidden;
    }

    .month-inner {
        display: flex;
        flex-direction: row;
        transform: translateX(-100%);

    }

    .month-inner.next {
        transition: transform var(--calendar-change-month-duration) linear;
        transform: translateX(-200%);
    }

    .month-inner.prev {
        transition: transform var(--calendar-change-month-duration) linear;
        transform: translateX(0);
    }

    .calendar-month {
        width: 100%;
        height: 100%;
    }

</style>