<script lang="ts">

    export let skeleton: boolean = false;
    export let disabled: boolean = false;

</script>

{#if (!skeleton)}
    <button class="action-button"
            {disabled}
            class:ag-btn={true}
            class:ag-btn-disabled={disabled}
            role="button" on:click>
        <slot/>
    </button>
{:else}
    <div class:action-button={true}
         class:bx--skeleton="{true}">
    </div>
{/if}

<style lang="scss">

  button {
    all: unset;
  }

  .action-button {
    width: 100%;
    height: 100%;

    min-height: 1.75rem;

    background: inherit;
    border-radius: var(--ag-bnt-border-radius);

    display: flex;
    align-items: center;

    &.bx--skeleton {
      position: relative;
      padding: 0;
      border: none;
      background: var(--cds-field-02);
      box-shadow: none;
      pointer-events: none;

      &:before {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        -webkit-animation: 3s ease-in-out skeleton infinite;
        animation: 3s ease-in-out skeleton infinite;
        background: var(--cds-skeleton-02, #c6c6c6);
        content: "";
        will-change: transform-origin, transform, opacity;
      }
    }

    &:hover {
      background: var(--cds-background-active);
    }

    :global([aria-expanded="true"]) & {
      background: var(--cds-background-active);
    }

    &.ag-btn-disabled, &:disabled {
      cursor: not-allowed;

      border-color: var(--cds-disabled-02, #c6c6c6);
      background: var(--cds-disabled-02, #c6c6c6);
      box-shadow: none;

      & :global(*) {
        color: var(--cds-disabled-03, #8d8d8d) !important;
      }
    }
  }

</style>