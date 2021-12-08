<script>
    import {createEventDispatcher} from 'svelte';

    export let bounding;
    export let fromGoalCard;
    export let anotherModal = false;
    export let width = 226;
    let scheduler;

    const dispatch = createEventDispatcher();

    const close = () => { if (!anotherModal) dispatch('close') };

    const handle_keydown = e => {
        if (e.key === 'Escape') {
            close();
        }
    };

    const updWindow = () => {
        if (scheduler && bounding) {
            let coords = bounding.getBoundingClientRect();
            let sch_coords = scheduler.getBoundingClientRect();

            let y = (coords.y + coords.height + 1);
            let x = coords.left;

            if (y + sch_coords.height >= window.innerHeight ) {
                fromGoalCard ? y -= sch_coords.height * .35: y -= sch_coords.height * .4;
                x -= (sch_coords.width);
            } else if (x + sch_coords.width >= window.innerWidth) x -= sch_coords.width * .9;
            if (fromGoalCard) y -= 70;


            if (x <= 0) x += sch_coords.width * .9;

            scheduler.style.left = x + "px";
            scheduler.style.top = y + "px";
        }
    }

    $: if (scheduler && bounding) updWindow();

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
        
        max-width: 550px;

        inset: 0 auto auto 0;

        max-height: 600px;
        display: flex;
        flex-direction: column;
        background: #fff;
        border-radius: 3px;
        box-shadow: 0 1px 8px 0 rgb(0 0 0 / 8%), 0 0 1px 0 rgb(0 0 0 / 30%);
        overflow: hidden;
        z-index: 1020;
    }

    .scheduler hr {
        width: 100%;
        border: 0;
        border-bottom: 1px solid #ddd;
        margin: 0;
    }
    
</style>

<svelte:window on:keydown={handle_keydown} on:resize={updWindow}/>
<div class="modal-background" on:click={close}></div>

<div class="scheduler" role="dialog" aria-modal="true" bind:this={scheduler} style="width: {width}px">
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
