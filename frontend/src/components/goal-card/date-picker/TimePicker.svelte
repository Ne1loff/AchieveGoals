<script lang="ts">
    import {onDestroy, onMount} from "svelte";

    export let value: Date;

    const hours = Array(24).fill(0).map((_, i) => i).map(it => it < 10 ? `0${it}` : `${it}`);
    const minutes = Array(60).fill(0).map((_, i) => i).map(it => it < 10 ? `0${it}` : `${it}`);

    let hourElem;
    let minuteElem;

    let selectedHour: number = 0;
    let selectedMinute: number = 0;

    const scrollToNum = (hour: boolean, index: number) => {
        hour ? hourElem.scrollTo(0, index * 32 + 16) : minuteElem.scrollTo(0, index * 32 + 16);
    }

    const selected = (hour: boolean) => {
        hour ?
            selectedHour = (hourElem.scrollTop - 16) / 32 :
            selectedMinute = (minuteElem.scrollTop - 16) / 32;
        console.log(selectedHour, selectedMinute);
    }

    onMount(() => {
        if (value) {
            scrollToNum(true, value.getHours());
            scrollToNum(false, value.getMinutes());
        }
    });

    onDestroy(() => {
        value.setHours(Math.round(selectedHour), Math.round(selectedMinute))
        value = value;
    })

</script>

<div class="time_picker">
    <div class="picker-columns">
        <div class="picker-column">
            <div class="picker-items" bind:this={hourElem} on:scroll={() => selected(true)}>
                {#each hours as hour, i}
                    <div class="picker-item" class:selected={selectedHour > (i - 0.5) && selectedHour < (i + 0.5)}
                         on:click={() => scrollToNum(true, i)}>
                        <span>{hour}</span>
                    </div>
                {/each}
            </div>
        </div>
        <div class="picker-column column-picker-divider">:</div>
        <div class="picker-column">
            <div class="picker-items" bind:this={minuteElem} on:scroll={() => selected(false)}>
                {#each minutes as minute, i}
                    <div class="picker-item" class:selected={selectedMinute > (i - 0.5) && selectedMinute < (i + 0.5)}
                         on:click={() => scrollToNum(false, i)}>
                        <span>{minute}</span>
                    </div>
                {/each}
            </div>
        </div>
        <div class="picker-center-highlight"></div>
    </div>
</div>

<style>

    :root {
        --own-timepicker-height: 240px;
        --own-timepicker-width: 240px;
        --own-picker-item-size: 2rem;
        --own-picker-scroll-padding: 122px;
        --own-picker-item-height: 2rem;
    }

    .time_picker {
        height: var(--own-timepicker-height);
        width: var(--own-timepicker-width);

        position: relative;
        -webkit-user-select: none;
        user-select: none;
        background: var(--cds-field);
        border-radius: 10px;
    }

    .picker-columns {
        display: flex;
        overflow: hidden;
        justify-content: center;
        padding: 0;
        text-align: right;
        height: 100%;
        position: relative;
        font-size: var(--cds-quotation-01-font-size);

        -webkit-mask-box-image: linear-gradient(
                to top, transparent,
                transparent 5%,
                var(--cds-field) 20%,
                var(--cds-field) 80%,
                transparent 95%,
                transparent);
    }

    .picker-column {
        position: relative;
        max-height: 100%;
        z-index: 1;
    }

    .picker-column.column-picker-divider {
        display: flex;
        align-items: center;
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

    .picker-center-highlight {
        height: var(--own-picker-item-height);
        box-sizing: border-box;
        position: absolute;
        left: 16px;
        right: 16px;
        top: 50%;
        transform: translateY(-50%);
        pointer-events: none;
        background-color: var(--cds-selected-ui);
        border-radius: 8px;
    }

</style>