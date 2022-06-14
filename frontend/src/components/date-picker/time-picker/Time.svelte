<script lang="ts">

    import TimeComponent from "./TimeComponent.svelte";

    export let value: Date;
</script>

<div class="time-holder">
    <div class="time-label">
        <span>Время:</span> <!-- TODO: l10n -->
    </div>
    <TimeComponent bind:value/>
</div>

<style>
    .time-holder {
        width: 100%;
        height: 3rem;

        display: flex;
        flex-direction: row;
        align-items: center;
        padding: 12px .25rem 12px 16px;
    }

    .time-label {
        flex-grow: 1;
    }
</style>