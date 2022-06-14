<script lang="ts">
    import OverflowMenuVertical from "carbon-icons-svelte/lib/OverflowMenuVertical.svelte";
    import {Button} from "carbon-components-svelte";
    import Icon from "@iconify/svelte";
    import {GOALS} from "../../data/storage/storage";
    import GoalComponent from "../goal-card/GoalComponent.svelte";
    import {Svrollbar} from "svrollbar";


    let viewport: Element;
    let contents: Element;

    let overflowTop: boolean = false;

    let goals = $GOALS;
    GOALS.subscribe((g) => {
        goals = g;
    });

    let scrollable: boolean = true;

    const scroll = () => (overflowTop = viewport.scrollTop > 0);

</script>

<div class="holder">
    <div class="holder-inner"
         style="overflow: {scrollable ? 'scroll' : 'hidden'}"
         bind:this={viewport}
         on:scroll={scroll}>
        <header class="header" data-overflow-top={overflowTop ? '' : undefined}>
            <div class="header-content">
                <h1>Goals</h1>
                <div class="header-actions">
                    <Button kind="ghost" size="small" as let:props>
                        <p {...props} class:button-p={true}>
                        <span class="button-span-icon">
                            <Icon icon="akar-icons:settings-horizontal"
                                  width="18" height="18" color="var(--cds-icon-01)"/>
                        </span>
                            <span class="button-span">Отображение</span>
                        </p>
                    </Button>
                    <Button kind="ghost" size="small" as let:props>
                        <p {...props} class:button-p={true}>
                        <span class="button-span-icon">
                            <OverflowMenuVertical size="18"/>
                        </span>
                        </p>
                    </Button>
                </div>
            </div>
        </header>
        <div class="content-wrapper">
            <div class="goals-content">
                <div class="content-inner" bind:this={contents}>
                    {#each goals.filter((it) => (it.gid === null)) as goal}
                        <GoalComponent bind:goal/>
                    {/each}
                </div>
            </div>
        </div>
        <Svrollbar {viewport} {contents}/>
    </div>
</div>

<style>

    :global(.v-scrollbar) {
        z-index: 999 !important;
    }

    :global(.v-scrollbar:hover) {
        --svrollbar-track-width: 1rem;
        --svrollbar-thumb-width: .5rem;
    }

    :global(.v-scrollbar:active) {
        --svrollbar-track-width: 1rem;
        --svrollbar-thumb-width: .5rem;
    }

    .button-p {
        border-radius: 5px;
        min-width: 2rem;
        padding: 0 !important;
        justify-content: center;
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

    .button-span {
        margin-left: 3px;
        margin-right: 6px;

        color: var(--cds-text-02);
    }

    .button-span-icon {
        height: 1.5rem;
        width: 1.5rem;
        display: flex;
        justify-content: center;
        align-items: center;
        color: var(--cds-icon-01);
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

    .header-actions {
        display: flex;
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

        padding-left: 28px;
        padding-right: 55px;
        max-width: 828px;
        min-width: 412px;
        box-sizing: content-box;
    }

</style>