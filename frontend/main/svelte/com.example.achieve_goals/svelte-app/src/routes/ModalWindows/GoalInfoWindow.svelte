<script lang="ts">
    import Icon from "@iconify/svelte";
    import {createEventDispatcher} from 'svelte';

    const dispatch = createEventDispatcher();
    const close = () => dispatch('close');
    const handle_keydown = e => {
        if (e.key === 'Escape') close()
    };

</script>

<style>

    #modal-root {
        position: absolute;
        top: 0;
        left: 0;

        background: none;
        width: 100%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .modal-background {
        position: absolute;

        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: #000;
        opacity: 0.5;
        z-index: 200;
    }

    .main-window {
        padding: 20px 24px;
        box-sizing: border-box;


        max-width: 650px;
        width: 100%;
        height: 100%;
        max-height: 900px;
        min-height: 400px;

        display: flex;
        flex-direction: column;

        background: #fff;

        border-radius: 10px;
        box-shadow: 0 1px 8px 10px rgb(0 0 0 / 8%), 0 0 1px 0 rgb(0 0 0 / 30%);
        z-index: 201;
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

<div id="modal-root">
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
</div>
