<script lang="ts">

    import {Button} from "carbon-components-svelte";
</script>

<div class="ag">
    {#if $$slots.default}
        <Button {...$$props}
                on:click
                on:mouseover
                on:mouseenter
                on:mouseleave
        >
            <slot/>
        </Button>
    {:else}
        <Button {...$$props}
                on:click
                on:mouseover
                on:mouseenter
                on:mouseleave
        />
    {/if}
</div>


<style>

    :root {
        --ag-bnt-border-radius: .25rem;
        --ag-bnt-padding: .125rem .75rem;
        --ag-bnt-margin: 0;
        --ag-btn-text-color: inerhit;
    }

    :global(.ag a.bx--btn) {
        color: var(--cds-text-04, #ffffff) !important;
        text-decoration: none;
    }

    :global(.bx--btn) {
        border-radius: var(--ag-bnt-border-radius) !important;
        padding: var(--ag-bnt-padding) !important;
        margin: var(--ag-bnt-margin) !important;
    }

    :global(.bx--btn.bx--btn--ghost) {
        color: var(--cds-interactive-03, #0f62fe) !important;
    }

    :global(.bx--btn.bx--btn--ghost:active) {
        color: var(--cds-inverse-01, #ffffff) !important;
    }

    .ag {
        display: contents;
    }

</style>