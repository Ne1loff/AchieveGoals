<script lang="ts">
    import Icon from "@iconify/svelte";
    import Overlay from "./overlay/OverlayComponent.svelte";
    import {createEventDispatcher} from "svelte";

    export let style: string | { [key: string]: any } = undefined;
    export let styleClasses: string | string[] = undefined;
    export let useOverlay: boolean = true;
    export let hideCloseBtn: boolean = false;
    export let useViaSidebar: boolean = false;
    export let showFooter: boolean = true;

    const dispatch = createEventDispatcher();

    const getClasses = (): string => {
        return (typeof styleClasses === 'string') ?
            styleClasses : (Array.isArray(styleClasses)) ?
                styleClasses.join(' ') : ''
    }

    const getStyle = (): string => {
        return (typeof style === 'string') ?
            style : (typeof style === 'object') ?
                style.toString() : '';
    }

    const close = () => {
        dispatch('close')
        if (typeof $$props.closePopover === 'function') {
            $$props.closePopover();
        }
    }
</script>

<div class="modal">
    {#if useOverlay}
        <Overlay on:click={close}/>
    {/if}
    <div class="modal-window {getClasses()}" style="{getStyle()}; flex-direction: {useViaSidebar ? 'row' : 'column'}">
        {#if useViaSidebar}
            <slot name="sidebar"/>
        {/if}
        <div class="modal-container" >
            <header class="modal-header">
                {#if $$slots.header}
                    <div class="modal-header-content">
                        <slot name="header"></slot>
                    </div>
                {/if}
                {#if !hideCloseBtn}
                    <button class="modal-close-btn" on:click={close}>
                        <Icon icon="carbon:close" width="28" color="var(--cds-icon-01)"/>
                    </button>
                {/if}
            </header>
            {#if $$slots.title}
                <hr/>
                <div class="modal-title">
                    <slot name="title"></slot>
                </div>
            {/if}
            {#if $$slots.content}
                <hr/>
                <div class="modal-content">
                    <slot name="content"></slot>
                </div>
            {/if}
            {#if $$slots.footer && showFooter}
                <hr/>
                <footer class="modal-footer">
                    <slot name="footer"></slot>
                </footer>
            {/if}
        </div>
    </div>
</div>

<style>

    :root {
        --own-modal-min-height: 0px;
        --own-modal-max-height: 0px;
        --own-modal-container-padding: 0;
        --own-modal-container-margin: 0;
        --own-modal-container-header-padding: 0;
    }

    .modal-header, .modal-title, .modal-content {
        margin-bottom: .5rem;
    }

    .modal-header {
        padding: var(--own-modal-container-header-padding);
    }

    .modal hr {
        width: calc(100% + var(--menu-container-hr-margin));
        margin: var(--menu-container-hr-margin);

        border: 0;
        border-bottom: 1px solid var(--cds-border-subtle);
    }

    .modal-window {
        display: flex;
        overflow: hidden;
    }

    .modal-container {
        flex-grow: 1;

        padding: var(--own-modal-container-padding);
        margin: var(--own-modal-container-margin);
    }

    .modal-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .modal-close-btn {
        width: 1.75rem;
        height: 1.75rem;

        padding: 0;

        display: flex;
        align-items: center;
        justify-content: center;

        background: inherit;
        border: none;
        border-radius: .5rem;
    }

    .modal-close-btn:hover {
        background-color: var(--cds-button-secondary-hover);
    }

    .modal {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;

        display: flex;
        align-items: center;
        justify-content: center;
    }

    .modal-window {

        max-height: var(--own-modal-max-height);
        min-height: var(--own-modal-min-height);

        z-index: 1000;
    }

</style>