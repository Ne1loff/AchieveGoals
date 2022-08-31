<script lang="ts">

    export let open: boolean = true;
    export let duration: number = 286;
    export let containerHeight: number;

    let height: string = 'auto';

    const changeHeight = () => {
        height = `${containerHeight}px`;
    }


    $: {
        changeHeight();
        containerHeight;
    }

</script>

<div class:container={true} class:open
     style="transition-duration: {duration}ms; --collapse-entire-height: {height};">
    <slot/>
</div>

<style>

    :root {
        --collapse-entire-height: auto;
    }

    .container {
        height: 0;
        overflow: hidden;
        transition: height .3s cubic-bezier(0.4, 0, 0.2, 1) 0ms;
    }

    .container.open {
        height: var(--collapse-entire-height);
    }

</style>