<script lang="ts">

    import Portal from "../../Portal.svelte";
    import PopoverItem from "./PopoverItem.svelte";
    import {onMount} from "svelte";
    import type {SveltePopoverOptions} from "./PopoverTypes";

    export let options: SveltePopoverOptions;

    let target: HTMLElement;

    let show: boolean = false;
    const toggle = () => show = !show;

    onMount(() => {
        options.fromComponent = true;
        options.target = target;
    });

</script>

<div class="portal" bind:this={target}>
    <slot name="target" {toggle}/>
</div>
{#if show}
    <Portal target="#popover-holder">
        <PopoverItem {options} on:close={toggle}>
            <svelte:fragment let:close>
                <slot name="content" {close}/>
            </svelte:fragment>
        </PopoverItem>
    </Portal>
{/if}

<style>

</style>