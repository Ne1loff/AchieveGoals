<script>
    import {createEventDispatcher} from 'svelte';

    const dispatch = createEventDispatcher();
    const close = () => dispatch('close');

    const handle_keydown = e => {
        if (e.key === 'Escape') {
            close();
        }
    };

</script>

<style>

    .modal-background {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: none;
        z-index: 102;
    }

    .scheduler {
        position: absolute;

        transform: translate(685px, 515px);
        max-width: 550px;

        inset: 0 auto auto 0;

        width: 250px;
        max-height: 600px;
        display: flex;
        flex-direction: column;
        background: #fff;
        border-radius: 3px;
        box-shadow: 0 1px 8px 0 rgb(0 0 0 / 8%), 0 0 1px 0 rgb(0 0 0 / 30%);
        overflow: hidden;
        z-index: 102;
    }

    .scheduler hr {
        width: 100%;
        border: 0;
        border-bottom: 1px solid #ddd;
        margin: 0;
    }

</style>

<svelte:window on:keydown={handle_keydown}/>
<div class="modal-background" on:click={close}></div>

<div class="scheduler" role="dialog" aria-modal="true">
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
