<script lang="ts">
    import AbstractTaskCreator from "../AbstractTaskCreator.svelte";
    import Button from "../../button/Button.svelte";
    import Icon from "@iconify/svelte";
    import Task from "../../../data/models/Task";

    export let taskStorage: Task[];
</script>

<AbstractTaskCreator {taskStorage}>
    <svelte:fragment slot="create-button" let:create>
        <div class="create-subtask-button-holder">
            <Button kind="ghost" size="small"
                    --ag-bnt-border-radius=".5rem"
                    on:click={create}
            >
                <Icon icon="carbon:add" width="24" height="24"/>
                <span>Добавить задачу</span>
            </Button>
        </div>
    </svelte:fragment>
</AbstractTaskCreator>

<style lang="scss">

  .create-subtask-button-holder {
    margin-left: 1.25rem;
  }

</style>