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
    import ServiceFactory from "../services/ServiceFactory";
    import {hrefs} from "../resources/config";

    let countryService: CountryService;
    let signUIOService: SignUIOService;
    let notificationService: NotificationService;

    let registration: Registration = new Registration();
    let confirmedPassword: string = '';
    let countries: Country[] = [];

    onMount(() => {
        countryService = ServiceFactory.INSTANCE.countryService;
        signUIOService = ServiceFactory.INSTANCE.signUIOService;
        notificationService = ServiceFactory.INSTANCE.notificationService;

        loadCountries();
    });

    const loadCountries = () => {
        countryService.getCountries()
            .then((it: Country[]) => countries = it)
            .catch((apiResponse: ApiResponse<ApiError>) => {
                onError(apiResponse.error);
                countries = [];
            });
    }

    const signUp = () => {
        signUIOService.signUp(registration)
            .then(() => {
                signUIOService.logIn(new Login().fromRegistration(registration))
                    .then(() => navigate(hrefs.home))
                    .catch((apiResponse: ApiResponse<ApiError>) => onError(apiResponse.error));
            }).catch((apiResponse: ApiResponse<ApiError>) => onError(apiResponse.error))
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
<div class="main-content login-page">
    <form class="box elevation-6" method="post">
        <h1>Sign up</h1>
        <div class="user_details">
            <InputField bind:value={registration.username} label={l10n.username}
                        required
                        --custom-height="45px"
                        --custom-width="285px"
                        --custom-margin="8px 0 4px 0"
                        --custom-border-color="var(--cds-border-inverse)"
                        --custom-background-color="var(--cds-ui-01)"
                        --own-input-error-color="var(--cds-text-error)"
            />
            <InputField bind:value={registration.email} label={l10n.email}
                        required
                        type="email"
                        pattern={EMAIL_REGEX}
                        patternErrorMessage={l10n.emailPatternErrorText}
                        --custom-height="45px"
                        --custom-width="285px"
                        --custom-margin="8px 0 4px 0"
                        --custom-border-color="var(--cds-border-inverse)"
                        --custom-background-color="var(--cds-ui-01)"
                        --own-input-error-color="var(--cds-text-error)"
            />
            <Svelecte options={countries}
                      bind:value={registration.locality}
                      placeholder={l10n.selectCountry}
                      selectOnTab
                      clearable
                      style="width: 285px;
                      margin: 8px 0 4px 0;
                      --sv-dropdown-height: 200px;
                      --sv-border-color: var(--cds-border-inverse);
                      --sv-border: 1px solid var(--sv-border-color);
                      --sv-active-border: 1px solid var(--cds-border-interactive);
                      --sv-bg: var(--cds-ui-01);
                      --sv-min-height: 45px;
                      --sv-icon-color: var(--cds-icon-01);
                      --sv-item-selected-bg: var(--cds-background-selected);
                      --sv-item-color: var(--cds-text-01);
                      --sv-item-active-color: var(--cds-text-01);
                      --sv-item-active-bg: var(--cds-background-active);
                      --sv-item-btn-bg: var(--sv-item-selected-bg);
                      --sv-item-btn-bg-hover: var(--cds-hover-ui);
                      --sv-placeholder-color: var(--cds-text-01);
                      --sv-icon-hover: var(--cds-icon-02);
                      --sv-highlight-bg: var(--cds-highlight);
                      color: var(--cds-text-01);
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
                        --custom-border-color="var(--cds-border-inverse)"
                        --custom-background-color="var(--cds-ui-01)"
                        --own-input-error-color="var(--cds-text-error)"
            />
            <InputField bind:value={confirmedPassword} label={l10n.confirmPass}
                        type="password"
                        newPass
                        forceError={passNotMatch}
                        bind:showPassword
                        --custom-height="45px"
                        --custom-width="285px"
                        --custom-margin="8px 0 4px 0"
                        --custom-border-color="var(--cds-border-inverse)"
                        --custom-background-color="var(--cds-ui-01)"
                        --own-input-error-color="var(--cds-text-error)"
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
        z-index: 1 !important;
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
        height: calc(100% - var(--own-nav-bar-height));

        background: var(--own-login-page-background);
    }

    /* Box */
    .box {
        width: 350px;
        padding: 20px;
        position: center;
        background: var(--cds-ui-01);
        border-radius: 16px;
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
        margin: 4px auto .5rem;

        text-align: center;
        display: flex;
        justify-content: space-between;
    }

    /* Sign In Link */
    .sign_in_link {
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