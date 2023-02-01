<script lang="ts">

    import Goal from "../../data/models/Goal";
    import GoalEditorComponent from "./GoalEditorComponent.svelte";
    import {onMount} from "svelte";
    import GoalService from "../../services/GoalService";
    import ServiceFactory from "../../services/ServiceFactory";

    export let taskStorage: Goal[];

    let goalService: GoalService;
    let newTask: Goal | undefined;

    const create = () => newTask = new Goal();
    const cancel = () => newTask = undefined;

    const save = () => {
        if (!newTask) return;

        const uniqIds = new Set(taskStorage.map(it => it.gid));
        if (uniqIds.size === 1) newTask.gid = [...uniqIds][0];

        goalService.createGoal(newTask)
            .then((_) => {
                create();
            });
    }

    onMount(() => {
        goalService = ServiceFactory.INSTANCE.goalService;
    })

</script>

<div class:task-creator={true}>
    {#if newTask}
        <GoalEditorComponent indent={1} bind:goal={newTask} on:save={save} on:cancel={cancel}/>
    {:else}
        <slot name="create-button" {create}/>
    {/if}
</div>

<style lang="scss">

  .task-creator {
    margin-top: 2px;
    z-index: 1;
  }

</style>