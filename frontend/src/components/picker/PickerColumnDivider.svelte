<script lang="ts">
    export let divider: string = ':'
</script>

<div class="picker-column column-picker-divider">{divider}</div>

<style>
    .picker-column {
        position: relative;
        max-height: 100%;
        z-index: 1;
    }

    .picker-column.column-picker-divider {
        display: flex;
        align-items: center;
    }
</style>