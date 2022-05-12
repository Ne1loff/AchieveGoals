<script lang="ts">
    import {Link, navigate} from "svelte-routing";
    import {Button, ContentSwitcher, Switch} from "carbon-components-svelte";
    import Icon from "@iconify/svelte";
    import {Login as LoginIcon} from "carbon-icons-svelte";

    //@ts-ignore
    import Svelecte from 'svelecte';
    import type Country from "../data/models/Country";
    import CountryService from "../services/CountryService";
    import SignUIOService from "../services/SignUIOService";
    import Registration from "../data/models/Registration";
    import {onMount} from "svelte";
    import ApiResponse from "../data/api/ApiResponse";
    import ApiError from "../data/api/ApiError";
    import NotificationService, {ErrorMessage} from "../services/NotificationService";
    import Login from "../data/models/Login";
    import Navbar from "../components/Navbar.svelte";
    import InputField from "../components/inputs/InputField.svelte";
    import {EMAIL_REGEX, PASSWORD_REGEX} from "../resources/regexes";
    import {l10n} from "../resources/localization/l10n";

    let countryService: CountryService;
    let signUIOService: SignUIOService;
    let notificationService: NotificationService;

    let registration: Registration = new Registration();
    let confirmedPassword: string = '';
    let countries: Country[] = [];

    onMount(() => {
        countryService = CountryService.getInstance();
        signUIOService = SignUIOService.getInstance();
        notificationService = NotificationService.getInstance();

        loadCountries();
    });

    const loadCountries = () => {
        countryService.getCountries()
            .then((it: Country[]) => countries = it)
            .catch((apiResponse: ApiResponse<ApiError>) => {
                onError(apiResponse.data);
                countries = [];
            });
    }

    const signUp = () => {
        signUIOService.signUp(registration)
            .then(() => {
                signUIOService.logIn(new Login().fromRegistration(registration))
                    .then(() => navigate('/home'))
                    .catch((apiResponse: ApiResponse<ApiError>) => onError(apiResponse.data));
            }).catch((apiResponse: ApiResponse<ApiError>) => onError(apiResponse.data))
    }

    const onError = (apiError: ApiError) => {
        notificationService.errorFromErrorMessage(new ErrorMessage().fromApiError(apiError));
    }

    let clickable: boolean;
    let passNotMatch: boolean;
    let showPassword: boolean;
    let selectedIndex: number = 0;

    $:registration.male = selectedIndex === 0
    $:passNotMatch = (confirmedPassword !== undefined && registration.password !== confirmedPassword);
    $:clickable = (registration.password === confirmedPassword
        && registration.password
        && confirmedPassword
        && registration.locality
        && registration.password.length >= 6);

    const handleKeydown = (e) => {
        if (e.key === 'Enter' && clickable) signUp();
    };

</script>

<svelte:window on:keydown={handleKeydown}/>
<Navbar outlinedBottom --own-nav-bar-left-margin-left="4px">
    <img slot="left" class="logo" on:click={() => navigate('/')} src="/static/logo_200x44.png" alt="logo">
</Navbar>
<div class="main-content">
    <form class="box" method="post">
        <h1>Sign up</h1>
        <div class="user_details">
            <InputField bind:value={registration.username} label={l10n.username}
                        required
                        --custom-height="45px"
                        --custom-width="285px"
                        --custom-margin="8px 0 4px 0"
                        --custom-border-color="#A9A9A9"
            />
            <InputField bind:value={registration.email} label={l10n.email}
                        required
                        type="email"
                        pattern={EMAIL_REGEX}
                        patternErrorMessage={l10n.emailPatternErrorText}
                        --custom-height="45px"
                        --custom-width="285px"
                        --custom-margin="8px 0 4px 0"
                        --custom-border-color="#A9A9A9"
            />
            <Svelecte options={countries}
                      bind:value={registration.locality}
                      placeholder={l10n.selectCountry}
                      selectOnTab
                      clearable
                      style="width: 285px;
                      margin: 8px 0 4px 0;
                      --sv-dropdown-height: 200px;
                      --sv-border-color: #A9A9A9;
                      --sv-border: 1px solid var(--sv-border-color);
                      --sv-min-height: 45px;
                      --sv-placeholder-color: var(--cds-text-01, #161616);
                      color: var(--cds-text-01, #161616);
                      font-size: 17px;"
            >
                <div slot="icon" style="margin-left: 8px; height: 100%; width: 0"></div>
            </Svelecte>
            <InputField bind:value={registration.password} label={l10n.password}
                        type="password"
                        newPass
                        required
                        pattern={PASSWORD_REGEX}
                        patternErrorMessage={l10n.passwordPatternErrorText}
                        forceError={passNotMatch}
                        bind:showPassword
                        --custom-height="45px"
                        --custom-width="285px"
                        --custom-margin="8px 0 4px 0"
                        --custom-border-color="#A9A9A9"
            />
            <InputField bind:value={confirmedPassword} label={l10n.confirmPass}
                        type="password"
                        newPass
                        forceError={passNotMatch}
                        bind:showPassword
                        --custom-height="45px"
                        --custom-width="285px"
                        --custom-margin="8px 0 4px 0"
                        --custom-border-color="#A9A9A9"
            />
            {#if passNotMatch}
                <legend class="pass_match">{l10n.passwordsNotMatch}!</legend>
            {/if}
        </div>
        <div class="check-for-male">
            <ContentSwitcher bind:selectedIndex>
                <Switch>
                    <div style="display: flex; align-items: center;">
                        <Icon icon="ic:round-man" width="24" height="24"/>
                        {l10n.gender(true)}
                    </div>
                </Switch>
                <Switch>
                    <div style="display: flex; align-items: center;">
                        <Icon icon="ic:round-woman" width="24" height="24"/>
                        {l10n.gender(false)}
                    </div>
                </Switch>
            </ContentSwitcher>
        </div>
        <Button size="small" icon={LoginIcon} on:click={signUp} disabled={!clickable}>{l10n.logUpAction}</Button>
        <div class="sign_in_link">
            {l10n.alreadyHaveAccount}?
            <Link to="/login">{l10n.login}</Link>
        </div>
    </form>
</div>

<style>

    * {
        box-sizing: content-box;
    }

    :global(.sv-dd-item) {
        height: 2rem;
    }

    :global(.sv-item) {
        height: 100%;
        align-items: center;
        text-align: start;
        font-size: 17px;
    }

    :global(.bx--btn) {
        border-radius: 6px;
        text-align: center;
    }

    :global(.bx--content-switcher-btn.bx--content-switcher--selected) {
        z-index: 1;
    }

    :global(.bx--content-switcher-btn) {
        justify-content: center;
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

    .box h1 {
        text-transform: uppercase;
        margin: 10px auto;
        font-weight: 500;
    }


    /* User Details */
    .user_details {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    .pass_match {
        margin: 0;
        padding: 5px;
        display: compact;
        color: red;
    }

    /* Check for male */
    .check-for-male {
        max-width: calc(285px - 10%);
        margin: 4px auto;

        text-align: center;
        display: flex;
        justify-content: space-between;
    }

    /* Sign In Link */
    .sign_in_link {
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
    }
</style>