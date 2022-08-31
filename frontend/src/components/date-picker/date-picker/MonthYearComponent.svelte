<script lang="ts">

    import dayjs from "dayjs";
    import Popover from "../../popover/Popover.svelte";
    import MonthYearPicker from "./MonthYearPicker.svelte";

    export let value: Date;
    export let minValue: Date = undefined;
    export let maxValue: Date = undefined;

</script>

<style>

    .month-year-label {
        padding: .5rem;
        height: 2rem;
        min-width: 8rem;
        background: var(--cds-button-secondary);
        display: flex;
        align-items: center;
        justify-content: center;
        color: var(--cds-text-04);
    }

    .month-year-label:focus-visible {
        outline: var(--cds-focus) solid 1px;
        background-color: var(--cds-hover-ui);
    }

    .month-year-label:hover {
        background: var(--cds-button-secondary-hover);
    }

    .month-year-label:active {
        background: var(--cds-button-secondary-active);
    }

</style>

<div>
    <Popover>
        <div slot="target">
            <div class="month-year-label" tabindex="1">
                {dayjs(value).format("MMMM YYYY")}
            </div>
        </div>
        <div slot="content">
            <MonthYearPicker bind:value {minValue} {maxValue}/>
        </div>
    </Popover>
</div>