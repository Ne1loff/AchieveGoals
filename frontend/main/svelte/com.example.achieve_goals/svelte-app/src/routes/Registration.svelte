<script lang="ts">
    import {navigate} from "svelte-routing";
    import PasswordField from "./components/PasswordField.svelte";
    import InputField from "./components/InputField.svelte";


    let username = ''
    let email = ''
    let male = true
    let password = ''
    let confirm_pass = ''

    let countries = [{id: 1, name: ""}];

    fetch('http://localhost:8080/api/countries/')
        .then(response => response.json())
        .then(commit => {
            countries = commit
            countries.splice(0, 0, {id: -1, name: "Выберите страну..."})
            selected = countries[0]
        })

    function registration() {
        fetch('http://localhost:8080/api/registration', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "username": username,
                "male": male,
                "email": email,
                "locality": selected.id,
                "password": password
            })
        }).then(response => {
            if (response.status === 201 || response.status === 200) {
                fetch('http://localhost:8080/api/login', {
                    method: 'POST',
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        login: username,
                        password: password,
                    })
                }).then(response => {
                    if (response.status === 200)
                        navigate('/home')
                    else
                        alert("Login or password incorrect")
                }).catch((err) => {
                    alert(err)
                })
            } else {
                alert("Произошла ошибка при регистрации")
            }
        }).catch((err) => {
            alert(err)
        })
    }

    let selected = countries[0];

    let clickable;

    $:clickable = (password === confirm_pass && password && confirm_pass && selected.id !== -1 && password.length >= 6);

    addEventListener('keydown', e => {
        if (window.location.pathname !== "/registration") return;
        if (e.key !== 'Enter') return;
        if (clickable) registration();
    })

</script>

<header class="header">
    <div class="container">
        <div class="header__inner">
            <div class="header__logo">
                <img class="logo" on:click={() => navigate('/')} src="/static/logo_200x44.png" alt="logo">
            </div>
        </div>
    </div>
</header>
<div class="main-content">
    <form class="box" method="post">
        <div class="title">Registration</div>
        <div class="user_details">
            <InputField bind:bindText={username} placeholderText="Username"
                        --custom-height="45px"
                        --custom-width="285px"
                        --custom-margin="8px 0 4px 0"
                        --custom-border-color="#A9A9A9"
            />
            <InputField bind:bindText={email} placeholderText="Email"
                        --custom-height="45px"
                        --custom-width="285px"
                        --custom-margin="8px 0 4px 0"
                        --custom-border-color="#A9A9A9"
            />
            <select class="country_selector" bind:value={selected}>
                {#each countries as country}
                    <option value={country}>
                        {country.name}
                    </option>
                {/each}
            </select>
            <PasswordField bind:password={password} placeholderText="Пароль"
                           newPass={true}
                           --custom-height="45px"
                           --custom-width="285px"
                           --custom-margin="8px 0 4px 0"
                           --custom-border-color="#A9A9A9"
            />
            <PasswordField bind:password={confirm_pass} placeholderText="Подтвердите пароль"
                           newPass="{true}"
                           --custom-height="45px"
                           --custom-width="285px"
                           --custom-margin="8px 0 4px 0"
                           --custom-border-color="#A9A9A9"
            />
            {#if password.length > 0 && password.length < 6 }
                <legend class="pass_match">Минимальная длина пароля 6 символов!</legend>
            {/if}
            {#if password !== confirm_pass && confirm_pass}
                <legend class="pass_match">Пароли не совпадают!</legend>
            {/if}
        </div>
        <div class="check__for__male">
            <lable>
                <input type="radio" bind:group={male} value={true}>
                Мужчина
            </lable>
            <lable>
                <input type="radio" bind:group={male} value={false}>
                Женщина
            </lable>
        </div>
        <button class="submit_btn" type="button" on:click={registration} disabled={!clickable}>Регистрация</button>
        <div class="sign_in_link">
            Уже есть аккаунт? <a on:click={() => navigate('/login')}>Войти</a>
        </div>
    </form>
</div>

<style>

    /*:root {*/
    /*    --custom-height: 45px;*/
    /*    --custom-width: 285px;*/
    /*    --custom-margin: 8px 0 4px 0;*/
    /*    --custom-border-color: #A9A9A9;*/
    /*}*/

    /* Container */
    .container {
        height: 100%;
        max-width: 99%;
        margin: 0 auto;
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

        background-color: white;
    }

    .header__inner {
        height: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .header::after {
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

    .header__inner {
        height: 100%;
    }

    .logo:hover {
        cursor: pointer;
    }

    /* Intro */
    .main-content {
        display: flex;
        justify-content: center;
        flex-direction: column;
        align-items: center;

        width: 100%;
        height: 100vh;
        bottom: 0;

        background: linear-gradient(135deg, #71b7e6, #9b59b6);
    }

    /* Box */
    .box {
        width: 350px;
        padding: 20px;
        position: center;
        background: white;
        border-radius: 10px;
        text-align: center;
    }

    /* Title */
    .title {
        text-transform: uppercase;
        position: relative;
        font-size: 25px;
        font-weight: 500;
    }


    /* User Details */
    .user_details {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    /* Country */
    .country_selector {
        background: white;
        border-color: #A9A9A9;
        border-radius: 6px;
        width: 312px;
        height: 48px;
        display: block;
        margin: 8px 0 4px 0;
        outline: none;
        padding: 12px 14px;
        color: black;
    }

    .pass_match {
        margin: 0;
        padding: 5px;
        display: compact;
        color: red;
    }

    /* Check for male */
    .check__for__male {
        text-align: center;
        display: flex;
        justify-content: space-between;
        padding: 5px 20%;

        font-size: 18px;
    }

    /* Sign In Link */
    .sign_in_link {
        padding: 5px;
        margin: 5px auto;
        text-align: center;
        color: black;
    }

    /* Submit Button */
    .submit_btn {
        margin: 5px auto;
        width: 130px;
        border-radius: 12px;
        border-color: #A9A9A9;
        background: #1877f2;
        color: #fff;

    }

    .submit_btn:hover {
        background: darkgray;
        transition: 0.3s;
    }

    .submit_btn:disabled {
        color: #a0a0a0;
        background: none;
    }

    /* Media */
    @media (max-width: 584px) {
        .box {
            max-width: 100%;
            padding: 5px;
            margin: 0;
        }

        .header__inner {
            justify-content: center;
        }

        .country_selector {
            max-width: 100%;
        }

        .submit_btn {
            max-width: 100%;
        }
    }

    @media (max-height: 470px) {
        .box {
            max-height: 100%;
            padding: 5px;
            margin: 0;
        }

        .user_details {
            max-height: 35vh;
            overflow-y: scroll;
        }

        .country_selector {
            max-height: 100%;
        }

        .submit_btn {
            max-height: 100%;
        }
    }
</style>