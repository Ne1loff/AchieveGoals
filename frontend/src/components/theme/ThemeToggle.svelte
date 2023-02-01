<script lang="ts">
    import ThemeManager from "../theme/ThemeManager.ts";
    import {onMount} from "svelte";
    import SunAndMoon from "../svg/SunAndMoon.svelte";

    let themeManager: ThemeManager;
    let isDarkTheme: boolean = false;
    const onThemeChanged = (_) => {
        isDarkTheme = themeManager.isDarkTheme();
    }

    const toggleTheme = () => {
        themeManager.toggleTheme();
    }

    onMount(() => {
        themeManager = ThemeManager.getInstance();
        themeManager.onThemeChanged(onThemeChanged);
        isDarkTheme = themeManager.isDarkTheme();
    });

</script>

<button
        class="theme-toggle"
        id="theme-toggle"
        aria-label="auto"
        aria-live="polite"
        data-theme={isDarkTheme ? "dark" : "light"}
        on:click={toggleTheme}
>
    <SunAndMoon/>
</button>

<style lang="scss">

  .theme-toggle {
    all: unset;
    --size: 2rem;

    background: none;
    border: none;
    padding: .25rem;

    inline-size: var(--size);
    block-size: var(--size);
    aspect-ratio: 1;
    border-radius: 50%;

    cursor: pointer;
    touch-action: manipulation;
    -webkit-tap-highlight-color: transparent;
    outline-offset: 5px;

    &:not(:disabled):active {
      background: none;
    }

    & > :global(svg) {
      inline-size: 100%;
      block-size: 100%;
      stroke-linecap: round;
    }

    &[data-theme="dark"] {
      --icon-fill: hsl(210 10% 70%);
      --icon-fill-hover: hsl(210 15% 90%);
    }

    @media (hover: none) {
      --size: 48px;
    }
  }

</style>