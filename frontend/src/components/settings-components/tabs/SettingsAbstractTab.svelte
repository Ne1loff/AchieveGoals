<script lang="ts">
    import {links} from "svelte-routing";
</script>

<div class="settings-container">
    <div class="settings-inner" use:links>
        <slot/>
    </div>
</div>

<style>

    .settings-container {
        height: 100%;
        overflow: auto;

        scrollbar-width: .5rem;
        scrollbar-color: var(--cds-active-ui) var(--cds-ui-background);
    }

    .settings-container::-webkit-scrollbar {
        width: .5rem;
    }

    .settings-container::-webkit-scrollbar-track {
        background: var(--cds-ui-background);
    }

    .settings-container::-webkit-scrollbar-thumb {
        background: var(--cds-active-ui);
        border-radius: 1rem;
    }

    .settings-inner {
        padding: 1rem 1rem 1.5rem 1rem;
    }

</style>