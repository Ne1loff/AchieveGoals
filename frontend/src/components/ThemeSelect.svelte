<script lang="ts">
    import {Select, SelectItem} from "carbon-components-svelte";
    import {THEMES} from "../resources/constants";
    import ThemeManager, {CarbonTheme} from "./theme/ThemeManager.ts";
    import {onMount} from "svelte";

    let themeManager: ThemeManager
    let activeTheme: CarbonTheme;
    $: if (themeManager && activeTheme) themeManager.setTheme(activeTheme);


    onMount(() => {
      themeManager = ThemeManager.getInstance();
      activeTheme = themeManager.getCurrentTheme();
    })
</script>

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