<script lang="ts">

    import {getClasses, getStyle} from "../utils";
    import {SkeletonText} from "carbon-components-svelte";

    export let skeleton: boolean = false;

</script>

<div class="text-container {getClasses($$props.class)}" class:skeleton style={getStyle($$props.style)}>
    {#if skeleton}
        <SkeletonText/>
    {:else}
        <slot/>
    {/if}
</div>

<style>

    :global(.text-container .bx--skeleton__text) {
        margin: 0;
    }

    .text-container {
        align-items: center;
        border-radius: .25rem;
        width: var(--custom-width, 100%);
    }

    .text-container.skeleton {
        overflow: hidden;
    }

</style>