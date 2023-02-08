<script lang="ts">

    import Portal from "../../Portal.svelte";
    import PopoverItem from "./PopoverItem.svelte";
    import {onMount} from "svelte";
    import type {SveltePopoverOptions} from "./PopoverTypes";

    export let options: SveltePopoverOptions;

    let target: HTMLElement;

    let show: boolean = false;
    const toggle = () => show = !show;

    const getChildrenWithDisplayNonContents = (target: Element): { result: boolean, element: Element } => {
        const displayStyle = window.getComputedStyle(target).display;
        if (displayStyle !== "contents") return {result: true, element: target};

        for (let child of target.children) {
            const result = getChildrenWithDisplayNonContents(child);
            if (result.result) return result;
        }

        return {result: false, element: target};
    }

    onMount(() => {
        options.fromComponent = true;
        options.target = <HTMLElement>getChildrenWithDisplayNonContents(target).element;
    });

</script>

<div class="portal" bind:this={target} aria-expanded={show}>
    <slot name="target" {toggle}/>
</div>
{#if show}
    <Portal target="#popover-holder">
        <PopoverItem {options} on:close={toggle} --own-popover-border-radius="1rem">
            <svelte:fragment let:close>
                <slot name="content" {close}/>
            </svelte:fragment>
        </PopoverItem>
    </Portal>
{/if}

<style lang="scss">

  .portal {
    display: contents;
  }

</style>