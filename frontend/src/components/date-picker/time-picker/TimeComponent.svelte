<script lang="ts">

    import dayjs from "dayjs";
    import TimePicker from "./TimePicker.svelte";
    import Popover from "../../popover/Popover.svelte";
    import {Button} from "carbon-components-svelte";
    import Time from "carbon-icons-svelte/lib/Time.svelte";

    export let value: Date;

</script>

<div>
    <Popover>
        <div slot="target">
            <Button
                    kind="secondary"
                    size="field"
                    icon={Time}
            >{dayjs(value).format("HH:mm")}</Button>
        </div>
        <div slot="content">
            <TimePicker bind:value/>
        </div>
    </Popover>
</div>