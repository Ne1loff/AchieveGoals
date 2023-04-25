<script lang="ts">

    import SidePanelCell from "./SidePanelCell.svelte";
    import Task from "../../../../data/models/Task";
    import InlineCalendar from "../../../date-picker/InlineCalendar.svelte";
    import Icon from "@iconify/svelte";
    import {getColorFromDayDiff} from "../../utils";
    import dayjs from "dayjs";
    import ActionButton from "./ActionButton.svelte";
    import PortablePopover from "../../../popover/global/PortablePopover.svelte";
    import {PRIORITIES} from "../../../../resources/constants";
    import PriorityMenu from "../../PriorityMenu.svelte";
    import Tag from "../../../tag/Tag.svelte";
    import {hrefs} from "../../../../resources/config";
    import type TaskLabel from "../../../../data/models/TaskLabel.ts";
    import LabelSelector from "../../../label/LabelSelector.svelte";

    const priorities = PRIORITIES;

    export let task: Task;

    $: {
        if (task) labels = task.labels;
    }

    let labels: TaskLabel[] = [];
    const removeLabel = (id: number) => {
        labels = labels.filter(it => it.id !== id);
        task.labels = labels;
    };

</script>

<div class="task-additional-details">
    <div class="panel-cell-holder" style="--cell-title-padding: 0 .5rem 0 .5rem;">
        <SidePanelCell title="Проект">
            <svelte:fragment slot="content">
                <div class="cell-button">
                    {#if task}
                        <PortablePopover options={{
                                stopPropagation: true,
                                classStyle: 'elevation-6'
                            }}>
                            <ActionButton slot="target" let:toggle on:click={toggle} disabled>
                                <div class="cell-details"
                                     style="color: {getColorFromDayDiff(task.deadline)}">
                                    <div class="info-tags-icon">
                                        <Icon class="action-icons" icon="carbon:circle-solid"
                                              width="16" height="16"/>
                                    </div>
                                    <div class="info-tags-text">
                                        Goals
                                    </div>
                                    <div class="info-tags-icon">
                                        <Icon icon="carbon:chevron-down" width="24" height="24"
                                              color="var(--cds-icon-01)"/>
                                    </div>
                                </div>
                            </ActionButton>
                            <InlineCalendar slot="content" goalId={task.id} withTime/>
                        </PortablePopover>
                    {:else}
                        <ActionButton skeleton/>
                    {/if}
                </div>
            </svelte:fragment>
        </SidePanelCell>
        <SidePanelCell title="Срок выполнения">
            <svelte:fragment slot="content">
                <div class="cell-button">
                    {#if task}
                        <PortablePopover options={{
                                stopPropagation: true,
                                classStyle: 'elevation-6'
                            }}>
                            <ActionButton slot="target" let:toggle on:click={toggle}>
                                <div class="cell-details"
                                     style="color: {getColorFromDayDiff(task.deadline)}">
                                    <div class="info-tags-icon">
                                        <Icon class="action-icons" icon="carbon:calendar"
                                              width="20" height="20"/>
                                    </div>
                                    <div class="info-tags-text">
                                        {dayjs(task.deadline).format('DD MMM YY')}
                                    </div>
                                    <div class="info-tags-icon">
                                        <Icon icon="carbon:chevron-down" width="24" height="24"
                                              color="var(--cds-icon-01)"/>
                                    </div>
                                </div>
                            </ActionButton>
                            <InlineCalendar slot="content" goalId={task.id} withTime/>
                        </PortablePopover>
                    {:else}
                        <ActionButton skeleton/>
                    {/if}
                </div>
            </svelte:fragment>
        </SidePanelCell>
        <SidePanelCell title="Приоритет">
            <svelte:fragment slot="content">
                <div class="cell-button">
                    {#if task}
                        <PortablePopover options={{
                                stopPropagation: true,
                                classStyle: 'elevation-6'
                            }}>
                            <ActionButton slot="target" let:toggle on:click={toggle}>
                                <div class="cell-details"
                                     style="color: {getColorFromDayDiff(task.deadline)}">
                                    <div class="info-tags-icon">
                                        <Icon icon={priorities[task.priority - 1].icon}
                                              width="20px" color={priorities[task.priority - 1].color}/>
                                    </div>
                                    <div class="info-tags-text">
                                        P{task.priority}
                                    </div>
                                    <div class="info-tags-icon">
                                        <Icon icon="carbon:chevron-down" width="24" height="24"
                                              color="var(--cds-icon-01)"/>
                                    </div>
                                </div>
                            </ActionButton>
                            <PriorityMenu slot="content" bind:selected={task.priority}/>
                        </PortablePopover>
                    {:else}
                        <ActionButton skeleton/>
                    {/if}
                </div>
            </svelte:fragment>
        </SidePanelCell>
        <SidePanelCell clickableLabel --cell-title-padding="0">
            <svelte:fragment slot="title">
                {#if task}
                    <PortablePopover options={{
                                stopPropagation: true,
                                classStyle: 'elevation-6'
                            }}>
                        <ActionButton slot="target" let:toggle on:click={toggle}>
                            <div class="cell-details"
                                 style="color: {getColorFromDayDiff(task.deadline)}">
                                <div class="info-tags-text">
                                    Метки
                                </div>
                                <div class="info-tags-icon">
                                    <Icon icon="carbon:add" width="24" height="24"
                                          color="var(--cds-icon-01)"/>
                                </div>
                            </div>
                        </ActionButton>
                        <LabelSelector slot="content" bind:task/>
                    </PortablePopover>
                {:else}
                    <ActionButton skeleton/>
                {/if}
            </svelte:fragment>
            <svelte:fragment slot="content">
                <div class="labels-holder">
                    {#if task}
                        {#each labels as label}
                            <Tag interactive filter
                                 type="blue"
                                 title="Убрать метку"
                                 href={hrefs.task(label.id)}
                                 on:close={() => removeLabel(label.id)}>
                                {label.name}
                            </Tag>
                        {/each}
                    {:else}
                        {#each Array(3) as index}
                            <Tag skeleton/>
                        {/each}
                    {/if}
                </div>
            </svelte:fragment>
        </SidePanelCell>
        <SidePanelCell clickableLabel --cell-title-padding="0">
            <svelte:fragment slot="title">
                {#if task}
                    <PortablePopover options={{
                                stopPropagation: true,
                                classStyle: 'elevation-6'
                            }}>
                        <ActionButton slot="target" let:toggle on:click={toggle} disabled>
                            <div class="cell-details"
                                 style="color: {getColorFromDayDiff(task.deadline)}">
                                <div class="info-tags-text">
                                    Напоминания
                                </div>
                                <div class="info-tags-icon">
                                    <Icon icon="carbon:add" width="24" height="24"
                                          color="var(--cds-icon-01)"/>
                                </div>
                            </div>
                        </ActionButton>
                        <PriorityMenu slot="content" bind:selected={task.priority}/>
                    </PortablePopover>
                {:else}
                    <ActionButton skeleton/>
                {/if}
            </svelte:fragment>
            <svelte:fragment slot="content">

            </svelte:fragment>
        </SidePanelCell>
    </div>
</div>

<style lang="scss">

  .task-additional-details {
    padding: .75rem 1rem 1rem;
    height: 100%;

    background: var(--cds-background-hover);

    & .panel-cell-holder {

      display: flex;
      flex-direction: column;

      gap: .5rem;

      & .labels-holder {
        margin-top: .25rem;

        & :global(.bx--skeleton) {
          background-color: var(--cds-field-02) !important;
        }
      }
    }

    & .cell-details {
      width: 100%;
      flex-grow: 1;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;

      padding: .125rem .5rem;

      gap: .5rem;

      & .info-tags-text {
        flex-grow: 1;
        color: var(--cds-text-01);
      }

      & .info-tags-icon {
        height: 1.5rem;
        width: 1.5rem;

        display: flex;
        justify-content: center;
        align-items: center;
      }
    }
  }

</style>