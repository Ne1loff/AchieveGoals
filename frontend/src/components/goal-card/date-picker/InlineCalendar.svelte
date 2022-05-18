<script lang="ts">
    import Flatpickr from 'svelte-flatpickr';
    import dayjs from "dayjs";

    export let value: Date;
    export let withTime: boolean = false;

    const options = {
        enableTime: withTime,
        inline: true,
        time_24hr: true,
        "locale": {
            "firstDayOfWeek": 1
        },
        disable: [
            function (date) {
                return date < dayjs().add(-1, 'd').toDate();
            }
        ]
    }

</script>

<style>

    :global(:root) {
        --f7-toolbar-height: 2rem;
        --f7-calendar-height: 240px;
        --f7-calendar-day-size: 1.5rem;
    }

    .calendar-container {
        width: 100%;
        height: 100%;
        position: relative;
        background-color: #fff;
    }

    .data-input {
        display: none;
    }
</style>

<div class="calendar-container">
    <Flatpickr {options} bind:value element="#datepicker">
        <div class="flatpickr" id="datepicker">
            <input class="data-input" type="text" data-input>
        </div>
    </Flatpickr>
</div>