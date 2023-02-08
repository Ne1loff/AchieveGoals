<script lang="ts">
    export let title: string;
    export let clickableLabel: boolean = false;
    export let withoutHr: boolean = false;
</script>

<div class="cell-container">
    <div class="cell-title">
        {#if (clickableLabel)}
            <slot name="title"/>
        {:else }
            {title}
        {/if}
    </div>
    {#if $$slots.content}
        <div class="cell-content">
            <slot name="content"/>
        </div>
    {/if}
</div>
{#if !withoutHr}
    <hr/>
{/if}


<style lang="scss">

  :root {
    --cell-title-padding: 0;
  }

  .cell-container {

    & .cell-content {
      max-height: 20rem;
    }

    & .cell-title {
      height: 1.75rem;
      @extend .cell-content;
      display: flex;
      align-items: center;

      font-weight: var(--cds-heading-01-font-weight);
      font-size: var(--cds-label-01-font-size);
      line-height: var(--cds-heading-02-line-height);

      padding: var(--cell-title-padding);
    }
  }

  hr {
    width: calc(100% - 1rem);
    margin: 0 .5rem;

    border: none;
    border-top: 2px solid var(--cds-border-subtle);
  }

</style>