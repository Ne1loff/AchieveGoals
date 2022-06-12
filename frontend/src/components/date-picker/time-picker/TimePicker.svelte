<script lang="ts">
    import {onDestroy} from "svelte";
    import Picker from "../../picker/Picker.svelte";
    import PickerColumn from "../../picker/PickerColumn.svelte";
    import PickerColumnDivider from "../../picker/PickerColumnDivider.svelte";

    export let value: Date = new Date();

    const hours = Array(24).fill(0).map((_, i) => i).map(it => it < 10 ? `0${it}` : `${it}`);
    const minutes = Array(60).fill(0).map((_, i) => i).map(it => it < 10 ? `0${it}` : `${it}`);

    let hour: number = value.getHours();
    let minute: number = value.getMinutes();

    onDestroy(() => {
        value.setHours(Math.round(hour), Math.round(minute))
        value = value;
    })

</script>

<div class="time_picker">
    <Picker>
        <PickerColumn data={hours} bind:selected={hour}/>
        <PickerColumnDivider/>
        <PickerColumn data={minutes} bind:selected={minute}/>
    </Picker>
</div>

<style>

    :global(:root) {
        --own-timepicker-height: 240px;
        --own-timepicker-width: 240px;
        --own-picker-item-size: 2rem;
        --own-picker-scroll-padding: 122px;
        --own-picker-item-height: 2rem;
    }

    .time_picker {
        height: var(--own-timepicker-height);
        width: var(--own-timepicker-width);

        position: relative;
        -webkit-user-select: none;
        user-select: none;
        background: var(--cds-field);
        border-radius: 10px;
    }

</style>