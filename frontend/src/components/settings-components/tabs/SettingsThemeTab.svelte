<script lang="ts">

    import SettingsAbstractTab from "./SettingsAbstractTab.svelte";
    import SettingsInfoBlock from "./SettingsInfoBlock.svelte";
    import {setLanguage} from "../../../resources/localization/l10n";
    import ThemeSelect from "../../ThemeSelect.svelte";


    const changeLanguage = (value: CustomEvent) => {
        setLanguage(value.detail);
    }

</script>

<SettingsAbstractTab --ag-bnt-border-radius=".5rem">
    <SettingsInfoBlock>
        <div class="general-info">
            <h4>Тема</h4>
            <ThemeSelect/>
        </div>
    </SettingsInfoBlock>
</SettingsAbstractTab>

<style>

</style>