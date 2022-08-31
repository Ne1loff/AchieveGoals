<script lang="ts">
    import {navigate} from "svelte-routing";
    import {Button} from "carbon-components-svelte";
    import Navbar from "../components/Navbar.svelte";
    import {onMount} from "svelte";
    import {hrefs} from "../resources/config";

    onMount(() => {
        navigate(hrefs.home);
    });
</script>


<Navbar
        --own-nav-bar-left-margin-left="4px"
        --own-nav-bar-right-margin-right="4px"
        --own-nav-bar-width="calc(100vw - 8px)"
>
    <div slot="left" class="header__logo">
        <img class="logo" on:click={() => navigate('/')} src="/static/logo_200x44.png" alt="logo">
    </div>
    <svelte:fragment slot="right">
        <Button on:click={() => navigate(hrefs.login)}>Вход</Button>
        <Button on:click={() => navigate(hrefs.registration)}>Регистрация</Button>
    </svelte:fragment>
</Navbar>
<div class="main-content">
    <div class="container">
        <div class="intro__inner">
            <h1 class="intro__title">Достигай своих целей вместе с нами!</h1>
        </div>
    </div>
</div>


<style>


    h1 {
        margin: 0;
    }

    /* Container */
    .container {
        height: 100%;
        width: 100%;
        margin: 0 auto;
    }


    /* Intro */
    .main-content {
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        position: absolute;

        width: 100%;
        height: calc(100vh - 4rem - 5px);
        bottom: 0;

        background: linear-gradient(135deg, #71b7e6, #9b59b6);
        -webkit-background-size: cover;
    }

    .intro__inner {
        width: 100%;
        height: 100%;
        max-width: 880px;
        margin: 0 auto;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .intro__title {
        font-size: 150px;
        font-weight: 700;
        text-align: center;
        line-height: 1;
    }

    .logo:hover {
        cursor: pointer;
    }
</style>