<script lang="ts">
    import Calendar from "./Calendar.svelte";
    import dayjs from "dayjs";

    export let value: Date = new Date();
    export let withTime: boolean = false;

</script>

<style>

    .calendar-container {
        width: 100%;
        height: 100%;
        position: relative;
        background-color: var(--cds-field);
    }

</style>

<div class="calendar-container">
    <Calendar bind:value minDate={dayjs().add(-1, 'd').toDate()}/>
</div>