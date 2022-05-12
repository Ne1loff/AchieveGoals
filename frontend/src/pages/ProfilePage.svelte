<script lang="ts">
    import {navigate} from "svelte-routing";
    import Icon from "@iconify/svelte"

    let inputTypeBool = [false, false, false];

    let oldPas = '';

    let changedPas = '';
    let confirmedChangedPas = '';

    let showConfirmWindow = false;
    let clickable;
    $:clickable = (changedPas === confirmedChangedPas && changedPas && confirmedChangedPas && oldPas);

    let user = {
        username: '',
        name: '',
        surname: '',
        male: '',
        email: '',
        locality: '',
        avatar: ''
    };

    let countries = [{id: 1, name: ""}];
    let selected = {id: 1, name: ""};

    let userViaChanges = JSON.parse(JSON.stringify(user));

    let fileVar = null;
    let toChange = [false, false, false, false, false, false]

    function getCountries() {
        if (countries.length === 1) {
            fetch('/api/countries/')
                .then(response => response.json())
                .then(commit => {
                    countries = commit
                    selected = countries.find(it => it.name === user.locality);
                })
        }
    }

    function mainSave() {
        showImage = false
        wasSave = false

        if (fileVar !== null) {
            let file = new FormData();
            file.append('avatar', fileVar)

            fetch('/api/user/avatar', {
                method: 'PUT',
                body: file
            }).then((response) => {
                if (response.status === 200) {

                } else {
                    alert(response.status)
                }
            })
        }

        fileVar = null;


        fetch('/api/user', {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(userViaChanges)
        }).then((response) => {
            if (response.status === 200) {
                alert("Профиль успешно изменен")
            } else {
                alert(response.status)
            }
        }).then(() => {
            fetch('/api/user')
                .then(response => {
                    if (response.status === 200)
                        return response.json()
                    else {
                        navigate('/login')
                    }
                })
                .then(commit => {
                    user = commit
                    userViaChanges = JSON.parse(JSON.stringify(user))
                })
        })

    }

    function mainCancel() {
        getUser()
        showImage = false
        wasSave = false
        image.setAttribute("src", user.avatar)
        userViaChanges = JSON.parse(JSON.stringify(user))
        cancel()
    }

    function cancel() {
        toChange[toChange.indexOf(true)] = false
    }

    function resetPas() {
        oldPas = "";
        changedPas = "";
        confirmedChangedPas = "";
        showConfirmWindow = false;
        cancel();
    }

    function save() {
        toChange[toChange.indexOf(true)] = false
        wasSave = true
    }

    function savePas() {

        fetch('/api/user/changePassword', {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "oldPassword": oldPas,
                "newPassword": confirmedChangedPas
            })
        }).then(
            response => {
                showConfirmWindow = false;
                if (response.ok) {
                    return true
                } else {
                    alert("Введен неверный старый пароль")
                    return false
                }
            }
        ).then(
            accept => {
                if (accept) {
                    oldPas = "";
                    changedPas = "";
                    confirmedChangedPas = "";
                    cancel();
                    alert("Пароль успешно изменен!")
                }
            }
        ).catch(
            (err) => console.error("Все плохо: " + err)
        )

    }

    let input;
    let image;
    let wasSave = false;
    let showImage = false;
    let wasChanged;
    getUser()

    $:{
        selected = selected;
        userViaChanges.locality = selected.name;
    }

    function onChange() {
        fileVar = input.files[0];

        if (fileVar) {
            showImage = true;

            const reader = new FileReader();
            reader.addEventListener("load", function () {
                if (typeof reader.result === "string") {
                    image.setAttribute("src", reader.result);
                }
            });
            reader.readAsDataURL(fileVar);

            return;
        }
        showImage = false;
    }

    function getUser() {
        fetch('/api/user')
            .then(response => {
                return response.json()
            })
            .then(commit => {
                user = commit
                userViaChanges = JSON.parse(JSON.stringify(user))
            }).catch((err) => {
            alert(err)
            navigate('/login')
        })
    }


