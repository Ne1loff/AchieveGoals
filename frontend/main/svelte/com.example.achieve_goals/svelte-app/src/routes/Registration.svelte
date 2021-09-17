<script>
    import {navigate} from "svelte-routing";

    let username = ''
    let email = ''
    let password = ''
    let confirm_pass = ''

    let countries = [
        {id: 0, name: 'Select country...'},
        {id: 1, name: 'Россия'},
        {id: 2, name: 'Украина'},
        {id: 3, name: 'Абхазия'},
        {id: 4, name: 'Австралия'},
        {id: 5, name: 'Австрия'},
    ]

    function registration() {
        fetch('/api/registration', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "username": username,
                "email": email,
                "locality": selected.id,
                "password": password
            })
        }).then((response) => {
            if (response.status === 201) {
                alert("Registration success")
                console.log(response.status)
                navigate('/home')
            } else {
                alert(response.status)
                console.log(response.status)
            }
        })
    }

    let selected

    let clickable

    $: clickable = (password === confirm_pass && password && confirm_pass && selected.id !== 0)

</script>
<div class="intro">
    <form class="box" method="post">
        <div class="title">Registration</div>
        <div class="user_details">
            <input class="input_field" type="text" bind:value={username} placeholder="Username">
            <input class="input_field" type="text" bind:value={email} placeholder="Email">
            <select class="country_selector" bind:value={selected}>
                {#each countries as country}
                    <option value={country}>
                        {country.name}
                    </option>
                {/each}
            </select>
            <input class="input_field" type="password" bind:value={password} placeholder="Password">
            <input class="input_field" type="password" bind:value={confirm_pass} placeholder="Confirm password">
            {#if password !== confirm_pass && confirm_pass}
                <legend class="pass_match">Passwords do not match!</legend>
            {/if}
        </div>
        <input class="submit_btn" type="submit" on:click={registration} disabled={!clickable} value="Sign up">
        <div class="sign_in_link">
            Already have an account? <a on:click={() => navigate('/login')}>Sign in</a>
        </div>
    </form>
</div>

<style>

    /* Intro */
    .intro {
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
        justify-content: space-between;
        text-align: center;
    }

    /* Input Field */
    .input_field {
        border-color: #A9A9A9;
        border-radius: 12px;
        margin: 8px auto;
        outline: none;
        padding: 10px 14px;
        color: black;
    }

    /* Country */
    .country_selector {
        background: white;
        border-color: #A9A9A9;
        border-radius: 12px;
        width: 282px;
        height: 41px;
        display: block;
        margin: 8px auto;
        outline: none;
        padding: 10px 14px;
        color: black;
    }

    .pass_match {
        margin: 0;
        padding: 5px;
        display: compact;
        color: red;
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
        background: none;

    }

    .submit_btn:hover {
        background: darkgray;
        transition: 0.3s;
    }


    /* Media */
    @media (max-width: 584px) {
        .box {
            max-width: 100%;
            padding: 5px;
            margin: 0;
        }

        .input_field {
            max-width: 100%;
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

        .input_field {
            max-height: 100%;
        }

        .country_selector {
            max-height: 100%;
        }

        .submit_btn {
            max-height: 100%;
        }
    }
</style>