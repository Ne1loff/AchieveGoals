<script lang="ts">
    import CollapsibleTaskHolder from "./CollapsibleTaskHolder.svelte";
    import Button from "../button/Button.svelte";
    import Goal from "../../data/models/Goal";

    export let goals: Goal[] = [];

</script>
<CollapsibleTaskHolder {goals} title="Просрочено">
    <svelte:fragment slot="action">
        <Button kind="ghost" size="small"
                --ag-bnt-border-radius=".5rem"
        >
            <span>Перенести</span>
        </Button>
    </svelte:fragment>
</CollapsibleTaskHolder>