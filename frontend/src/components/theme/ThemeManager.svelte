<script lang="ts">

    import {Theme} from "carbon-components-svelte";
    import ThemeManager, {CarbonTheme} from "./ThemeManager.ts";
    import {onMount} from "svelte";

    let themeComponent: Theme;
    let themeManager: ThemeManager;
    let currentTheme: CarbonTheme;

    onMount(() => themeManager = new ThemeManager(themeComponent, currentTheme));
</script>

<Theme bind:this={themeComponent} bind:theme={currentTheme} persist persistKey="__carbon-theme"/>

