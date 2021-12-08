<script lang="ts">
    import {navigate} from "svelte-routing";
    import {clickOutside} from "../js/clickOutside";

    let dropdownTrigger;

    export let activePage = '';

    export let user = {
        username: '',
        name: '',
        surname: '',
        male: '',
        email: '',
        locality: '',
        avatar: ''
    };

    function logOut() {
        fetch('http://localhost:8080/api/logout', {
            method: 'POST'
        })
            .then(_ => navigate('/'))
    }

    //todo enter with Enter key
</script>


<header class="header">
    <div class="container">
        <div class="header__inner">
            <nav class="nav__bar">
                <div class="nav__bar__left-content">
                    <img class="logo" on:click={() => navigate('/home')} src="/static/logo_200x44.png" alt="logo">
                </div>
                <div class="nav__bar__right-content" use:clickOutside on:click_outside={() => dropdownTrigger = false}>
                    <img class="avatar__btn" src="{user.avatar}" alt="user_avatar"
                         on:click={() => dropdownTrigger = !dropdownTrigger}>
                    <div class="dropdown-menu"
                         style="display: {dropdownTrigger ? 'block' : 'none'}">
                        <div class="dropdown-item-profile">
                            <div class="dropdown-item-profile-inner">
                                <div class="profile__info_photo">
                                    <img class="profile__avatar" src="{user.avatar}"
                                         alt="avatar">
                                </div>
                                <div class="profile__info">
                                    <div class="profile__info__username">{user.username}</div>
                                    <div class="profile__info__email">{user.email}</div>
                                </div>
                            </div>
                        </div>
                        <div class="dropdown-item-navigate">
                            <button class="dropdown-item" type="button" on:click={() => navigate('/user/profile')}
                                    disabled={activePage === "Profile"}>
                                Профиль
                            </button>
                            <button class="dropdown-item" type="button" on:click={logOut}>Выход</button>
                        </div>
                        <div class="dropdown-item-setup">
                            <button class="dropdown-item" type="button" disabled={activePage === "Reference"}>
                                Справка
                            </button>
                            <button class="dropdown-item" type="button" disabled={activePage === "Settings"}>
                                Настройки
                            </button>
                            <button class="dropdown-item" type="button" disabled={activePage === "Send feedback"}>
                                Оставить отзыв
                            </button>
                        </div>
                    </div>
                </div>
            </nav>
        </div>
    </div>
</header>

<style>

    /* Nav Bar right content */
    .nav__bar__right-content {
        display: inline-table;
        position: relative;
    }


    .avatar__btn {
        margin: 0;
        width: 30px;
        height: 30px;
        border-radius: 50%;
        vertical-align: middle;
    }

    .avatar__btn:hover {
        cursor: pointer;
    }

    /* Dropdown menu */
    .dropdown-menu {
        position: absolute;
        right: 0;
        cursor: auto;
        padding: 3px 0;
        background-color: white;

        min-width: 160px;
        width: 298px;
        box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
        z-index: 1000;

        border-bottom: 1px solid;
        border-right: 1px solid;
        border-left: 1px solid;
    }

    .dropdown-item {
        margin: 0;
        padding: 0 36px 0 16px;
        background-color: white;
        border: none;
        border-radius: 0;
        width: 100%;
        height: 40px;
        text-align: left;
        cursor: pointer;
    }

    .dropdown-item:disabled {
        background: #ddd;
        cursor: default;
    }

    .dropdown-item:hover {
        background: #ddd;
    }

    .dropdown-item-navigate {
        border-bottom: 1px solid;
        padding: 8px 0;
    }

    .dropdown-item-setup {
        padding: 8px 0;
    }

    .dropdown-item-profile {
        border-bottom: 1px solid;
    }

    .dropdown-item-profile-inner {
        padding: 16px;

        display: flex;
    }

    .profile__info_photo {
        margin-right: 16px;
    }

    .profile__avatar {
        width: 40px;
        height: 40px;
        border-radius: 50%;
    }

    .profile__info__username {
        font-size: 16px;
        font-weight: 500;
    }

    .profile__info__email {
        margin-top: 8px;
        font-size: 14px;
        font-weight: 400;
    }

    /* Container */
    .container {
        height: 100%;
        max-width: 99%;
        margin: 0 auto;
    }


    /* Header */
    .header {
        width: 100%;

        display: block;
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        z-index: 1000;
        height: 4rem;

        background-color: black;
    }

    .header__inner {
        height: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .header__inner:after {
        content: "";
        display: block;

        width: 100%;
        height: 5px;

        background-color: #707070;
        position: absolute;
        top: 100%;
        left: 0;
        z-index: 1;
    }

    /* Nav Bar */
    .nav__bar {
        height: 100%;
        width: 100%;
        font-size: 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .nav__bar__right-content:hover {
        cursor: pointer;
    }

    .nav__bar__left-content:hover {
        cursor: pointer;
    }
</style>