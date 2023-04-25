<script lang="ts">
    import Icon from "@iconify/svelte";
    import Button from "../button/Button.svelte";
    import TaskLabel from "../../data/models/TaskLabel.ts";
    import ServiceFactory from "../../services/ServiceFactory";
    import {createEventDispatcher} from "svelte";

    const dispatch = createEventDispatcher();

    export let labelName: string = ''
    const createLabel = () => {
        const label: TaskLabel = new TaskLabel();
        label.name = labelName;

        ServiceFactory.INSTANCE.taskLabelService.createLabel(label)
            .then((label) => dispatch("created", label));
    }

</script>

<li class="task-label-creator">
    <Button kind="ghost" on:click={createLabel} size="field">
        <Icon color="var(--cds-icon-01)" height="24" icon="carbon:add"
              width="24"/>
        <span class="task-label-name-preview">Создать "{labelName}"</span>
    </Button>
</li>

<style lang="scss">
  .task-label-creator {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;

    & .task-label-name-preview {
      font-weight: var(--cds-display-02-font-weight);
    }
  }
</style>