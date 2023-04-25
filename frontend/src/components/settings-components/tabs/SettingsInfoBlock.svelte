<script lang="ts">

    import {onMount} from "svelte";

    let child: HTMLElement;
    let isFirstChild: boolean;

    onMount(() =>{
        isFirstChild = child.parentElement.firstChild === child;
    })

</script>

{#if !isFirstChild}
    <hr class="spacing-xlarge" bind:this={child}/>
{/if}
<div class="info-block spacing-xlarge">
    <slot/>
</div>

<style>

    hr {
        border: none;
        border-bottom: 1px solid var(--cds-border-subtle);
        margin: 0;
    }

</style>