<script>
    import {createEventDispatcher, onDestroy} from 'svelte';

    export let anotherModal = false;

    const dispatch = createEventDispatcher();
    const close = () => dispatch('close');

    let modal;

    const handle_keydown = e => {
        if (e.key === 'Escape' && !anotherModal) {
            close();
            return;
        }

        if (e.key === 'Tab') {
            // trap focus
            const nodes = modal.querySelectorAll('*');
            const tabbable = Array.from(nodes).filter(n => n.tabIndex >= 0);

            let index = tabbable.indexOf(document.activeElement);
            if (index === -1 && e.shiftKey) index = 0;

            index += tabbable.length + (e.shiftKey ? -1 : 1);
            index %= tabbable.length;

            tabbable[index].focus();
            e.preventDefault();
        }
    };

    const previously_focused = typeof document !== 'undefined' && document.activeElement;

    if (previously_focused) {
        onDestroy(() => {
            previously_focused.focus();
        });
    }
</script>

<svelte:window on:keydown={handle_keydown}/>

<div class="modal-background" on:click={close}></div>

<div class="modal" role="dialog" aria-modal="true" bind:this={modal}>
    <div class="modal-container">
        <slot name="header"></slot>
        <slot></slot>
        <div class="buttons">
            <div class="buttons-inner">
            <slot name="button"></slot>
            <button class="button_close" autofocus on:click={close}>Отмена</button>
            </div>
        </div>
    </div>
</div>

<style>
    .modal-background {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: none;
        z-index: 100;
    }

    .modal {
        position: absolute;
        left: 50%;
        top: 50%;
        width: calc(100vw - 4em);
        max-width: 550px;
        max-height: 335px;
        transform: translate(-50%, -60%);
        padding: 1em;
        background: none;

        background: white;
        border-radius: 10px;

        box-shadow: 0 15px 50px 0 rgb(0 0 0 / 35%);
        flex-grow: 0;

        overflow-y: hidden;

        z-index: 102;
    }

    .buttons {
        margin: 6px -16px -16px;
        padding: 16px;
        border-top: 1px solid #ddd;
    }

    .buttons-inner {
        display: flex;
        margin: -12px 0 0 -12px;
    }

    .button_close {
        margin: 12px 0 0 12px;
        background: transparent;
        border: 1px solid rgba(0,0,0,.1);

        line-height: 18px;
        padding: 6px 10px;
        height: 32px;

        border-radius: 5px;
        box-sizing: border-box;

        color: rgba(0, 0, 0, 0.88);
        cursor: pointer;
    }

</style>