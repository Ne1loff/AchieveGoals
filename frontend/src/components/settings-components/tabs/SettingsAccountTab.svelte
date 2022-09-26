<script lang="ts">

    import SettingsAbstractTab from "./SettingsAbstractTab.svelte";
    import {USER} from "../../../data/storage/storage";
    import Avatar from "../../Avatar.svelte";

    import InputField from "../../inputs/InputField.svelte";
    import Button from "../../button/Button.svelte";
    import SettingsInfoBlock from "./SettingsInfoBlock.svelte";
    import User from "../../../data/models/User";
    import {onMount} from "svelte";
    import serviceFactory from "../../../services/ServiceFactory";
    import type {onSettingsChange} from "../utils";
    import Text from "../../text/Text.svelte";
    import {isTypeOf} from "../../../utils/objects";

    export let wasChange: boolean;
    export let onChange: onSettingsChange;


    const save = () => $USER = userCopy;
    const cancel = () => userCopy = $USER?.clone();


    let userCopy: User | undefined;

    onMount(async () => {

        if ($USER) {
            userCopy = $USER.clone();
        } else {
            await serviceFactory.INSTANCE.userService.getCurrentUser();
            if (isTypeOf<User>($USER)) userCopy = $USER.clone();
        }

        onChange.accept = save;
        onChange.reject = cancel;
    });

    $:wasChange = userCopy ? $USER?.username !== userCopy?.username : false;
</script>

<SettingsAbstractTab --ag-bnt-border-radius=".5rem">
    <SettingsInfoBlock>
        <div class="account-info">
            <h4>Фото</h4>
            <div class="avatar">
                <div class="picture">
                    <Avatar size="big"
                            src={userCopy?.avatar} alt="Profile picture"/>
                </div>
                <div class="picture-actions">
                    <div class="void">
                        &nbsp;
                    </div>
                    <div class="buttons spacing-small">
                        <Button size="small" kind="secondary">Поменять фото</Button>
                        <Button size="small" kind="danger-tertiary">Удалить фото</Button>
                    </div>
                    <div class="photo-size-info spacing-small">
                        Выберите фото размером до 4МБ.
                    </div>
                </div>
            </div>
        </div>
        <div class="account-info">
            <h4>Название</h4>
            <div class="username spacing-small">
                {#if userCopy}
                    <InputField bind:value={userCopy.username}
                                --custom-height="2rem"
                                --custom-width="400px"
                                --custom-border-color="var(--cds-border-subtle)"
                    />
                {:else}
                    <InputField skeleton={true}
                                --custom-height="2rem"
                                --custom-width="400px"
                                --custom-border-color="var(--cds-border-subtle)"
                    />
                {/if}
            </div>
        </div>
        <div class="account-info">
            <h4>Email</h4>
            <div class="email-info spacing-small">
                <Text skeleton={userCopy === undefined}
                      --custom-width="400px"
                >
                    {userCopy?.email}
                </Text>
            </div>
            <div class="action" class:spacing-small={true}>
                <Button size="small" kind="secondary">Изменить Email</Button>
            </div>
        </div>
        <div class="account-info">
            <h4>Пароль</h4>
            <div class="spacing-small">
                <Button size="small" kind="secondary">Изменить пароль</Button>
            </div>
        </div>
    </SettingsInfoBlock>
    <SettingsInfoBlock>
        <div class="delete-account">
            <h3>Удалить аккаунт</h3>
            <div class="delete-account-info spacing-small">
                Все ваши данные, включая задачи, проекты, комментарии и не только будут сразу удалены без возможности
                восстановления.
            </div>
            <div class="action spacing-small">
                <Button size="small" kind="danger-tertiary">Удалить аккаунт</Button>
            </div>
        </div>
    </SettingsInfoBlock>
</SettingsAbstractTab>

<style>

    .account-info, .delete-account {
        display: flex;
        flex-direction: column;
    }

    .account-info:not(:first-child), .delete-account:not(:first-child) {
        margin-top: .75rem;
    }

    .avatar {
        display: flex;
    }

    .photo-size-info {
        font-size: var(--cds-helper-text-01-font-size);
        letter-spacing: var(--cds-helper-text-01-letter-spacing);
        line-height: var(--cds-helper-text-01-line-height);
        color: var(--cds-text-helper);
    }

    .delete-account-info {
        font-size: var(--cds-helper-text-01-font-size);
        letter-spacing: var(--cds-helper-text-01-letter-spacing);
        line-height: var(--cds-helper-text-01-line-height);
    }

    .picture-actions {
        margin-left: .75rem;
    }

    .buttons {
        margin-top: .5rem;
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