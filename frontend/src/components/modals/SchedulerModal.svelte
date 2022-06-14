<script lang="ts">
    export let width: number = 226;
</script>

<style>

    .container {
        max-width: 550px;
        max-height: 600px;
        display: flex;
        flex-direction: column;
        background: var(--cds-field);
        border-radius: 3px;
        z-index: 1020;
    }

    .container hr {
        width: 100%;
        border: 0;
        border-bottom: 1px solid var(--cds-border-subtle);
        margin: 0;
    }

</style>

<div class="container" style="width: {width}px">
    {#if $$slots.header}
        <slot name="header"></slot>
        {#if $$slots.content}
            <hr>
        {/if}
    {/if}
    {#if $$slots.content}
        <slot name="content"></slot>
        {#if $$slots.footer}
            <hr>
        {/if}
    {/if}
    {#if $$slots.footer}
        <slot name="footer"></slot>
    {/if}
</div>
