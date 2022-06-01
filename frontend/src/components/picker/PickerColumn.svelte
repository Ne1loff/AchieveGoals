<script lang="ts">

    import {onMount} from "svelte";

    export let data: Array<any>;
    export let selected: number = 0;

    let itemsElem;
    let selectedItemOffset;

    const scrollToItem = (index: number) => itemsElem.scrollTo(0, index * 32 + 16);
    const onScroll = () => {
        selectedItemOffset = (itemsElem.scrollTop - 16) / 32;
        selected = Math.round(selectedItemOffset);
    };

    onMount(() => scrollToItem(selected));

</script>

<div class="picker-column">
    <div class="picker-items" bind:this={itemsElem} on:scroll={onScroll}>
        {#each data as item, i}
            <div class="picker-item" class:selected={selectedItemOffset > (i - 0.5) && selectedItemOffset < (i + 0.5)}
                 on:click={() => scrollToItem(i)}>
                <span>{item}</span>
            </div>
        {/each}
    </div>
</div>

<style>

    .picker-column {
        position: relative;
        max-height: 100%;
        z-index: 1;
    }

    .picker-items {
        overflow: auto;
        -ms-overflow-style: none;
        scrollbar-width: none;
        scroll-snap-type: y mandatory;
        height: 100%;
        box-sizing: border-box;
        padding: var(--own-picker-scroll-padding) 0;
    }

    .picker-items::-webkit-scrollbar {
        display: none;
    }

    .picker-item {
        height: var(--own-picker-item-size);
        line-height: var(--own-picker-item-size);
        white-space: nowrap;
        position: relative;
        overflow: hidden;
        text-overflow: ellipsis;
        left: 0;
        top: 0;
        width: 100%;
        box-sizing: border-box;
        color: var(--cds-text-disabled);
        cursor: pointer;
        scroll-snap-align: center;
    }

    .picker-item.selected {
        color: var(--cds-text-01);
    }

    .picker-item span {
        padding: 0 10px;
    }

</style>