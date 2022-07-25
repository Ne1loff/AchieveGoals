<script lang="ts">
    import MenuContainer from "../modals/MenuContainer.svelte";
    import {link, navigate} from "svelte-routing";
    import {onMount} from "svelte";
    import User from "../../data/models/User";
    import {USER} from "../../data/storage/storage";
    import Icon from "@iconify/svelte";
    import ServiceFactory from "../../services/ServiceFactory";

    let user: User;

    const logout = () => {
        ServiceFactory.INSTANCE.signUIOService.logOut()
            .then(() => {
                if (typeof $$props.closePopover === "function") {
                    $$props.closePopover();
                }
                navigate("/login");
            });
    }

    onMount(() => {
        user = $USER;
    })

</script>

<MenuContainer width="268"
               --menu-container-padding="6px 0"
               --menu-container-hr-margin="4px 0"
               --menu-container-border-radius="10px"
               classes="elevation-2"
>
    <a slot="header" class="user-settings menu-item" use:link href="/settings/account">
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
        <a class="menu-item" use:link href="/settings/theme">
            <div class="action">
                <div class="icon">
                    <Icon icon="carbon:color-palette" width="20" color="var(--cds-icon-02)"/>
                </div>
                <span>Тема</span> <!--    TODO: l10n        -->
            </div>
        </a>
        <a class="menu-item" use:link href="/application">
            <div class="action">
                <div class="icon">
                    <Icon icon="carbon:application-mobile" width="20" color="var(--cds-icon-02)"/>
                </div>
                <span>Установить приложение</span> <!--    TODO: l10n        -->
            </div>
        </a>
        <hr/>
        <a class="menu-item" use:link href="/user/teams">
            <div class="action">
                <div class="icon">
                    <Icon icon="fluent:people-team-20-regular" width="20" color="var(--cds-inverse-support-03)"/>
                </div>
                <span>Команды</span> <!--    TODO: l10n        -->
            </div>
        </a>
        <a class="menu-item" use:link href="/premium">
            <div class="action">
                <div class="icon">
                    <Icon icon="fluent:premium-20-regular" width="20" color="var(--cds-inverse-support-04)"/>
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
        border-radius: 5px;
        box-sizing: border-box;
        text-decoration: none;

        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: left;
    }

    .menu-item:hover {
        background-color: var(--cds-field-hover);
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