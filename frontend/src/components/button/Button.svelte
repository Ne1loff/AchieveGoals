<script lang="ts">

    import {Button} from "carbon-components-svelte";
</script>

<Button {...$$props} as let:props>
    <p {...props}>
        <slot/>
    </p>
</Button>

<style>

    :root {
        --ag-bnt-border-radius: .25rem;
        --ag-bnt-padding: .125rem .75rem;
    }

    :global(.bx--btn) {
        border-radius: var(--ag-bnt-border-radius) !important;
        padding: var(--ag-bnt-padding) !important;
    }

</style>