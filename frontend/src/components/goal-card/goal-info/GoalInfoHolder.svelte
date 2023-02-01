<script lang="ts">

    import GoalCheckbox from "../GoalCheckbox.svelte";
    import Goal from "../../../data/models/Goal";
    import {PRIORITY_COLORS} from "../../../resources/constants";
    import GoalEditorComponent from "../GoalEditorComponent.svelte";
    import Icon from "@iconify/svelte";

    const priorityColors = PRIORITY_COLORS;
    export let goal: Goal;

    let editing: boolean = false;
    const edit = () => {
        editing = true
    };
    const save = () => {
        editing = false;
    }

    const cancel = () => {
        editing = false;
    }


</script>
{#if (goal && !editing)}
    <div class="goal-detail">
        <div class="done-button-holder">
            <GoalCheckbox
                    bind:done={goal.isDone}
                    round
                    --own-checkbox-size="1.5rem"
                    --own-checkbox-border-color={priorityColors[goal.priority].icon}
                    --own-checkbox-hover-border-color={priorityColors[goal.priority].icon}
                    --own-checkbox-done-border-color={priorityColors[goal.priority].icon}

                    --own-checkbox-bg-color={priorityColors[goal.priority].background}
                    --own-checkbox-hover-bg-color={priorityColors[goal.priority].background}
                    --own-checkbox-done-bg-color={priorityColors[goal.priority].icon}

                    --own-check-mark-bg-color={priorityColors[goal.priority].icon}
                    --own-check-mark-hover-bg-color={priorityColors[goal.priority].icon}
            />
        </div>
        <div class="detail-holder">
            <div class="input-holder detail-title" on:click|preventDefault={edit}>
                {goal.title}
            </div>
            <div class="input-holder" on:click|preventDefault={edit}>
                {#if !goal.description || goal.description.length === 0}
                    <div class="input-placeholder">
                        <Icon icon="fluent:text-description-24-filled" width="24" height="24"/>
                        <!--TODO: l10n-->
                        <span>Описание</span>
                    </div>
                {:else}
                    {goal.description}
                {/if}
            </div>
        </div>
    </div>
{:else if (editing)}
    <GoalEditorComponent bind:goal={goal} on:save={save} on:cancel={cancel}/>
{/if}

<style lang="scss">
  .goal-detail {
    width: 100%;
    height: 4.5rem;

    display: flex;
    flex-direction: row;

    & .done-button-holder {
      height: 100%;
      padding-top: calc(.5rem + 2px);

      display: flex;
      flex-direction: column;
      justify-content: start;
    }

    & .detail-holder {
      flex-grow: 1;
      height: 100%;

      padding-top: .5rem;
      padding-left: .75rem;

      display: flex;
      flex-direction: column;
      justify-content: space-between;

      & .input-holder {
        width: 100%;

        &.detail-title {
          font-size: var(--cds-heading-03-font-size);
          font-weight: var(--cds-heading-01-font-weight);
          line-height: var(--cds-heading-03-line-height);
        }

        & .input-placeholder {
          display: flex;
          flex-direction: row;
          gap: .5rem;
          color: var(--cds-text-placeholder);
          align-items: center;
        }
      }
    }
  }
</style>