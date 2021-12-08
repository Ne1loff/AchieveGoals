<script lang="ts">
    import Icon from "@iconify/svelte";
    import {createEventDispatcher} from 'svelte';

    const dispatch = createEventDispatcher();
    const close = () => dispatch('close');
    const handle_keydown = e => {
        e.key === 'Escape' ? close() : _
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

    .main-window {
        padding: 20px 24px;
        box-sizing: border-box;

        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);

        max-width: 550px;
        width: 250px;
        max-height: 600px;

        display: flex;
        flex-direction: column;

        background: #fff;

        border-radius: 10px;
        box-shadow: 0 1px 8px 0 rgb(0 0 0 / 8%), 0 0 1px 0 rgb(0 0 0 / 30%);
        overflow: hidden;
        z-index: 102;
    }

    .header {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }

    .close_btn {
        padding: 0;
        border-radius: 3px;
        border: none;
        width: 24px;
        height: 24px;

        background: none;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .close_btn:hover {
        background: #f0f0f0;
    }

    :global(.close_btn_icon) {
        width: 24px;
        height: 24px;
    }

</style>

<svelte:window on:keydown={handle_keydown}/>
<div class="modal-background" on:click={close}></div>

<div class="main-window" role="dialog" aria-modal="true">
    <div class="header">
        <slot name="parent"></slot>
        <button class="close_btn" on:click={close}>
            <Icon class="close_btn_icon" icon="clarity:window-close-line"/>
        </button>
    </div>
    <slot name="about"></slot>
    {#if $$slots.children}
        <slot name="children"></slot>
    {/if}
    {#if $$slots.done}
        <slot name="done"></slot>
    {/if}
</div>
