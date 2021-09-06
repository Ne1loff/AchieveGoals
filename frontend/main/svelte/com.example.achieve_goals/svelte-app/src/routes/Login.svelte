<script>
    import Kitchen from '@smui/snackbar/kitchen/index'
    import {navigate} from "svelte-routing"
    let login = ''
    let password = ''
    let kitchen;
    let kitchenReason = 'nothing yet';
    let kitchenAction = 'nothing yet';
    function getJwt() {
        fetch('/api/login', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                login: login,
                password: password,
            })
        }).then((response) => {
            if (response.status === 200) {
                navigate("/")
            } else {
                response.json().then((text) => {
                    pushToKitchen(text)
                })
            }
        })
    }
    function pushToKitchen(text) {
        kitchen.push({
            props: {
                variant: 'stacked'
            },
            label: text,
            dismissButton: false,
        });
    }
    fetch(`/api/user`)
        .then((response) => {
            if (response.status === 200) {
                window.location.replace("/")
            }
        })
</script>

<Kitchen bind:this = {kitchen}/>

<form class="box" method="post">
    <h1>Login</h1>
    <div class="user_details">
        <input class="text_field" type="text" name="" placeholder="Email">
        <input class="text_field" type="password" name="" placeholder="Password">
    </div>
    <input class="submit_btn" type="submit" on:click={getJwt} name="" value="Login">
    <div class="signup_link">
        Not a member? <a href="/registration">Sign up</a>
    </div>
</form>


<style>


    /* Box */
    .box {
        width: 350px;
        padding: 20px;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
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
        justify-content: space-between;
    }

    /* Text Field */
    .text_field {
        border-color: #A9A9A9;
        border-radius: 12px;
        display: block;
        margin: 12px auto;
        outline: none;
        padding: 10px 14px;
        color: black;
        text-align: center;
    }

    .text_field::placeholder {
        color: black;
        opacity: .6;
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
            padding: 0;
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
            padding: 0;
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