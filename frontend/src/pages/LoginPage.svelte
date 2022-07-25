<script lang="ts">
    import {Link, navigate} from "svelte-routing"
    import {Button} from "carbon-components-svelte";
    import {Login as LoginIcon} from "carbon-icons-svelte";
    import {onMount} from "svelte";


    import InputField from "../components/inputs/InputField.svelte";
    import Navbar from "../components/Navbar.svelte";
    import {l10n} from "../resources/localization/l10n";
    import SignUIOService from "../services/SignUIOService";
    import NotificationService, {ErrorMessage} from "../services/NotificationService";
    import Login from "../data/models/Login";
    import ApiResponse from "../data/api/ApiResponse";
    import ApiError from "../data/api/ApiError";
    import ServiceFactory from "../services/ServiceFactory";
    import UserService from "../services/UserService";
    import CheckboxLine from "../components/CheckboxLine.svelte";

    const handleKeydown = (e) => {
        if (e.key !== 'Enter') return;
        sigIn();
    }

    let userService: UserService;
    let signUIOService: SignUIOService;
    let notificationService: NotificationService;

    let login: Login = new Login();

    function sigIn() {
        if (login.login.length > 0 && login.password.length > 0) {
            signUIOService.logIn(login)
                .then(() => navigate('/home/goals'))
                .catch((apiResponse: ApiResponse<ApiError>) =>
                    notificationService.errorFromErrorMessage(new ErrorMessage().fromApiError(apiResponse.error))
                );
        }
    }

    onMount(() => {
        userService = ServiceFactory.INSTANCE.userService;
        signUIOService = ServiceFactory.INSTANCE.signUIOService;
        notificationService = ServiceFactory.INSTANCE.notificationService;
    });

</script>

<svelte:window on:keydown={handleKeydown}/>
<Navbar outlinedBottom --own-nav-bar-left-margin-left="4px">
    <img slot="left" class="logo" on:click={() => navigate('/')} src="/static/logo_200x44.png" alt="logo">
</Navbar>
<div class="main-content login-page">
    <div class="intro__inner">
        <form class="box elevation-6" method="post">
            <h1>Login</h1>
            <div class="user_details">
                <div class="input_box">
                    <InputField bind:value={login.login}
                                placeholderText="Username/Email"
                                label={l10n.login}
                                --custom-height="45px"
                                --custom-width="285px"
                                --custom-border-color="var(--cds-border-inverse)"
                                --custom-background-color="var(--cds-ui-01)"
                    />
                </div>
                <div class="input_box">
                    <InputField bind:value={login.password} label={l10n.password}
                                type="password"
                                newPass={false}
                                --custom-height="45px"
                                --custom-width="285px"
                                --custom-border-color="var(--cds-border-inverse)"
                                --custom-background-color="var(--cds-ui-01)"
                    />
                </div>
                <CheckboxLine position={'left'} bind:checked={login.rememberMe} labelText={l10n.rememberMe}
                              activeContainer
                              --line-padding="6px 15px 6px 15px"
                />
            </div>
            <Button size="small" icon={LoginIcon} on:click={sigIn}>{l10n.logInAction}</Button>
            <div class="signup_link">
                {l10n.noAccount}?
                <Link to="/registration">{l10n.registration}</Link>
            </div>
        </form>
    </div>
</div>


<style>

    /* Intro */
    .main-content {
        display: flex;
        justify-content: center;
        flex-direction: column;
        align-items: center;

        background: var(--own-login-page-background);

        width: 100%;
        height: calc(100% - var(--own-nav-bar-height));
    }


    /* Box */
    .box {
        width: 350px;
        padding: 20px;
        position: center;
        background: var(--cds-ui-01);
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
        margin-bottom: .5rem;
    }

    .input_box {
        padding: 6px 0;
        max-width: 90%;
        display: flex;
        align-items: center;
    }

    /* Sign Up Link */
    .signup_link {
        padding: 5px;
        margin: 5px auto;
        text-align: center;
        color: var(--cds-text-01);
    }

    /* Media */
    @media (max-width: 584px) {
        .box {
            max-width: 100%;
            padding: 5px;
            margin: 0;
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
    }

</style>