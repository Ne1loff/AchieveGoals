<script lang="ts">
    import Icon from "@iconify/svelte";
    import Overlay from "./overlay/OverlayComponent.svelte";
    import {createEventDispatcher} from "svelte";
    import Button from "../button/Button.svelte";
    import {getClasses} from "../utils";

    export let style: string | { [key: string]: any } = undefined;
    export let styleClasses: string | string[] = undefined;
    export let useOverlay: boolean = true;
    export let hideCloseBtn: boolean = false;
    export let useViaSidebar: boolean = false;
    export let showFooter: boolean = true;

    const dispatch = createEventDispatcher();

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
    <div class="modal-window {getClasses(styleClasses)}"
         style="{getStyle()} flex-direction: {useViaSidebar ? 'row' : 'column'}">
        {#if useViaSidebar}
            <slot name="sidebar"/>
        {/if}
        <div class="modal-container">
            <header class="modal-header">
                {#if $$slots.header}
                    <div class="modal-header-content">
                        <slot name="header"></slot>
                    </div>
                {/if}
                {#if !hideCloseBtn}
                    <Button kind="ghost"
                            size="small"
                            --ag-bnt-border-radius=".5rem"
                            --ag-bnt-padding="0"
                            on:click={close}>
                        <Icon icon="carbon:close" width="28"/>
                    </Button>
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
                <div class="modal-content" style:height={showFooter ? 'calc(100% - (56px + 8px + 1px) * 2)' : ''}>
                    <slot name="content"></slot>
                </div>
            {/if}
            {#if $$slots.footer && showFooter}
                <hr/>
                <slot name="footer"></slot>
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

    .modal-header:not(:last-child), .modal-title:not(:last-child), .modal-content:not(:last-child) {
        margin-bottom: .5rem;
    }

    .modal-content {
        height: calc(100% - 56px - 8px - 1px);
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


        height: 100%;
    }

    .modal-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
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

        height: var(--own-modal-max-height);

        z-index: 1000;
    }

</style>