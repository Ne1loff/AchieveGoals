<script lang="ts">
    import FilterLabelsComponentsHolder from "./FilterLabelsComponentsHolder.svelte";
    import LabelComponent from "../../label/LabelComponent.svelte";
    import Button from "../../button/Button.svelte";
    import Icon from "@iconify/svelte";
    import {TASK_LABELS} from "../../../data/storage/storage";
    import {onMount} from "svelte";
    import ServiceFactory from "../../../services/ServiceFactory";
    import PortableModal from "../../modals/PortableModal.svelte";
    import EditLabelModal from "../../label/EditLabelModal.svelte";
    import TaskLabel from "../../../data/models/TaskLabel";

    onMount(() => {
        if ($TASK_LABELS.length === 0) {
            ServiceFactory.INSTANCE.taskLabelService.getLabels();
        }
    })


    let newLabel: TaskLabel = new TaskLabel();
    const createLabel = () => ServiceFactory.INSTANCE.taskLabelService.createLabel(newLabel);

</script>

<FilterLabelsComponentsHolder title="Метки">
    <svelte:fragment slot="action">
        <div class="btn-holder">
            <PortableModal>
                <Button --ag-bnt-border-radius=".5rem" kind="ghost"
                        let:toggle
                        on:click={toggle} size="small"
                        slot="target"
                >
                    <Icon height="24" icon="carbon:add" width="24"/>
                </Button>
                <EditLabelModal bind:label={newLabel} let:close on:close={close}
                                on:save={createLabel}
                                slot="content"
                />
            </PortableModal>
        </div>
    </svelte:fragment>
    <svelte:fragment>

        {#each $TASK_LABELS as label}
            <LabelComponent bind:label/>
        {/each}

    </svelte:fragment>
</FilterLabelsComponentsHolder>

<style lang="scss">
  .btn-holder {
    & :global(.ag-btn) {
      padding: .25rem !important;
    }
  }
</style>