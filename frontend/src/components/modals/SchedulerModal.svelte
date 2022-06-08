<script>

    export let width = 226;

</script>

<style>

    .scheduler {
        max-width: 550px;
        max-height: 600px;
        display: flex;
        flex-direction: column;
        background: var(--cds-field);
        border-radius: 3px;
        z-index: 1020;
    }

    .scheduler hr {
        width: 100%;
        border: 0;
        border-bottom: 1px solid #ddd;
        margin: 0;
    }

</style>

<div class="scheduler" style="width: {width}px">
    {#if $$slots.header}
        <slot name="header"></slot>
        <hr>
    {/if}
    {#if $$slots.suggestion}
        <slot name="suggestion"></slot>
        <hr>
    {/if}
    {#if $$slots.date}
        <slot name="date"></slot>
        <hr>
    {/if}
    {#if $$slots.time}
        <slot name="time"></slot>
    {/if}
</div>
