<script lang="ts">

    import ModalWindow from "../modals/ModalWindow.svelte";
    import {navigate} from "svelte-routing";
    import {getProjectPath} from "../../utils/location-utils";
    import SettingsSideBar from "./SettingsSideBar.svelte";
    import {SETTING_BUTTONS, SETTINGS_SUB_TABS} from "../../resources/constants";
    import {SettingsSubUrl, SettingsUrl} from "../../resources/basicFilter.config";
    import SettingsAccountTab from "./tabs/SettingsAccountTab.svelte";
    import SettingsGeneralTab from "./tabs/SettingsGeneralTab.svelte";
    import SettingsFooter from "./SettingsFooter.svelte";
    import SettingsThemeTab from "./tabs/SettingsThemeTab.svelte";
    import Button from "../button/Button.svelte";
    import Icon from "@iconify/svelte";
    import SettingsEmailSubTab from "./tabs/sub-tabs/SettingsEmailSubTab.svelte";
    import SettingsPasswordSubTab from "./tabs/sub-tabs/SettingsPasswordSubTab.svelte";
    import type {onSettingsChange} from "./utils";

    export let props: { [k: string]: string };

    let wasChange: boolean = false;

    $:console.log(props);

    const navigateToHome = () => navigate(getProjectPath());
    let onChange: onSettingsChange = {
        accept: () => {}, reject: () => {},
        disableAcceptBtn: false,
        disableRejectBtn: false
    };
    const resetOnChange = () => {
        onChange.disableAcceptBtn = false;
        onChange.disableRejectBtn = false;
    }

    $: if (!wasChange) resetOnChange();

    let currentTab, currentSubTab;
    $: currentTab = SETTING_BUTTONS.find(it => it.obj === props?.tab);
    $: currentSubTab = SETTINGS_SUB_TABS.find(it => it.obj === props?.subTab);

</script>


<ModalWindow useViaSidebar
             on:close={navigateToHome}
             styleClasses={['settings-window', 'elevation-9']}
             --menu-container-hr-margin="0 -.75rem"
             --own-modal-max-height="40rem"
             --own-modal-min-height="30rem"
             --own-overlay-bg="#131313bf"
             --own-modal-container-header-padding=".5rem .5rem .5rem 1rem"
             showFooter={wasChange}
>
    <svelte:fragment slot="header">
        {#if !currentSubTab}
            <h2>{currentTab.title}</h2>
        {:else}
            <div class="settings-header">
                <Button kind="ghost"
                        size="small"
                        --ag-bnt-border-radius=".5rem"
                        --ag-bnt-padding="0"
                        on:click={() => navigate(currentTab.href)}>
                    <Icon icon="carbon:arrow-left" width="28"/>
                </Button>

                <h2>{currentSubTab.title}</h2>
            </div>
        {/if}
    </svelte:fragment>
    <svelte:fragment slot="sidebar">
        <div class="settings-sidebar">
            <SettingsSideBar open params={props}/>
        </div>
    </svelte:fragment>
    <svelte:fragment slot="content">
        {#if props?.tab === SettingsUrl.ACCOUNT}
            {#if props?.subTab === SettingsSubUrl.EMAIL}
                <SettingsEmailSubTab bind:wasChange bind:onChange {currentTab}/>
            {:else if props?.subTab === SettingsSubUrl.PASSWORD}
                <SettingsPasswordSubTab bind:wasChange bind:onChange {currentTab}/>
            {:else}
                <SettingsAccountTab bind:wasChange bind:onChange/>
            {/if}
        {:else if props?.tab === SettingsUrl.GENERAL}
            <SettingsGeneralTab/>
        {:else if props?.tab === SettingsUrl.THEME}
            <SettingsThemeTab/>
        {/if}
    </svelte:fragment>
    <svelte:fragment slot="footer">
        <SettingsFooter {onChange}/>
    </svelte:fragment>
</ModalWindow>


<style>

    :global(.settings-window) {
        width: 54rem;
        padding: 0;

        border-radius: 16px;

        background-color: var(--cds-background);
    }

    .settings-header {
        display: flex;
        gap: .5rem;
        align-items: center;
    }

    .settings-sidebar {
        position: relative;
    }

</style>