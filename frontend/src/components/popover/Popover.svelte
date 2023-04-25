<style>
    .target {
        display: inline-block;
        position: relative;
    }
    .own-popover {
        position: relative;
    }
</style>

<div class="own-popover">
    <div bind:this={targetRef} class="target" on:click={onClick} on:touchend={onTouchEnd} on:mouseover={onMouseOver} on:mouseout={onMouseOut}>
        <slot name="target" {open} />
    </div>
    {#if open}
        <Content on:open={onOpen} on:setOpen={setOpen} {placement} {targetRef} {zIndex} {arrow} {action} {overlayColor} {arrowColor} {preventDefault} {stopPropagation}>
            <slot name="content" {open} />
        </Content>
    {/if}
</div>

<script>
    const DEFAULT_ZINDEX = 1000;
    import Content from './Content.svelte';
    import {createEventDispatcher} from 'svelte';

    let targetRef;

    const dispatch = createEventDispatcher();

    const onOpen = () => {
        dispatch('open');
    };

    export let action = 'click';
    export let zIndex = DEFAULT_ZINDEX;
    export let arrow = false;
    export let placement = 'auto';
    export let arrowColor = 'var(--cds-field)';
    export let overlayColor = 'rgba(0, 0, 0, 0.02)';
    export let preventDefault = false;
    export let stopPropagation = false;

    export let open = false;

    const setOpen = () => {
        open = !open;
        if (!open) {
            dispatch('close');
        }
    };
    const eventClick = e => {
        if (preventDefault) e.preventDefault();
        if (stopPropagation) e.stopPropagation();
        setOpen();
    };
    const eventMouseOut = ({ relatedTarget }) => {
        if (relatedTarget.id === 'overlay' && !open) {
            setOpen();
        }
    };

    const onTouchEnd = action === 'click' ? eventClick : null;
    const onClick = action === 'click' ? eventClick : null;

    const setOpenTrue = () => (open = true);

    const onMouseOver = action === 'hover' ? setOpenTrue : null;
    const onMouseOut = action === 'hover' ? eventMouseOut : null;
</script>