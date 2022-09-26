<script lang="ts">

    import SettingsAbstractTab from "./SettingsAbstractTab.svelte";
    import SettingsInfoBlock from "./SettingsInfoBlock.svelte";
    import {Select, SelectItem} from "carbon-components-svelte";
    import {CurrentLanguage, LanguagesNames} from "../../../resources/localization/languages/languages";
    import {setLanguage} from "../../../resources/localization/l10n";


    const changeLanguage = (value: CustomEvent) => {
        setLanguage(value.detail);
    }

</script>

<SettingsAbstractTab --ag-bnt-border-radius=".5rem">
    <SettingsInfoBlock>
        <div class="general-info">
            <h4>Язык</h4>
            <Select selected={$CurrentLanguage}
                    on:change={changeLanguage}
            >
                {#each LanguagesNames as lang}
                    <SelectItem value={lang.value} text={lang.name}/>
                {/each}
            </Select>
        </div>
    </SettingsInfoBlock>
</SettingsAbstractTab>

<style>

</style>