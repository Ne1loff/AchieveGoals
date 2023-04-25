<script lang="ts">
    import ThemeManager, {CarbonTheme} from "./ThemeManager.ts";
    import {onMount} from "svelte";
    import ThemePreview from "../svg/ThemePreview.svelte";

    let themeManager: ThemeManager;
    let selectedTheme: CarbonTheme;

    const selectTheme = (theme: CarbonTheme) => {
        themeManager.setTheme(theme)
    };

    onMount(() => {
        themeManager = ThemeManager.getInstance();
        selectedTheme = themeManager.getCurrentTheme();
        themeManager.onThemeChanged((theme) => selectedTheme = theme);
    })

</script>

<div class="ag-theme-preview-selector">
    <button class:theme-selector={true} on:click={() => selectTheme("white")}>
        <ThemePreview selected={selectedTheme === "white"}
                      title="White"
        />
    </button>
    <button class:theme-selector={true} on:click={() => selectTheme("g10")}>
        <ThemePreview selected={selectedTheme === "g10"}
                      title="G10"
                      backgroundColor="#fff"
                      mainContentColor="#f4f4f4"
                      buttonColor="#393939"
                      textColor="#161616"
                      skeletonColor="#e5e5e5"
        />
    </button>
    <button class:theme-selector={true} on:click={() => selectTheme("g80")}>
        <ThemePreview selected={selectedTheme === "g80"}
                      title="G80"
                      backgroundColor="#525252"
                      mainContentColor="#393939"
                      buttonColor="#6f6f6f"
                      textColor="#f4f4f4"
                      skeletonColor="#474747"
        />
    </button>
    <button class:theme-selector={true} on:click={() => selectTheme("g90")}>
        <ThemePreview selected={selectedTheme === "g90"}
                      title="G90"
                      backgroundColor="#393939"
                      mainContentColor="#262626"
                      buttonColor="#6f6f6f"
                      textColor="#f4f4f4"
                      skeletonColor="#353535"
        />
    </button>
    <button class:theme-selector={true} on:click={() => selectTheme("g100")}>
        <ThemePreview selected={selectedTheme === "g100"}
                      title="Black"
                      backgroundColor="#262626"
                      mainContentColor="#161616"
                      buttonColor="#6f6f6f"
                      textColor="#f4f4f4"
                      skeletonColor="#353535"
        />
    </button>

</div>

<style lang="scss">

  .theme-selector {
    all: unset;
    border: 1px solid var(--cds-border-subtle);
    border-radius: .25rem;

    height: 77px;

    &:hover {
      -webkit-transform: translateY(-3px);
      transform: translateY(-3px);
      box-shadow: 0 1px 8px 0 rgb(0 0 0 / 8%);
    }

    &:focus {
      border-color: var(--cds-focus, #0f62fe);
      box-shadow: inset 0 0 0 1px var(--cds-focus, #0f62fe),inset 0 0 0 2px var(--cds-ui-background, #ffffff);
    }
  }

  .ag-theme-preview-selector {
    display: flex;
    gap: 1rem;
    flex-wrap: wrap;
  }

</style>