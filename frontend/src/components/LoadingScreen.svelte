<script lang="ts">
    import {Circle3} from "svelte-loading-spinners";

    export let show: boolean;

</script>

{#if show}
    <div class="loading-background">
        <div class="loading-spinner">
            <Circle3/>
        </div>
    </div>
{/if}

<style>

    .loading-background {
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;

        display: flex;
        justify-content: center;
        align-items: center;

        background: var(--cds-background);
        z-index: 1111;
    }

    .loading-spinner {
        user-select: none;
    }

</style>