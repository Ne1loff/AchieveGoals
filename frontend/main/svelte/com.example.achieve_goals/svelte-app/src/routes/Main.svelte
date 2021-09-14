<script>

import {navigate} from "svelte-routing";
</script>

<header class="header">
    <div class="container">
        <div class="header__inner">
            <div class="header__logo"><img class="logo" src="/static/logo_200x44.png" alt="logo"></div>
            <nav class="nav">
                <li>
                    <a class="nav__link" on:click={() => navigate('/login')}>Login</a>
                </li>
                <li>
                    <a class="nav__link" on:click={() => navigate('/registration')}>Registration</a>
                </li>
            </nav>
        </div>
    </div>
</header>

<div class="intro">
    <div class="container">
        <div class="intro__inner">
            <h1 class="intro__title">Achieve your goals with us!</h1>
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
        max-width: 1200px;
        margin: 0 auto;
    }


    /* Intro */
    .intro {
        display: block;
        flex-direction: column;

        width: 100%;
        height: 100vh;
        bottom: 0;

        background: url("/static/intro.jpg") center no-repeat;
        -webkit-background-size: cover;
    }

    .intro__inner {
        width: 100%;
        max-width: 880px;
        margin: 0 auto;
        transform: translateY(50%);
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

    .header__logo {
        width: 150px;
        height: 30px;
        transform: translateY(-25%);
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

    .nav li:after {
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

    .nav li:hover {
        background-color: #e3e3e3;
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