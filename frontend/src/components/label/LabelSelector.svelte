<script lang="ts">
    import Task from "../../data/models/Task";
    import LabelCreator from "./LabelCreator.svelte";
    import TaskLabel from "../../data/models/TaskLabel.ts";
    import {onMount} from "svelte";
    import {TASK_LABELS} from "../../data/storage/storage";
    import ServiceFactory from "../../services/ServiceFactory";
    import Icon from "@iconify/svelte";
    import Checkbox from "../checkbox/Checkbox.svelte";

    export let task: Task;
    let fullPredict: boolean = false;
    let canChange: boolean[];

    let labelName: string = '';
    let labels: TaskLabel[] = [];

    $: {
        if (labelName.length > 0) {
            labels = $TASK_LABELS.filter(it => {
                const value = it.name.toLowerCase();
                const predict = labelName.toLowerCase();
                fullPredict = value === predict;
                return value.indexOf(predict) !== -1;
            });
        } else {
            labels = $TASK_LABELS;
        }
        canChange = new Array(labels.length).fill(true);
    }

    const onInput = (event) => {
        const value: string = event.target.value;
        if (value.length <= 30) labelName = value;
    }

    const addOrRemoveLabel = (label: TaskLabel, remove: boolean) => {
        if (!canChange) return;

        let taskLabels = task.labels;

        if (remove)
            taskLabels = taskLabels.filter(it => it.id !== label.id)
        else
            taskLabels.push(label);

        task.labels = taskLabels;
    }

    onMount(() => {
        TASK_LABELS.subscribe((values) => labels = values);

        if ($TASK_LABELS.length == 0) {
            ServiceFactory.INSTANCE.taskLabelService.getLabels();
        }
    })

</script>

{#if task}
    <div class="task-label">
        <div class="task-label-input">
            <input class="task-label-search" placeholder="Введите название метки"
                   value={labelName}
                   on:input={onInput}
            />
        </div>
        <ul class="task-label-selector">
            {#if labels.length > 0}
                {#each labels as label, index (label.id)}
                    {@const checked = task.labels.some(it => it.id === label.id)}
                    <li class="task-label-selector-option"
                        on:click={() => {if (canChange[index]) addOrRemoveLabel(label, checked)}}
                    >
                        <Icon icon="carbon:tag" width="24" height="24" rotate={1}/>
                        <span>{label.name}</span>
                        <Checkbox {checked} bind:canChange={canChange[index]} size="1.5rem" {...{
                            size: "1.5rem", secondaryColor: "var(--cds-icon-01)",
                            primaryColor: "var(--cds-button-primary)", duration: 200
                        }}/>
                    </li>
                {/each}
            {/if}
            {#if labelName.length > 0 && !fullPredict}
                <LabelCreator {labelName} on:created={({detail}) => addOrRemoveLabel(detail, false)}/>
            {/if}
        </ul>
    </div>
{/if}

<style lang="scss">

  .task-label {

    background: var(--cds-field);
    border-radius: .5rem;
    overflow: hidden;

    & .task-label-input {
      width: 100%;
      padding: .5rem;

      & .task-label-search {
        width: 100%;
        background: inherit;
        color: var(--cds-text-01);
        border: none;
        margin: 0;

        &::placeholder {
          color: var(--cds-text-placeholder);
        }
      }
    }

    & .task-label-selector {
      display: flex;
      flex-direction: column;
      max-height: 10rem;
      overflow-y: auto;
      overflow-x: auto;

      & :global(button) {
        width: 100% !important;
        gap: 1rem;
        justify-content: start;
      }

      & .task-label-selector-option {
        padding: .25rem .5rem;

        display: flex;
        align-items: center;

        cursor: pointer;

        &:hover {
          background-color: var(--cds-field-hover);
        }

        & span {
          margin-left: .75rem;
          flex-grow: 1;

          font-size: var(--cds-body-01-font-size);
        }
      }
    }
  }

</style>