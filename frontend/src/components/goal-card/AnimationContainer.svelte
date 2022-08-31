<script lang="ts">

    import {slide} from 'svelte/transition';

    export let animate: boolean = false;
    export let animationDurationIn: number = 200;
    export let animationDurationOut: number = 200;

</script>

{#if animate}
    <div in:slide={{ duration:  animationDurationIn }}
         out:slide={{ duration:  animationDurationOut }}>
        <slot/>
    </div>
{:else}
    <slot/>
{/if}

<style>

</style>