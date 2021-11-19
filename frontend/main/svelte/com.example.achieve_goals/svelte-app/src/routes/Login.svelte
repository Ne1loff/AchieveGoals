<script lang="ts">
    import {navigate} from "svelte-routing"
    import { onDestroy } from 'svelte';
    import PasswordField from "./components/PasswordField.svelte";
    import InputField from "./components/InputField.svelte";


    let login = ''
    let password = ''

    $: {
        console.log(password)
    }

    const handleKeydown = (e) => {
        if (e.key !== 'Enter') return;
        sigIn();
    }

    async function sigIn() {
        fetch('http://localhost:8080/api/login', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                login: login,
                password: password,
            })
        }).then(response => {
            if (response.status === 200)
                navigate('/home')
            else
                alert("Login or password incorrect")
        }).catch((err) => {

            console.log(err)
        })
    }

</script>

<svelte:window on:keydown={handleKeydown}/>

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
    <div class="intro__inner">
        <form class="box" method="post">
            <h1>Login</h1>
            <div class="user_details">
                <div class="input_box">
                    <InputField bind:bindText={login} placeholderText="Username/Email"/>
                </div>
                <div class="input_box">
                    <PasswordField bind:password={password} placeholderText="Password"
                                   newPass="{false}"/>
                </div>
            </div>
            <button class="submit_btn" type="button" on:click={sigIn}>Login</button>
            <div class="signup_link">
                Not a member? <a on:click={() => navigate('/registration')}>Sign up</a>
            </div>
        </form>
    </div>
</div>


<style>


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


    .logo:hover {
        cursor: pointer;
    }

    /* Intro */
    .main-content {
        display: flex;
        justify-content: center;
        flex-direction: column;
        align-items: center;

        background: linear-gradient(135deg, #71b7e6, #9b59b6);

        width: 100%;
        height: 100vh;
        bottom: 0;
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

    .box h1 {
        text-transform: uppercase;
        margin: 10px auto;
        font-weight: 500;
    }


    /* User Details*/
    .user_details {
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .input_box {
        padding: 6px 0;
        max-width: 90%;
    }

    /* Submit Button */
    .submit_btn {
        margin: 5px auto;
        width: 130px;
        border-radius: 12px;
        border-color: #A9A9A9;
        color: black;
        background: none;
    }

    .submit_btn:hover {
        background: darkgray;
        transition: 0.3s;
    }


    /* Sign Up Link */
    .signup_link {
        padding: 5px;
        margin: 5px auto;
        text-align: center;
        color: black;
    }

    /* Media */
    @media (max-width: 584px) {
        .box {
            max-width: 100%;
            padding: 5px;
            margin: 0;
        }

        .text_field {
            max-width: 100%;
        }

        .submit_btn {
            max-width: 100%;
        }
    }

    @media (max-height: 370px) {
        .box {
            max-height: 100%;
            padding: 5px;
            margin: 0;
        }

        .user_details {
            max-height: 15vh;
            overflow-y: scroll;
        }

        .text_field {
            max-height: 100%;
        }

        .submit_btn {
            max-height: 100%;
        }
    }

</style>