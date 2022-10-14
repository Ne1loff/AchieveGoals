<script lang="ts">
    import {link, navigate} from "svelte-routing"
    import {Login as LoginIcon} from "carbon-icons-svelte";
    import {onMount} from "svelte";


    import InputField from "../components/inputs/InputField.svelte";
    import Navbar from "../components/Navbar.svelte";
    import {l10n} from "../resources/localization/l10n";
    import SignUIOService from "../services/SignUIOService";
    import ToastService, {ErrorMessage} from "../services/ToastService";
    import Login from "../data/models/Login";
    import ApiResponse from "../data/api/ApiResponse";
    import ApiError from "../data/api/ApiError";
    import ServiceFactory from "../services/ServiceFactory";
    import UserService from "../services/UserService";
    import CheckboxLine from "../components/checkbox/CheckboxLine.svelte";
    import {hrefs} from "../resources/config";
    import Button from "../components/button/Button.svelte";

    const handleKeydown = (e) => {
        if (e.key !== 'Enter') return;
        sigIn();
    }

    let userService: UserService;
    let signUIOService: SignUIOService;
    let notificationService: ToastService;

    let login: Login = new Login();

    let wasSigIn: boolean;
    let error: boolean;

    const onError = () => {
        wasSigIn = false;
        login.password = '';
        error = true;
    }

    $: if (error) error = login.password.length === 0;

    function sigIn() {
        if (!wasSigIn && login.login.length > 0 && login.password.length > 0) {
            wasSigIn = true;
            signUIOService.logIn(login)
                .then(() => navigate(hrefs.home))
                .catch((apiResponse: ApiResponse<ApiError>) => {
                        notificationService.errorFromErrorMessage(new ErrorMessage().fromApiError(apiResponse.error!!));
                        onError();
                    }
                );
        }
    }

    onMount(() => {
        userService = ServiceFactory.INSTANCE.userService;
        signUIOService = ServiceFactory.INSTANCE.signUIOService;
        notificationService = ServiceFactory.INSTANCE.toastService;
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
                                autocomplete="username"
                                label={l10n.login}
                                forceError={error}
                                --custom-height="45px"
                                --custom-width="285px"
                                --custom-border-color="var(--cds-border-inverse)"
                                --custom-background-color="var(--cds-ui-background)"
                    />
                </div>
                <div class="input_box">
                    <InputField bind:value={login.password} label={l10n.password}
                                type="password"
                                newPass={false}
                                forceError={error}
                                --custom-height="45px"
                                --custom-width="285px"
                                --custom-border-color="var(--cds-border-inverse)"
                                --custom-background-color="var(--cds-ui-background)"
                    />
                </div>
                <CheckboxLine position={'left'} bind:checked={login.rememberMe} labelText={l10n.rememberMe}
                              activeContainer
                              --line-padding="6px 15px 6px 15px"
                />
            </div>
            <Button size="small" on:click={sigIn}
                    --ag-bnt-padding=".125rem 1.125rem"
            >
                <div class="btn-title">
                    {l10n.logInAction}
                </div>
                <LoginIcon/>
            </Button>
            <div class="signup_link">
                {l10n.noAccount}?
                <a class="bx--link" use:link href={hrefs.registration}>{l10n.registration}</a>
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
        background: var(--cds-ui-background);
        border-radius: 16px;
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

    .btn-title {
        margin-right: .5rem;
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