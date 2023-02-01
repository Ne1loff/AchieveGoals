import type {Theme} from "carbon-components-svelte";
import type {CarbonTheme} from "carbon-components-svelte/types/Theme/Theme.svelte";

type ToggleColors = {
    dark: "g80" | "g90" | "g100";
    light: "white" | "g10";
}

class ThemeManager {

    private static instance: ThemeManager;
    private carbonThemeComponent: Theme;
    private currentTheme: CarbonTheme;

    private toggleColors: ToggleColors = {
        dark: "g100",
        light: "white"
    }

    public constructor(carbonTheme: Theme, currentTheme: CarbonTheme) {
        this.carbonThemeComponent = carbonTheme;
        this.currentTheme = currentTheme;

        if (ThemeManager.instance) {
            console.error("Instance of ThemeManager already exist. Please get instance of ThemeManager from static method. ThemeManager::getInstance()");
            return ThemeManager.instance;
        }

        this.onThemeChanged((theme) => this.currentTheme = theme);
        ThemeManager.instance = this;
    }

    public static getInstance(): ThemeManager {
        if (!this.instance)
            throw Error("You need to put the \"ThemeManager.svelte\" component in one of your components.")
        return this.instance;
    }

    public onThemeChanged(callback: (theme: CarbonTheme) => void) {
        this.carbonThemeComponent.$on("update", ({detail}) => {
            callback(detail.theme);
        })
    }

    public setTheme(theme: CarbonTheme) {
        this.carbonThemeComponent.$set({theme: theme})
    }

    public setToggleColors(toggleColors: ToggleColors) {
        this.toggleColors = toggleColors;
    }

    public isDarkTheme(): boolean {
        return this.currentTheme === "g80" || this.currentTheme === "g90" || this.currentTheme === "g100";
    }

    public isLightTheme(): boolean {
        return this.currentTheme === "white" || this.currentTheme === "g10";
    }

    public toggleTheme() {
        this.setTheme(this.isLightTheme() ? this.toggleColors.dark : this.toggleColors.light);
    }

    public getCurrentTheme(): CarbonTheme {
        return this.currentTheme;
    }
}

export default ThemeManager;
export type {CarbonTheme}