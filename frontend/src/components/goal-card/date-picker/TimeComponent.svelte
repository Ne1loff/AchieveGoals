<script lang="ts">

    import {TimePicker} from "attractions";
    import dayjs from "dayjs";

    export let value: Date;

    const hours = Array(24).fill(0).map((_, i) => i);
    const minutes = Array(60).fill(0).map((_, i) => i);

    let time;
    //$:value = dayjs(value).set('hour', time?.getHours()).set('minute', time?.getMinutes()).toDate();

</script>

<style>

</style>

<div>
    <TimePicker bind:value={time} {hours} {minutes} hideNow/>
</div>