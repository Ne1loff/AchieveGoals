<script lang="ts">
    import Calendar from "./Calendar.svelte";
    import dayjs from "dayjs";
    import TimeComponent from "./time-picker/TimeComponent.svelte";

    export let value: Date = new Date();
    export let minDate: Date = dayjs().add(-1, 'd').toDate();
    export let withTime: boolean = false;

</script>

<style>

    .calendar-container {
        width: 100%;
        height: 100%;
        position: relative;
        background-color: var(--cds-field);
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    .time-holder {
        width: 100%;
        height: 3rem;

        display: flex;
        flex-direction: row;
        align-items: center;
        padding: 12px .25rem 12px 16px;
    }

    .time-label {
        flex-grow: 1;
    }

</style>

<div class="calendar-container">
    <Calendar bind:value {minDate}/>
    {#if withTime}
        <div class="time-holder">
            <div class="time-label">
                <span>Время:</span> <!-- TODO: l10n -->
            </div>
            <TimeComponent bind:value/>
        </div>
    {/if}
</div>