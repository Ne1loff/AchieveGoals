<script lang="ts">

    import SettingsAbstractTab from "../SettingsAbstractTab.svelte";
    import {USER} from "../../../../data/storage/storage";

    import InputField from "../../../inputs/InputField.svelte";
    import SettingsInfoBlock from "./../SettingsInfoBlock.svelte";
    import User from "../../../../data/models/User";
    import {onMount} from "svelte";
    import serviceFactory from "../../../../services/ServiceFactory";
    import type {onSettingsChange} from "../../utils";
    import {isEmptyString, isEqualsStrings} from "../../utils";
    import {isTypeOf} from "../../../../utils/objects";
    import {navigate} from "svelte-routing";
    import {EMAIL_REGEX} from "../../../../resources/regexes";
    import {l10n} from "../../../../resources/localization/l10n";

    export let wasChange: boolean;
    export let onChange: onSettingsChange;
    export let currentTab: { obj: string, title: string, icon: string, href: string };

    let newEmail: string;
    let emailPatternError: boolean;
    let confirmedEmail: string;
    let password: string;

    const save = () => $USER = userCopy ?? $USER;
    const cancel = () => navigate(currentTab.href);

    let userCopy: User | undefined;

    $: if (onChange) onChange.disableAcceptBtn = isEmptyString(newEmail)
        || isEmptyString(confirmedEmail)
        || isEmptyString(password)
        || !isEqualsStrings(newEmail, confirmedEmail)
        || emailPatternError;

    onMount(async () => {

        wasChange = true;

        if ($USER) {
            userCopy = $USER.clone();
        } else {
            await serviceFactory.INSTANCE.userService.getCurrentUser();
            if (isTypeOf<User>($USER)) userCopy = $USER.clone();
        }

        onChange.accept = save;
        onChange.reject = cancel;
    });

</script>

<SettingsAbstractTab --ag-bnt-border-radius=".5rem">
    <SettingsInfoBlock>
        <div class="account-info">
            <h4>Новый Email</h4>
            <div class="username spacing-small">
                {#if userCopy}
                    <InputField bind:value={newEmail}
                                on:error={({detail}) => {
                                    emailPatternError = detail;
                                }}
                                type="email"
                                name="new-email"
                                pattern={EMAIL_REGEX}
                                patternErrorMessage={l10n.emailPatternErrorText}
                                disableAutocomplete
                    />
                {:else}
                    <InputField skeleton={true}/>
                {/if}
            </div>
        </div>
        <div class="account-info">
            <h4>Подтвердите Email</h4>
            <div class="username spacing-small">
                {#if userCopy}
                    <InputField bind:value={confirmedEmail}
                                name="confirm-email"
                                type="email"
                                disableAutocomplete
                    />
                {:else}
                    <InputField skeleton={true}
                    />
                {/if}
            </div>
        </div>
        <div class="account-info">
            <h4>Текущий пароль</h4>
            <div class="username spacing-small">
                {#if userCopy}
                    <InputField bind:value={password}
                                name="current-pass"
                                type="password"
                                newPass={false}
                                disableAutocomplete
                    />
                {:else}
                    <InputField skeleton={true}
                    />
                {/if}
            </div>
        </div>
    </SettingsInfoBlock>
</SettingsAbstractTab>

<style>

    .account-info {
        display: flex;
        flex-direction: column;
        --custom-height: 2.5rem;
        --custom-width: 400px;
        --custom-border-color: var(--cds-border-subtle);
    }

    .account-info:not(:first-child) {
        margin-top: .75rem;
    }

    hr {
        border: none;
        border-bottom: 1px solid var(--cds-border-subtle);
        margin: 0;
    }

    hr:first-child {
        display: none;
    }

</style>