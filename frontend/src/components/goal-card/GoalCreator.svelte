<script lang="ts">
    import AbstractTaskCreator from "./AbstractTaskCreator.svelte";
    import Icon from "@iconify/svelte";
    import Goal from "../../data/models/Goal";

    export let taskStorage: Goal[];

    const title: string = "Добавить цель";
</script>


<AbstractTaskCreator {taskStorage}>
    <svelte:fragment slot="create-button" let:create>
        <button class:creator-button={true} tabindex="1"
                on:click={create}>
            <span class="button-icon">
                <Icon class="add-icon" icon="carbon:add" width="24"/>
            </span>
            <span class="button-title">{title}</span>
        </button>
    </svelte:fragment>
</AbstractTaskCreator>

<style lang="scss">

  .creator-button {
    margin-left: 1.5rem;
    padding-left: .5rem;

    width: calc(100% - 1.5rem);
    height: 2rem;

    display: flex;
    align-items: center;
    justify-content: flex-start;

    background: inherit;
    color: var(--cds-text-placeholder);
    border: none;
    border-radius: 5px;
  }

  .creator-button:focus-visible {
    outline: var(--cds-focus) solid 1px;
    background-color: var(--cds-hover-ui);
  }

  .creator-button:hover .button-icon {
    background-color: var(--cds-danger);
    color: white;
  }

  .creator-button:hover .button-title {
    color: var(--cds-text-error);
  }

  .button-icon {
    margin-right: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50%;
    color: var(--cds-danger);
  }

</style>