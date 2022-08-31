<script lang="ts">
    import {Svrollbar} from "svrollbar";
    import Icon from "@iconify/svelte";
    import Accordion from "../../accordion/Accordion.svelte";

    export let withoutHeader: boolean = false;
    export let collapsible: boolean = false;
    export let collapsibleBtnTitle: string = 'Other';

    let viewport: Element;
    let contents: Element;

    let overflowTop: boolean = false;
    let expanded: boolean;

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
        {#if collapsible}
            <Accordion bind:open={expanded}>
                <svelte:fragment slot="header" let:toggle>
                    <div class="action-buttons">
                        <button class="collapse-button"
                                on:click={toggle}>
                            <span>
                                <span class="icon-holder" data-item-rotate={expanded ? '90' : '0'}>
                                    <Icon icon="uil:angle-right-b"
                                          width="24" height="24"
                                          color="color: var(--cds-icon-01)"
                                    />
                                </span>
                                <span>{collapsibleBtnTitle}</span>
                            </span>
                        </button>
                    </div>
                </svelte:fragment>
                <svelte:fragment>
                    <div class="content-wrapper">
                        <div class="goals-content">
                            <div class="content-inner" bind:this={contents}>
                                <slot/>
                            </div>
                        </div>
                    </div>
                </svelte:fragment>
            </Accordion>
        {:else}
            <div class="content-wrapper">
                <div class="goals-content">
                    <div class="content-inner" bind:this={contents}>
                        <slot/>
                    </div>
                </div>
            </div>
        {/if}
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

    .action-buttons {
        position: relative;
        width: 100%;
        margin: .25rem 0;

        display: flex;
        align-items: center;
        justify-content: space-between;


        color: var(--cds-text-01);
        background-color: inherit;
    }

    .icon-holder {
        transition: transform .1s linear;

        display: flex;
        align-items: center;
        justify-content: center;
    }

    .icon-holder[data-item-rotate="90"] {
        transform: rotate(90deg);
    }

    .action-buttons::after {
        position: absolute;
        left: 0;
        bottom: 0;
        content: "";
        margin-left: 29px;

        width: calc(100% - 29px);
        border: 0;
        border-bottom: 1px solid var(--cds-border-subtle);
    }

    .collapse-button {
        flex-grow: 1;
        height: 40px;
    }

    .collapse-button span {
        display: flex;
        align-items: center;
        justify-content: flex-start;
    }

    .collapse-button {
        cursor: pointer;

        border: none;
        color: var(--cds-text-01);
        background-color: inherit;
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

        min-height: 100%;

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