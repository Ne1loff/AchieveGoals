<script lang="ts">

    import TimePicker from "./TimePicker.svelte";
    import Popover from "../../popover/Popover.svelte";
    import Time from "carbon-icons-svelte/lib/Time.svelte";
    import Button from "../../button/Button.svelte";

    export let value: Date;

</script>

<div>
    <Popover>
        <div slot="target">
            <Button
                    kind="secondary"
                    size="field"
                    iconDescription="Выбрать время"
                    --ag-bnt-border-radius="0 .25rem .25rem 0"
            >
                <Time/>
            </Button>
        </div>
        <div slot="content">
            <TimePicker bind:value/>
        </div>
    </Popover>
</div>