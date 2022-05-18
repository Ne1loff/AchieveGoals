<script lang="ts">

    export let open: boolean = true;
    export let collapsed: boolean = false;

</script>

<style>

    :root {
        --own-sidebar-padding: 0px;
        --own-sidebar-margin: 0px;
        --own-sidebar-height: 100%;
        --own-sidebar-width: 16rem;
        --own-sidebar-justify-content: space-between;
        --own-sidebar-align-items: center;
        --own-sidebar-align-content: center;
        --own-sidebar-background: #ccc;
        --own-sidebar-transition-time: 0.3s;
        --own-sidebar-collapsed-width: 4rem;
    }

    .side-bar {
        padding: var(--own-sidebar-padding);
        margin: var(--own-sidebar-margin);

        height: var(--own-sidebar-height);
        width: 0;

        display: flex;
        flex-direction: column;

        background: var(--own-sidebar-background);

        justify-content: var(--own-sidebar-justify-content);
        align-items: var(--own-sidebar-align-items);
        align-content: var(--own-sidebar-align-content);

        transition: width var(--own-sidebar-transition-time) ease-in-out;
    }

    .side-bar.open {
        width: var(--own-sidebar-width);
    }

    .side-bar.collapsed {
        min-width: var(--own-sidebar-collapsed-width);
    }

    .side-bar-top, .side-bar-center, .side-bar-bottom {
        width: 100%;
        height: calc(100% / 3);
    }

</style>

<div class="side-bar" class:open class:collapsed>
    <div class="side-bar-top">
        <slot name="top"></slot>
    </div>
    <div class="side-bar-center">
        <slot name="center"></slot>
    </div>
    <div class="side-bar-bottom">
        <slot name="bottom"></slot>
    </div>
</div>