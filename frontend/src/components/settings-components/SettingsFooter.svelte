<script lang="ts">

    import Button from "../button/Button.svelte";
    import type {onSettingsChange} from "./utils";

    export let onChange: onSettingsChange;
</script>

<footer class="settings-footer"
        style:--ag-bnt-margin=".75rem 0 0 10px"
>
    <Button size="small" kind="secondary" on:click={onChange.reject}>Отмена</Button>
    <Button size="small" kind="danger" on:click={onChange.accept}>Изменить</Button>
</footer>

<style>

    .settings-footer {
        padding: 0 .75rem .75rem .75rem;

        display: flex;
        align-items: center;
        justify-content: flex-end;
    }

</style>