</script>

<div class="main-content-wrapper">
    <div class="content">
        <div class="container">
            <div class="row-content">
                <div class="content-block">
                    <div class="content-block-name">
                        <div class="content-container-text">
                            <span class="content-block-name-text">Профиль</span>
                        </div>
                        <div class="content-right-block">
                            <div class="content-block-user-info">
                                <div class="content-container">
                                    <div class="user-info-top">
                                        <div class="user-info-text">Username</div>
                                        <div class="user-info-field">
                                            <div>
                                                {userViaChanges.username !== user.username ?
                                                    userViaChanges.username : user.username }
                                            </div>
                                        </div>
                                    </div>
                                    <div class="user-info">
                                        <div class="user-info-text">Имя</div>
                                        {#if !toChange[1]}
                                            <div class="user-info-field">
                                                <div>
                                                    {(user.name === null && !wasSave) || userViaChanges.name === '' || userViaChanges.name === null ?
                                                        "Не задано" : wasSave ?
                                                            userViaChanges.name : user.name}
                                                </div>
                                                <a on:click={() => {
                                                        cancel()
                                                        toChange[1] = true
                                                    }}>Изменить</a>
                                            </div>
                                        {:else}
                                            <div class="user-info-field-change">
                                                <div class="input_container">
                                                    <input class="input_field" type="text" placeholder="Имя"
                                                           bind:value={userViaChanges.name}>
                                                </div>
                                                <div class="user-info-field-buttons">
                                                    <button class="save__button" type="button" on:click={save}>
                                                        Сохранить
                                                    </button>
                                                    <button class="cancel__button" type="button" on:click={cancel}>
                                                        Отмена
                                                    </button>
                                                </div>
                                            </div>
                                        {/if}
                                    </div>
                                    <div class="user-info">
                                        <div class="user-info-text">Фамилия</div>
                                        {#if !toChange[2]}
                                            <div class="user-info-field">
                                                <div>
                                                    {(user.surname === null && !wasSave) || userViaChanges.surname === '' || userViaChanges.surname === null ?
                                                        "Не задано" : wasSave ?
                                                            userViaChanges.surname : user.surname}
                                                </div>
                                                <a on:click={() => {
                                                        cancel()
                                                        toChange[2] = true
                                                    }}>Изменить</a>
                                            </div>
                                        {:else}
                                            <div class="user-info-field-change">
                                                <div class="input_container">
                                                    <input class="input_field" type="text" placeholder="Фамилия"
                                                           bind:value={userViaChanges.surname}>
                                                </div>
                                                <div class="user-info-field-buttons">
                                                    <button class="save__button" type="button" on:click={save}>
                                                        Сохранить
                                                    </button>
                                                    <button class="cancel__button" type="button"
                                                            on:click={cancel}>
                                                        Отмена
                                                    </button>
                                                </div>
                                            </div>
                                        {/if}
                                    </div>
                                    <div class="user-info">
                                        <div class="user-info-text">Пароль</div>
                                        {#if !toChange[3]}
                                            <div class="user-info-field">
                                                <div>
                                                    ********
                                                </div>
                                                <a on:click={() => {
                                                        cancel()
                                                        toChange[3] = true
                                                    }}>Изменить</a>
                                            </div>
                                        {:else}
                                            <div class="user-info-field-change">
                                                <form class="user-info-form">
                                                    <div class="input_box">
                                                        <div class="input_container">
                                                            {#if inputTypeBool[0]}
                                                                <input class="input_field" type="text"
                                                                       placeholder="Старый пароль"
                                                                       bind:value={oldPas}
                                                                       autocomplete="on">
                                                            {:else}
                                                                <input class="input_field" type="password"
                                                                       placeholder="Старый пароль"
                                                                       bind:value={oldPas}
                                                                       autocomplete="on">
                                                            {/if}
                                                            <div class="icon_holder"
                                                                 on:click={() => {inputTypeBool[0] = !inputTypeBool[0]}}>
                                                                <Icon class="input_box_icon"
                                                                      icon="akar-icons:eye-{inputTypeBool[0] ? 'open' : 'closed'}"/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="input_box">
                                                        <div class="input_container">
                                                            {#if inputTypeBool[1]}
                                                                <input class="input_field" type="text"
                                                                       placeholder="Новый пароль"
                                                                       bind:value={changedPas}
                                                                       autocomplete="new-password">
                                                            {:else}
                                                                <input class="input_field" type="password"
                                                                       placeholder="Новый пароль"
                                                                       bind:value={changedPas}
                                                                       autocomplete="new-password">
                                                            {/if}
                                                            <div class="icon_holder"
                                                                 on:click={() => {inputTypeBool[1] = !inputTypeBool[1]}}>
                                                                <Icon class="input_box_icon"
                                                                      icon="akar-icons:eye-{inputTypeBool[1] ? 'open' : 'closed'}"/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="input_box">
                                                        <div class="input_container">
                                                            {#if inputTypeBool[2]}
                                                                <input class="input_field" type="text"
                                                                       placeholder="Подтвердите новый пароль"
                                                                       bind:value={confirmedChangedPas}
                                                                       autocomplete="new-password">
                                                            {:else}
                                                                <input class="input_field" type="password"
                                                                       placeholder="Подтвердите новый пароль"
                                                                       bind:value={confirmedChangedPas}
                                                                       autocomplete="new-password">
                                                            {/if}
                                                            <div class="icon_holder"
                                                                 on:click={() => {inputTypeBool[2] = !inputTypeBool[2]}}>
                                                                <Icon class="input_box_icon"
                                                                      icon="akar-icons:eye-{inputTypeBool[2] ? 'open' : 'closed'}"/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </form>
                                                {#if changedPas !== confirmedChangedPas && confirmedChangedPas}
                                                    <legend class="pass_match">Пароли не совпадают!</legend>
                                                {/if}
                                                <div class="user-info-field-buttons">
                                                    <button class="save__button" type="button"
                                                            disabled={!clickable}
                                                            on:click={() => showConfirmWindow = true}>
                                                        Изменить
                                                    </button>
                                                    <button class="cancel__button" type="button"
                                                            on:click={resetPas}>
                                                        Отмена
                                                    </button>
                                                </div>
                                            </div>
                                        {/if}
                                        <div class="user-info-change-confirm_window"
                                             style="display: {showConfirmWindow ? 'block' : 'none'}">
                                            <div class="confirm_window_box">
                                                <h1 class="confirm_window_text">
                                                    Вы уверены что хотите изменить пароль?
                                                </h1>
                                                <div class="confirm_window_buttons">
                                                    <button class="save__button" type="button" on:click={savePas}>
                                                        Да
                                                    </button>
                                                    <button class="cancel__button" type="button"
                                                            on:click={resetPas}>
                                                        Нет
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="user-info">
                                        <div class="user-info-text">Пол</div>
                                        {#if !toChange[4]}
                                            <div class="user-info-field">
                                                <div>
                                                    {user.male === userViaChanges.male ?
                                                        user.male ? "Мужской" : "Женский"
                                                        : userViaChanges.male ? "Мужской" : "Женский"}
                                                </div>
                                                <a on:click={() => {
                                                        cancel()
                                                        toChange[4] = true
                                                    }}>Изменить</a>
                                            </div>
                                        {:else}
                                            <div class="user-info-field-change">
                                                <div class="check__for__male">
                                                    <lable>
                                                        <input type="radio" bind:group={userViaChanges.male}
                                                               value={true}>
                                                        Мужской
                                                    </lable>
                                                    <lable>
                                                        <input type="radio" bind:group={userViaChanges.male}
                                                               value={false}>
                                                        Женский
                                                    </lable>
                                                </div>
                                                <div class="user-info-field-buttons">
                                                    <button class="save__button" type="button" on:click={save}>
                                                        Сохранить
                                                    </button>
                                                    <button class="cancel__button" type="button" on:click={cancel}>
                                                        Отмена
                                                    </button>
                                                </div>
                                            </div>
                                        {/if}
                                    </div>
                                    <div class="user-info">
                                        <div class="user-info-text">Страна</div>
                                        {#if !toChange[5]}
                                            <div class="user-info-field">
                                                <div>
                                                    {user.locality}
                                                </div>
                                                <a on:click={() => {
                                                        cancel()
                                                        getCountries()
                                                        toChange[5] = true
                                                    }}>Изменить</a>
                                            </div>
                                        {:else}
                                            <div class="user-info-field-change">
                                                <select class="country_selector" bind:value={selected}>
                                                    {#each countries as country}
                                                        <option value={country}>
                                                            {country.name}
                                                        </option>
                                                    {/each}
                                                </select>
                                                <div class="user-info-field-buttons">
                                                    <button class="save__button" type="button" on:click={save}>
                                                        Сохранить
                                                    </button>
                                                    <button class="cancel__button" type="button" on:click={cancel}>
                                                        Отмена
                                                    </button>
                                                </div>
                                            </div>
                                        {/if}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="content-block-user-photo">
                        <div class="content-container-text">
                            <span class="user-photo-text">Фото</span>
                        </div>
                        <div class="user-photo-container">
                            <div class="content-container">
                                <img class="user-photo" bind:this={image}
                                     src="{user.avatar}"
                                     alt="photo">
                                <input type="file" accept="image/*" bind:this={input}
                                       on:change={onChange}>
                            </div>
                        </div>
                        {#if wasChanged}
                            <div class="user-info-field-buttons">
                                <button class="save__button" type="button" on:click={mainSave}>Сохранить</button>
                                <button class="cancel__button" type="button" on:click={mainCancel}>Отмена</button>
                            </div>
                        {/if}
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer">
    </div>
</div>


<style>

    body {
        overflow-y: scroll;
    }

    .main-content-wrapper {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        height: 100%;
    }

    .content {
        display: flex;
        flex-direction: column;
    }

    .container {
        margin: 0 auto;
        padding: 0 20px;
        min-width: 70vw;
        width: 80vw;
        max-width: 98vw;
        box-sizing: border-box;
        min-height: calc(100vh - 4rem - 5px - 200px)
    }

    .row-content {
        padding: 50px 0;
        box-sizing: border-box;
        height: 100%;
        display: flex;
        align-items: center;
    }

    .content-block {
        width: 100%;
        display: flex;
        flex-direction: row;
        justify-content: space-evenly;
    }

    .content-right-block {
        display: flex;
        justify-content: normal;
    }

    .content-block-name {
        max-width: 970px;
        width: 100%;
        margin: 8px 8px;
        box-sizing: border-box;
    }

    .content-container-text {
        width: 100%;
    }

    .content-block-name-text {
        font-size: 32px;
        font-weight: 500;
    }

    .content-block-user-info {
        width: calc(100%);
    }

    .content-block-user-photo {
        padding: 0 8px 0 8px;
        box-sizing: border-box;
    }

    .content-block-user-photo input {
        margin: 8px 0 0 0;
        padding: 4px;
        border-radius: 0;
    }

    .icon_holder {
        width: 30px;
        height: 30px;
        cursor: pointer;

        display: flex;
        align-items: center;
        justify-content: center;

        border-radius: 50%;
    }

    .icon_holder:hover {
        background: rgba(0, 0, 0, .05);
    }

    :global(.input_box_icon) {
        width: 20px;
        height: 20px;
    }

    .input_field {
        height: 100%;
        width: 85%;
        max-width: 90%;
        border: none;

        padding: 0;
        margin: 0;
    }

    .input_field:focus {
        outline: none;
    }

    .user-info-top {
        width: 100%;
        padding: 20px 0;
        background: #f1f1f1;
        border-top: 1px solid #e5e5e5;
        border-bottom: 1px solid #e5e5e5;
        display: flex;
        justify-content: space-between;
    }

    .user-info {
        width: 100%;
        padding: 20px 0;
        background: #f1f1f1;
        border-bottom: 1px solid #e5e5e5;
        display: flex;
        justify-content: space-between;
    }

    .user-info-text {
        padding: 0 15px;
        box-sizing: border-box;
    }

    .user-info-field {
        display: flex;
        width: 60%;
        justify-content: space-between;
        padding: 0 15px;
    }

    .user-info-field-change {
        display: flex;
        flex-direction: column;
        width: 60%;
        justify-content: space-between;
        padding: 0 15px;
    }

    .user-info-form {
        display: flex;
        flex-direction: column;
        padding: 0;
        margin: 0;
    }

    .input_box {
        padding: 6px 0;
        user-select: none;
    }

    .input_container {
        background: white;
        height: 22px;
        max-width: 70%;

        padding: 12px 14px;
        font-size: 17px;
        border: 1px solid #dddfe2;
        border-radius: 6px;

        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .country_selector {
        background: white;
        width: 300px;
    }

    .check__for__male {
        width: 150px;
        display: flex;
        justify-content: space-between;
    }

    .user-info-field-buttons {
        margin-top: 12px;
        display: flex;
        align-items: normal;
        width: 200px;
    }

    .user-info-field-buttons button {
        padding: 0 12px;
        border-radius: 0;
        color: white;
        height: 32px;
    }

    .pass_match {
        margin: 0;
        padding: 5px;
        display: compact;
        color: red;
    }

    .user-info-change-confirm_window {
        position: absolute;

        height: 150px;

        border: 2px solid #000000;
        box-sizing: border-box;

        display: flex;

        background: #ffffff;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);

        bottom: 55%;
        left: 30%;
        z-index: 1000;
    }

    .confirm_window_box {
        display: flex;
        align-content: center;
        align-items: center;
        justify-content: center;
        flex-direction: column;

        height: calc(100% - 12px);

        padding: 8px;
    }

    .confirm_window_text {
        font-size: 20px;
        font-weight: 400;
        padding: 8px;
    }

    .confirm_window_buttons {
        width: 100%;
        box-sizing: border-box;
        padding: 0 16px;

        display: flex;
        justify-content: flex-end;
    }

    .confirm_window_buttons button {
        padding: 0 12px;
        border: 1px solid #000000;
        border-radius: 0;
        color: white;

        width: 80px;
        height: 32px;
    }

    .save__button {
        background: #2283df;
        border: 1px solid #2283df;
        margin-right: 8px;
    }

    .save__button:hover {
        cursor: pointer;
    }

    .save__button:disabled {
        color: rgba(0, 0, 0, 0.5);
        border: 1px solid rgba(34, 131, 223, 0.4);
        background: rgba(34, 131, 223, 0.4);
        cursor: not-allowed;
    }

    .cancel__button {
        background: #ff3737;
        border: 1px solid #ff3737;
        margin-left: 8px;
    }

    .cancel__button:hover {
        cursor: pointer;
    }

    .content-block-user-photo {
        max-height: 100%;
    }

    .content-container {
        display: flex;
        flex-direction: column;
    }

    .user-photo-container {
        background: #f9f9f9;
        border: 1px solid #e5e5e5;
        padding: 8px;
        box-sizing: border-box;
    }

    .user-photo-text {
        font-size: 32px;
        font-weight: 500;
    }

    .user-photo {
        height: 300px;
    }

    /* Footer */
    .footer {
        min-height: 200px;
        width: 100%;
        bottom: 0;
        background: rgba(17, 17, 17, 0.99);
    }

    @media (max-width: 1160px) {
        .content-block {
            flex-wrap: wrap;
        }
    }
</style>