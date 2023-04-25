<script lang="ts">
    import Icon from "@iconify/svelte";
    import {SettingsUrl} from "../../resources/basicFilter.config";
    import Sidebar from "../Sidebar.svelte";
    import HomeSideBarButton from "../home-components/sidebar/HomeSideBarButton.svelte";
    import {SETTING_BUTTONS} from "../../resources/constants";

    export let open: boolean;
    export let params: { tab?: string };

    const isSelected = (url: string): boolean => params?.tab === url;

    const selected: { [k: string]: boolean } = {
        [SettingsUrl.ACCOUNT]: false,
        [SettingsUrl.GENERAL]: false,
        [SettingsUrl.THEME]: false,
        [SettingsUrl.PRODUCTIVITY]: false,
        [SettingsUrl.REMINDERS]: false,
        [SettingsUrl.NOTIFICATIONS]: false
    }

    $: {
        if (params?.tab) {
            for (let selectedKey in selected) {
                selected[selectedKey] = isSelected(selectedKey);
            }
        }
    }

</script>

<Sidebar bind:open
         --own-sidebar-background="var(--cds-layer)"
         --own-sidebar-transition-time=".25s"
         --own-sidebar-padding=".5rem"
         --own-sidebar-display="block"
>
    <svelte:fragment slot="top">
        <h4 class="settings-title">Настройки</h4>
        <div class="action-buttons"
             style="--sidebar-btn-padding: 0 .25rem 0 .5rem; --sidebar-btn-margin: .125rem .5rem 0 0">
            {#each SETTING_BUTTONS as settingBtn}
                <HomeSideBarButton title={settingBtn.title} href={settingBtn.href} selected={selected[settingBtn.obj]}>
                    <Icon slot="icon" icon={settingBtn.icon}
                          style="width: 1.75rem; height: 1.75rem; padding: 4px; color: #808080;"/>
                </HomeSideBarButton>
            {/each}
        </div>
    </svelte:fragment>
</Sidebar>

<style>

    .action-buttons {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin-left: .25rem;
    }

    .settings-title {
        margin-left: .5rem;
        margin-bottom: 2rem;
    }

    .icon-badge span {
        width: 100%;
        height: 100%;
        transform: translateY(4px);
    }

    :global(a.side-bar-button .icon-badge span) {
        transform: translate(3px, 6px) !important;
    }

</style>