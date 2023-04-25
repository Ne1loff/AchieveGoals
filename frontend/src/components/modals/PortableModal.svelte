<script lang="ts">

    import Portal from "../Portal.svelte";

    let target: HTMLElement;

    let show: boolean = false;
    const toggle = () => show = !show;
    const close = () => show = false;

</script>

<div bind:this={target} class="portal">
    <slot name="target" {toggle}/>
</div>
{#if show}
    <Portal target="#popover-holder">
        <slot name="content" {close}/>
    </Portal>
{/if}

<style lang="scss">

  .portal {
    display: contents;
  }

</style>