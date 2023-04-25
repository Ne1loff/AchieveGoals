<script lang="ts">

    import SettingsAbstractTab from "./SettingsAbstractTab.svelte";
    import SettingsInfoBlock from "./SettingsInfoBlock.svelte";
    import {setLanguage} from "../../../resources/localization/l10n";
    import ThemesPreview from "../../theme/ThemesPreview.svelte";

    const changeLanguage = (value: CustomEvent) => {
        setLanguage(value.detail);
    }

</script>

<SettingsAbstractTab --ag-bnt-border-radius=".5rem">
    <SettingsInfoBlock>
        <div class="general-info">
            <h4>Тема</h4>
            <ThemesPreview/>
        </div>
    </SettingsInfoBlock>
</SettingsAbstractTab>

<style lang="scss">

    h4 {
      margin-bottom: .5rem;
    }

</style>