<script lang="ts">
    import ModalWindow from "../modals/ModalWindow.svelte";
    import {ButtonSet} from "carbon-components-svelte";
    import Button from "../button/Button.svelte";
    import TaskLabel from "../../data/models/TaskLabel";
    import {createEventDispatcher, onMount} from "svelte";
    import SidePanelCell from "../goal-card/goal-info/side-panel/SidePanelCell.svelte";

    const dispatch = createEventDispatcher();

    export let label: TaskLabel;
    export let create: boolean = false;
    let labelCopy: TaskLabel;

    $:saveBtnDisable = label ? (labelCopy ? label.name === labelCopy.name : true) : true;

    const setupCopy = () => {
        labelCopy = new TaskLabel();

        if (create) return;

        labelCopy.id = label.id;
        labelCopy.name = label.name;
    }

    const close = () => dispatch('close');
    const cancel = () => {
        dispatch('cancel');
        close();
    }
    const save = () => {
        label = labelCopy;
        dispatch('save');
        close();
    }

    onMount(() => setupCopy())
</script>

<ModalWindow --menu-container-hr-margin="0 -.75rem" --own-modal-max-height="30rem" --own-modal-min-height="10rem"
             --own-modal-padding="2rem"
             --own-overlay-bg="#131313bf"
             hideCloseBtn
             on:close
             showFooter
             styleClasses={['label-edit-modal', 'elevation-8']}
             useOverlay
>
    <div class="title" slot="header">
        <h3>{create ? 'Создать' : 'Изменить'} метку</h3>
    </div>
    <div class="content" slot="content">
        <SidePanelCell title="Название метки" withoutHr>
            <svelte:fragment slot="content">
                {#if labelCopy}
                    <input class="label-name-input" bind:value={labelCopy.name}/>
                {/if}
            </svelte:fragment>
        </SidePanelCell>
    </div>
    <div class="footer" slot="footer">
        <div class="submit-buttons">
            <ButtonSet>
                <Button kind="secondary" on:click={cancel} size="small">Отмена</Button>
                <Button disabled={saveBtnDisable} on:click={save} size="small">Сохранить</Button>
            </ButtonSet>
        </div>
    </div>
</ModalWindow>

<style lang="scss">
  :global(.label-edit-modal) {
    background-color: var(--cds-field);
    border-radius: 16px;

    width: 30rem;
    height: auto !important;
  }

  .title {
    & h3 {
      padding: .25rem .75rem;
    }
  }

  .content {
    padding: .75rem;

    & .label-name-input {
      background-color: inherit;
      color: var(--cds-text-01);
      width: 100%;

      border-radius: .5rem;
    }
  }

  .footer {
    width: 100%;
    display: flex;
    justify-content: end;


    & .submit-buttons {
      width: 100%;

      display: flex;
      align-items: center;
      justify-content: right;
      padding: .5rem;
    }
  }
</style>