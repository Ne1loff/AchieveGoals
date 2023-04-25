<script lang="ts">

    import {slide} from 'svelte/transition';
    import type {TransitionConfig} from "svelte/types/runtime/transition";

    export let animFunc: (node: Element, {}?:  any) => TransitionConfig = slide;

    export let animate: boolean = false;
    export let show: boolean = true;

    export let animationDurationIn: number = 200;
    export let animationDurationOut: number = 200;

</script>

{#if animate && show}
    <div in:animFunc|local={{ duration:  animationDurationIn }}
         out:animFunc|local={{ duration:  animationDurationOut }}>
        <slot/>
    </div>
{:else if !animate}
    <slot/>
{/if}

<style>

</style>