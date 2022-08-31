<script lang="ts">

    export let open: boolean;
    const toggle = () => {
        changeOnMaxDuration();
        open = !open;
    };

    let animationDuration: number = 0;

    const changeOnMaxDuration = () => animationDuration = 200;
    const changeOnMinDuration = () => animationDuration = 50;

    let containerInnerHeight: number;
</script>

<div class="ag-accordion">
    <div class="accordion-header">
        <slot name="header" {toggle}/>
    </div>
    <div class="accordion-container" class:expanded={open} on:transitionend={changeOnMinDuration}
         style="transition-duration: {animationDuration}ms; --ag-accordion-height: {containerInnerHeight}px;"
    >
        <div class="accordion-container-inner" bind:clientHeight={containerInnerHeight}>
            <slot/>
        </div>
    </div>
</div>

<style>

    :root {
        --ag-accordion-height: 10rem;
    }

    .accordion-container {
        overflow: hidden;
        height: 0;

        transition: height .2s cubic-bezier(0.4, 0, 0.2, 1);
    }

    .accordion-container.expanded {
        height: var(--ag-accordion-height);
    }


</style>