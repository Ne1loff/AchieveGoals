<script lang="ts">
    import MenuContainer from "../modals/MenuContainer.svelte";
    import Icon from "@iconify/svelte";
    import {createEventDispatcher} from "svelte";

    const dispatch = createEventDispatcher();

    const close = () => dispatch('close');
    const edit = () => {
        dispatch('edit');
        close();
    };
    const deleteLabel = () => {
        dispatch('delete');
        close();
    };

</script>

<MenuContainer
        --menu-container-border-radius=".5rem"
        --menu-container-hr-margin=".25rem -.25rem"
        --menu-container-padding=".25rem"
>
    <svelte:fragment slot="content">
        <div class="menu-actions" slot="header">
            <button class="actions-btn" on:click={edit}>
                <Icon height="20" icon="carbon:edit" width="20"/>
                <span>Редактировать метку</span>
            </button>
            <button class="actions-btn" on:click={deleteLabel}>
                <Icon height="20" icon="carbon:trash-can" width="20"/>
                <span>Удалить метку</span>
            </button>
        </div>
    </svelte:fragment>
</MenuContainer>

<style lang="scss">
  .menu-actions {
    margin: .125rem 0;
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;

    & .actions-btn {
      width: 100%;
      margin: 0;
      padding: .5rem;
      box-sizing: border-box;
      border-radius: .25rem;

      background: var(--cds-field);

      display: flex;
      align-items: center;
      border: none;

      color: var(--cds-text-01);
      cursor: pointer;

      &:hover {
        background-color: var(--cds-field-hover);
      }

      & span {
        margin-left: .5rem;
      }
    }
  }
</style>