<script lang="ts">
    import Icon from "@iconify/svelte";
    import TaskLabel from "../../data/models/TaskLabel.ts";
    import {TASKS} from "../../data/storage/storage";
    import {hrefs} from "../../resources/config";
    import {onMount, tick} from "svelte";
    import Button from "../button/Button.svelte";
    import EditLabelModal from "./EditLabelModal.svelte";
    import PortableModal from "../modals/PortableModal.svelte";
    import PortablePopover from "../popover/global/PortablePopover.svelte";
    import LabelMenu from "./LabelMenu.svelte";
    import ServiceFactory from "../../services/ServiceFactory";

    export let label: TaskLabel;

    $:taskCount = $TASKS.filter(task => task.labels.some((it) => it.id === label.id)).length;
    let href: string = '#';
    let editToggle: { func: () => void } = {
        func: () => {
        }
    };

    const setToggleFunc = (func: () => void): string => {
        editToggle.func = func;
        return ""
    }

    onMount(async () => {
        let timeout: boolean = false;
        setTimeout(() => timeout = true, 3000);
        while (!label && !timeout) await tick();
        href = timeout ? "#" : hrefs.label(label.id);
    });

    // CRUD
    const labelService = ServiceFactory.INSTANCE.taskLabelService;
    const updateLabel = () => {
        if (!label) return;
        labelService.editLabel(label);
    };
    const deleteLabel = () => {
        if (!label) return;
        labelService.deleteLabel(label.id)
    };

</script>

{#if label}
    <a class="label-container" href={href}>
        <div class="label-icon">
            <Icon icon="carbon:tag" width="20" height="20" rotate={1}/>
        </div>
        <div class="label-content">
            <span class="label-title">
                {label.name}
            </span>
            <div class="label-actions" style="--ag-bnt-border-radius:.5rem;" on:click|preventDefault>
                <PortableModal>
                    <Button slot="target" let:toggle kind="ghost" size="small" on:click={toggle}>
                        {setToggleFunc(toggle)}
                        <Icon icon="carbon:edit" width="20" height="20"/>
                    </Button>
                    <EditLabelModal slot="content" let:close bind:label
                                    on:close={close}
                                    on:save={updateLabel}
                    />
                </PortableModal>
                <PortablePopover options={{
                        stopPropagation: true
                    }}>
                    <Button slot="target" let:toggle kind="ghost" size="small" on:click={toggle}>
                        <Icon icon="carbon:overflow-menu-horizontal" width="20" height="20"/>
                    </Button>
                    <LabelMenu slot="content" let:close
                               on:edit={editToggle.func}
                               on:delete={deleteLabel}
                               on:close={close}
                    />
                </PortablePopover>
            </div>
            <div class="label-task-count">
                {taskCount === 0 ? '' : taskCount}
            </div>
        </div>
    </a>
{/if}

<style lang="scss">

  a {
    all: unset;
    cursor: pointer;
    border-bottom: 1px solid var(--cds-border-subtle);
  }

  .label-container {
    margin-left: 1.75rem;
    min-height: 2rem;
    height: 2rem;

    padding: .25rem;

    display: flex;
    align-items: center;

    & .label-content {
      padding: 0 .5rem;
      flex-grow: 1;
      display: flex;
      justify-content: space-between;
      align-items: center;

      & .label-task-count {
        margin-right: .25rem;
      }
    }

    &:hover {
      & .label-task-count {
        opacity: 0;
      }

      & .label-actions {
        & :global(.ag-btn) {
          opacity: 1;
        }
      }
    }

    & .label-actions {
      z-index: 1;

      position: absolute;
      right: 0;

      & :global(.ag-btn) {
        padding: .25rem !important;
        min-width: 2rem;
        justify-content: center;

        opacity: 0;
      }
    }
  }

  :global([aria-expanded="true"]) {
    & :global(.ag-btn) {
      opacity: 1 !important;
    }
  }

</style>