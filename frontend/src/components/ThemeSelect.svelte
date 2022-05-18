<script lang="ts">
    import {Select, SelectItem, Theme} from "carbon-components-svelte";
    import {THEMES} from "../resources/constants";

    let activeTheme: string = THEMES[0].value;
</script>

<Theme bind:theme={activeTheme} persist persistKey="__carbon-theme"/>
<Select bind:selected={activeTheme}>
    {#each THEMES as theme}
        <SelectItem value={theme.value} text={theme.text}/>
    {/each}
</Select>

<style>

    :global(.bx--select-input) {
        margin: 0 !important;
    }

    :global(.bx--select) {
        flex-direction: row !important;
    }
</style>