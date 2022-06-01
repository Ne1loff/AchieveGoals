<script lang="ts">

    import PopoverService from "../../services/PopoverService";
    import {onMount, SvelteComponent} from "svelte";
    import ServiceFactory from "../../services/ServiceFactory";

    export let component: SvelteComponent;
    export let componentProps: { [k: string]: any };

    let targetRef;

    let popoverService: PopoverService;

    const createPopover = () => {
        popoverService.create(targetRef, component, componentProps);
    }

    onMount(() => popoverService = ServiceFactory.INSTANCE.popoverService);

</script>

<div class="target" bind:this={targetRef} on:click={createPopover}>
    <slot></slot>
</div>

<style>

    .target {
        display: inline-block;
    }

</style>