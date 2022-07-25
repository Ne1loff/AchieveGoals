<script lang="ts">
    import {popoverTrigger, SvelteComponentOptions} from "./popover/global/Popover";
    import Icon from "@iconify/svelte";

    export let src: string = undefined;
    export let useIcon: boolean = true;
    export let iconifyProps: { icon: string, height: number, width: number, color: string } = {
        icon: "fe:app-menu",
        height: 24, width: 24,
        color: "var(--cds-icon-01)"
    }
    export let dropdownComponent: SvelteComponentOptions = undefined;


</script>

<div class="top-bar-menu">
    <button
            use:popoverTrigger={{
                        component: dropdownComponent,
                        useOverlay: false,
                        style: {
                            "--own-popover-border-radius": "10px"
                        }
                    }}
    >
        {#if src}
            <img {src} alt="picture"/>
        {:else if useIcon}
            <Icon {...iconifyProps}/>
        {/if}
    </button>
</div>

<style>
    .top-bar-menu {
        margin: .25rem;
        background-color: inherit;
    }

    .top-bar-menu button {
        background-color: inherit;
    }
</style>