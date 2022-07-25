<script lang="ts">
    import Icon from "@iconify/svelte";
    import Overlay from "./overlay/OverlayComponent.svelte";
    import {createEventDispatcher} from "svelte";

    const dispatch = createEventDispatcher();

    export let hideCloseBtn: boolean = false;

    const close = () => {
        dispatch('close')
    }
</script>

<div class="modal-container">
    <Overlay on:click={close}/>
    <div class="modal-window">
        <div class="modal-header">
            {#if $$slots.header}
                <div class="modal-header-content">
                    <slot name="header"></slot>
                </div>
            {/if}
            {#if !hideCloseBtn}
                <div class="modal-close-btn">
                    <Icon icon="carbon:close"/>
                </div>
            {/if}
        </div>
        <div class="modal-title">
            <slot name="title"></slot>
        </div>
        <div class="modal-content">
            <slot name="content"></slot>
        </div>
        <div class="modal-footer">
            <slot name="footer"></slot>
        </div>
    </div>
</div>

<style>

    :root {
        --own-modal-height: 0px;
    }

    .modal-window {

    }

</style>