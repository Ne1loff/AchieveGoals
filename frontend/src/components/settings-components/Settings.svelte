<script lang="ts">

    import ModalWindow from "../modals/ModalWindow.svelte";
    import {navigate} from "svelte-routing";
    import {getProjectPath} from "../../utils/location-utils";
    import SettingsSideBar from "./SettingsSideBar.svelte";
    import {SETTING_BUTTONS} from "../../resources/constants";
    import {SettingsUrl} from "../../resources/basicFilter.config";
    import SettingsAccountTab from "./tabs/SettingsAccountTab.svelte";
    import SettingsGeneralTab from "./tabs/SettingsGeneralTab.svelte";
    import SettingsFooter from "./SettingsFooter.svelte";
    import type {Task} from "../utils";

    export let props: { [k: string]: string };

    let wasChange: boolean;

    const navigateToHome = () => navigate(getProjectPath());
    let onChange: Task = {
        accept: () => {}, reject: () => {}
    };

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
        <h2>{SETTING_BUTTONS.find(it => it.obj === props?.tab).title}</h2>
    </svelte:fragment>
    <svelte:fragment slot="sidebar">
        <div class="settings-sidebar">
            <SettingsSideBar open params={props}/>
        </div>
    </svelte:fragment>
    <svelte:fragment slot="content">
        {#if props?.tab === SettingsUrl.ACCOUNT}
            <SettingsAccountTab bind:wasChange bind:onChange/>
        {:else if props?.tab === SettingsUrl.GENERAL}
            <SettingsGeneralTab/>
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

    .settings-sidebar {
        position: relative;
    }

</style>