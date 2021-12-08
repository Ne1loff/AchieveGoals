<script>
    import {navigate} from "svelte-routing";
</script>

<header class="header">
    <div class="container">
        <div class="header__inner">
            <div class="header__logo">
                <img class="logo" on:click={() => navigate('/')} src="/static/logo_200x44.png" alt="logo">
            </div>
            <nav class="nav">
                <li>
                    <a class="nav__link" on:click={() => navigate('/login')}>Вход</a>
                </li>
                <li>
                    <a class="nav__link" on:click={() => navigate('/registration')}>Регистрация</a>
                </li>
            </nav>
        </div>
    </div>
</header>

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
        max-width: 99%;
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

        background: url("/static/intro.jpg") center no-repeat;
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


    /* Header */
    .header {
        width: 100%;

        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        z-index: 1000;
        height: 4rem;

        background-color: #fff;
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


    .logo:hover {
        cursor: pointer;
    }

    /* Nav */
    .nav {
        height: 100%;
        font-size: 20px;
        display: flex;
    }

    .nav li {
        height: 100%;
        width: 100%;

        text-decoration: none;
        display: flex;
    }

    .nav li:hover {
        background-color: #e3e3e3;
        cursor: pointer;
    }

    .nav__link {
        height: 100%;
        color: #000;
        padding: 0 20px;
        display: flex;
        align-items: center;
        text-decoration: none;
    }

</style>