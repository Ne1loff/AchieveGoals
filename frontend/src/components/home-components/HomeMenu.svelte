<script lang="ts">
    import MenuContainer from "../modals/MenuContainer.svelte";
    import {link, navigate} from "svelte-routing";
    import {onMount} from "svelte";
    import User from "../../data/models/User";
    import {USER} from "../../data/storage/storage";
    import Icon from "@iconify/svelte";
    import ServiceFactory from "../../services/ServiceFactory";
    import {hrefs} from "../../resources/config";
    import {SettingsUrl} from "../../resources/basicFilter.config";

    let user: User;

    const logout = () => {
        ServiceFactory.INSTANCE.signUIOService.logOut()
            .then(() => {
                close();
                navigate(hrefs.login);
            });
    }

    const close = () => typeof $$props.closePopover === "function" ? $$props.closePopover() : '';


    onMount(() => {
        user = $USER;
    })

</script>

<MenuContainer width="268"
               --menu-container-padding="6px 0"
               --menu-container-hr-margin="4px 0"
               --menu-container-border-radius="16px"
               classes="elevation-2"
>
    <a slot="header" class="user-settings menu-item" use:link href={hrefs.settings(SettingsUrl.ACCOUNT)}
       on:click={close}
    >
        {#if user}
            <div class="user-info">
                <div class="user-avatar">
                    <img src={user.avatar} alt="User avatar" height="48" width="48"/>
                </div>
                <div class="user-details">
                    <strong>{user.username}</strong>
                    <p>{user.email}</p>
                </div>
            </div>
            <div class="action">
                <div class="icon">
                    <Icon icon="carbon:settings" width="20" color="var(--cds-icon-02)"/>
                </div>
                <span>Настройки</span> <!--    TODO: l10n        -->
            </div>
        {/if}
    </a>
    <svelte:fragment slot="content">
        <a class="menu-item" use:link href={hrefs.settings(SettingsUrl.THEME)} on:click={close}>
            <div class="action">
                <div class="icon">
                    <Icon icon="carbon:color-palette" width="20" color="var(--cds-icon-02)"/>
                </div>
                <span>Тема</span> <!--    TODO: l10n        -->
            </div>
        </a>
        <a class="menu-item" use:link href={hrefs.mobile} on:click={close}>
            <div class="action">
                <div class="icon">
                    <Icon icon="carbon:application-mobile" width="20" color="var(--cds-icon-02)"/>
                </div>
                <span>Установить приложение</span> <!--    TODO: l10n        -->
            </div>
        </a>
        <hr/>
        <a class="menu-item" use:link href={hrefs.teams} on:click={close}>
            <div class="action">
                <div class="icon">
                    <Icon icon="fluent:people-team-20-regular" width="20" color="var(--cds-support-03)"/>
                </div>
                <span>Команды</span> <!--    TODO: l10n        -->
            </div>
        </a>
        <a class="menu-item" use:link href={hrefs.premium} on:click={close}>
            <div class="action">
                <div class="icon">
                    <Icon icon="fluent:premium-20-regular" width="20" color="var(--cds-support-04)"/>
                </div>
                <span>Перейти на премиум</span> <!--    TODO: l10n        -->
            </div>
        </a>
    </svelte:fragment>
    <svelte:fragment slot="footer">
        <div class="menu-item" on:click={logout}>
            <div class="action">
                <div class="icon">
                    <Icon icon="carbon:logout" width="20" color="var(--cds-icon-02)"/>
                </div>
                <span>Выход</span> <!--    TODO: l10n        -->
            </div>
        </div>
    </svelte:fragment>
</MenuContainer>

<style>

    hr {
        width: 100%;
        border: 0;
        border-bottom: 1px solid var(--cds-border-subtle);
        margin: var(--menu-container-hr-margin);
    }

    .menu-item {
        padding: 0 6px;
        margin: 0 6px;
        background: inherit;
        color: var(--cds-text-01);
        border-radius: 16px;
        box-sizing: border-box;
        text-decoration: none;

        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: left;

        cursor: pointer;
        user-select: none;
    }

    .menu-item:hover {
        background-color: var(--cds-field-hover);
    }

    .user-settings {
        padding-top: .5rem;
    }

    .user-info, .action {
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: left;
    }

    .action {
        height: 2rem;
    }

    .icon {
        margin-left: 3px;
        margin-right: .5rem;
    }

    .user-avatar {
        height: 48px;
        width: 48px;

        margin-right: 14px;
        margin-left: 3px;

        overflow: hidden;

        display: flex;
        align-items: center;
        justify-content: center;

        border: 1px solid rgba(0, 0, 0, .1);
        border-radius: 50%;
    }

    .user-details strong {
        font-size: var(--cds-heading-02-font-size);
    }

    .user-details p {
        color: var(--cds-text-secondary);
    }

</style>