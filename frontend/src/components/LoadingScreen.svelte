<script lang="ts">
    import {Circle3} from "svelte-loading-spinners";

    export let show: boolean;

</script>

<div class="loading-background" class:show>
    <div class="loading-spinner">
        <Circle3/>
    </div>
</div>

<style>

    .loading-background {
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;

        display: none;
        justify-content: center;
        align-items: center;

        background: var(--cds-background);
        z-index: 1111;
    }

    .loading-background.show {
        display: flex;
    }

</style>