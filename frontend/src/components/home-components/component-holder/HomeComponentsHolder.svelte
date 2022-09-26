<script lang="ts">
    import {Svrollbar} from "svrollbar";

    export let withoutHeader: boolean = false;

    let viewport: Element;
    let contents: Element;

    let overflowTop: boolean = false;

    const scroll = () => (overflowTop = viewport.scrollTop > 0);
</script>

<div class="holder">
    <div class="holder-inner"
         bind:this={viewport}
         on:scroll={scroll}>
        {#if !withoutHeader}
            <header class="header" data-overflow-top={overflowTop ? '' : undefined}>
                <div class="header-content">
                    <slot name="head"/>
                </div>
            </header>
        {/if}
        <div class="content-wrapper">
            <div class="goals-content">
                <div class="content-inner" bind:this={contents}>
                    <slot/>
                </div>
            </div>
        </div>
        <Svrollbar {viewport} {contents}/>
    </div>
</div>

<style>

    :root {
        --holder-padding-left: 28px;
        --holder-padding-right: 55px;
    }

    :global(.v-scrollbar) {
        z-index: 2 !important;
    }

    :global(.v-scrollbar:hover) {
        --svrollbar-track-width: 1rem;
        --svrollbar-thumb-width: .5rem;
    }

    :global(.v-scrollbar:active) {
        --svrollbar-track-width: 1rem;
        --svrollbar-thumb-width: .5rem;
    }

    .collapse-button span {
        display: flex;
        align-items: center;
        justify-content: flex-start;
    }

    .holder {
        display: flex;
        flex-direction: column;
        flex-grow: 1;
        vertical-align: top;

        margin: .25rem 0;

        max-height: 100%;
        height: calc(100% - .5rem);

        position: relative;
        box-sizing: content-box;
    }

    .holder-inner {
        overflow: scroll;
        -ms-overflow-style: none;
        scrollbar-width: none;

        flex-grow: 1;
    }

    .holder-inner::-webkit-scrollbar {
        display: none;
    }

    .header {
        display: flex;
        flex-shrink: 0;

        background: var(--cds-ui-background);

        position: sticky;
        top: 0;
        z-index: 1;
        padding-left: 55px;
        padding-right: 55px;
        padding-top: 36px;
    }

    .header-content {
        display: flex;
        align-items: center;
        justify-content: space-between;

        width: 100%;
        padding-bottom: 8px;
        max-width: 800px;
        margin: 0 auto;
        border-bottom: 1px solid transparent;
        transition: border-bottom-color .3s;
        word-break: break-word;
    }

    .header[data-overflow-top] .header-content {
        border-bottom-color: var(--cds-ui-03);
    }

    .content-wrapper {
        position: relative;
        display: flex;
        justify-content: center;

        min-height: calc(100% - 81px);

        padding-bottom: 84px;
    }

    .goals-content {
        position: relative;

        flex-grow: 1;

        padding-left: var(--holder-padding-left);
        padding-right: var(--holder-padding-right);
        max-width: 828px;
        min-width: 412px;
        box-sizing: content-box;
    }

</style>