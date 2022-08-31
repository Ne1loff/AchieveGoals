<script lang="ts">

    export let width: number = 226;
    export let classes: string | string[] = [];

    const getClasses = (): string => {
        if (!classes) return '';

        if (Array.isArray(classes)) {
            return classes.join(' ');
        } else {
            return classes;
        }
    }
</script>

<style>

    :root {
        --menu-container-padding: 0;
        --menu-container-border-radius: 3px;
        --menu-container-hr-margin: 0;
    }

    .container {
        padding: var(--menu-container-padding);

        max-width: 550px;
        max-height: 600px;
        display: flex;
        flex-direction: column;
        background: var(--cds-field);
        border: 1px solid var(--cds-border-subtle);
        border-radius: var(--menu-container-border-radius);
        z-index: 1020;
    }

    .container hr {
        width: calc(100% + var(--menu-container-hr-margin));
        border: 0;
        border-bottom: 1px solid var(--cds-border-subtle);
        margin: var(--menu-container-hr-margin);
    }

</style>

<div class="container {getClasses()}" style="width: {width}px">
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
