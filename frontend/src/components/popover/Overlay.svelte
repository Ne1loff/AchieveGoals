<style>
    .overlay {
        position: fixed;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
    }
</style>

<div id="overlay" class="overlay" on:mouseenter={onMouseEnter} on:click={onClick} on:touchend={onTouchEnd} style="z-index: {zIndex}; background-color: {overlayColor}"></div>

<script>
    export let zIndex;
    export let action;
    export let overlayColor = 'rgba(0, 0, 0, 0.02)';
    export let preventDefault;
    export let stopPropagation;

    import { createEventDispatcher } from 'svelte';

    const dispatch = createEventDispatcher();

    const eventClick = e => {
        if (preventDefault) e.preventDefault();
        if (stopPropagation) e.stopPropagation();

        dispatch('setOpen', {});
    };

    const onClick = action === 'click' ? eventClick : null;
    const onTouchEnd = action === 'click' ? eventClick : null;

    const onMouseEnter = action === 'hover' ? eventClick : null;
</script>