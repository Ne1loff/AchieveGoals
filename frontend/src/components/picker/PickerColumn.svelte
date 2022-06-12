<script lang="ts">

    import {onMount, tick} from "svelte";
    import type {PickerOptions} from "./picker";

    export let data: Array<any>;
    export let selected: number = 0;
    export let options: PickerOptions = {
        pickerScrollPadding: 122,
        pickerHeight: 32,
        pickerUnit: 'px'
    };

    const getStyle = (): string => {
        let height = '2rem';
        let width = '100%';
        let scrollPadding = '122px';

        if (options) {
            if (options.pickerHeight) {
                height = options.pickerHeight + options.pickerUnit;
            }
            if (options.pickerWidth) {
                width = options.pickerWidth + options.pickerUnit;
            }
            if (options.pickerScrollPadding) {
                scrollPadding = options.pickerScrollPadding + options.pickerUnit;
            }
        }

        return `
        --own-picker-item-height: ${height};
        --own-picker-item-width: ${width};
        --own-picker-scroll-padding: ${scrollPadding};`
    }

    const style: string = getStyle();

    let selectedIndex = selected;

    let itemsElem;
    let selectedItemOffset;

    const scrollToItem = async (index: number) => {
        if (itemsElem.scrollHeight < ((data.length - 1) * options.pickerHeight + options.pickerScrollPadding * 2 + 32)) {
            await tick();
        }
        itemsElem.scrollTo(0, (index * options.pickerHeight + 16));
    };

    const onScroll = () => {
        selectedItemOffset = (itemsElem.scrollTop - 16) / options.pickerHeight;
        selectedIndex = Math.round(selectedItemOffset);
        selected = selectedIndex > data.length - 1 ? data.length - 1 : selectedIndex;
    };

    const checkEquals = () => selected === selectedIndex;

    const checkReadyItemsElem = () => !!itemsElem;

    $: if (!checkEquals()) {
        selectedIndex = selected;
        if (checkReadyItemsElem()) scrollToItem(selected);
    }

    onMount(() => scrollToItem(selected));

</script>

<div class="picker-column">
    <div class="picker-items"
         bind:this={itemsElem}
         on:scroll={onScroll}
         style={style}>
        {#each data as item, i}
            <div class="picker-item"
                 class:selected={selectedItemOffset > (i - 0.5) && selectedItemOffset < (i + 0.5)}
                 on:click={() => scrollToItem(i)}>
                <span>{item}</span>
            </div>
        {/each}
    </div>
</div>

<style>

    :root {
        --own-picker-item-height: 2rem;
        --own-picker-item-width: 100%;
        --own-picker-scroll-padding: 122px;
    }

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
        width: var(--own-picker-item-width);
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