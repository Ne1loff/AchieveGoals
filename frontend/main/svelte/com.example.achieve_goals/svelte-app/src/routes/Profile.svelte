<script lang="ts">
    import {navigate} from "svelte-routing";
    import Header from "./Header.svelte";
    import * as _ from 'lodash/fp';

    let user = {
        username: '',
        name: '',
        surname: '',
        male: '',
        email: '',
        locality: '',
        avatar: ''
    };
    let userViaChanges = JSON.parse(JSON.stringify(user));

    let fileVar = null;
    let toChange = [false, false, false, false, false, false]

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
                    alert("Profile avatar has been update")
                    console.log(response.status)
                } else {
                    alert(response.status)
                    console.log(response)
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
                alert("Profile has been update")
                console.log(response.status)
            } else {
                alert(response.status)
                console.log(response.status)
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

    function save() {
        toChange[toChange.indexOf(true)] = false
        wasSave = true
    }

    let input;
    let image;
    let wasSave = false;
    let showImage = false;
    let wasChanged;
    getUser()

    $:{
        wasChanged = (!(_.isEqual(user, userViaChanges)) && wasSave) || showImage;
        console.log(user)
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
    }

    //todo enter with Enter key
</script>

<Header {user} activePage="Profile"/>

<div class="main-content">
    <div class="main-content-wrapper">
        <div class="content">
            <div class="container">
                <div class="row-content">
                    <div class="content-block">
                        <div class="content-block-name">
                            <div class="content-container-text">
                                <span class="content-block-name-text">Profile</span>
                            </div>
                            <div class="content-right-block">
                                <div class="content-block-user-info">
                                    <div class="content-container">
                                        <div class="user-info-top">
                                            <div class="user-info-text">Username</div>
                                            {#if !toChange[0]}
                                                <div class="user-info-field">
                                                    <div>
                                                        {userViaChanges.username !== user.username ?
                                                            userViaChanges.username : user.username }
                                                    </div>
                                                    <a on:click={() => {
                                                        cancel()
                                                        toChange[0] = true
                                                    }}>Change</a>
                                                </div>
                                            {:else}
                                                <div class="user-info-field-change">
                                                    <input class="input_field" type="text"
                                                           bind:value={userViaChanges.username}>
                                                    <div class="user-info-field-buttons">
                                                        <button class="save__button" type="button" on:click={save}>
                                                            Save
                                                        </button>
                                                        <button class="cancel__button" type="button" on:click={cancel}>
                                                            Cancel
                                                        </button>
                                                    </div>
                                                </div>
                                            {/if}
                                        </div>
                                        <div class="user-info">
                                            <div class="user-info-text">Name</div>
                                            {#if !toChange[1]}
                                                <div class="user-info-field">
                                                    <div>
                                                        {userViaChanges.name === '' || userViaChanges.name === null ?
                                                            "Not specified" : userViaChanges.name !== user.name ?
                                                                userViaChanges.name : user.name}
                                                    </div>
                                                    <a on:click={() => {
                                                        cancel()
                                                        toChange[1] = true
                                                    }}>Change</a>
                                                </div>
                                            {:else}
                                                <div class="user-info-field-change">
                                                    <input class="input_field" type="text"
                                                           bind:value={userViaChanges.name}>
                                                    <div class="user-info-field-buttons">
                                                        <button class="save__button" type="button" on:click={save}>
                                                            Save
                                                        </button>
                                                        <button class="cancel__button" type="button" on:click={cancel}>
                                                            Cancel
                                                        </button>
                                                    </div>
                                                </div>
                                            {/if}
                                        </div>
                                        <div class="user-info">
                                            <div class="user-info-text">Surname</div>
                                            {#if !toChange[2]}
                                                <div class="user-info-field">
                                                    <div>
                                                        {userViaChanges.surname === '' || userViaChanges.surname === null ?
                                                            "Not specified" : userViaChanges.surname !== user.surname ?
                                                                userViaChanges.surname : user.surname}
                                                    </div>
                                                    <a on:click={() => {
                                                        cancel()
                                                        toChange[2] = true
                                                    }}>Change</a>
                                                </div>
                                            {:else}
                                                <div class="user-info-field-change">
                                                    <input class="input_field" type="text"
                                                           bind:value={userViaChanges.surname}>
                                                    <div class="user-info-field-buttons">
                                                        <button class="save__button" type="button" on:click={save}>
                                                            Save
                                                        </button>
                                                        <button class="cancel__button" type="button" on:click={cancel}>
                                                            Cancel
                                                        </button>
                                                    </div>
                                                </div>
                                            {/if}
                                        </div>
                                        <div class="user-info">
                                            <div class="user-info-text">Password</div>
                                            {#if !toChange[3]}
                                                <div class="user-info-field">
                                                    <div>
                                                        ********
                                                    </div>
                                                    <a>Change</a>
                                                </div>
                                            {:else}
                                                <div class="user-info-field">
                                                    <div>
                                                        ********
                                                    </div>
                                                    <a>Change do not support</a>
                                                </div>
                                            {/if}
                                        </div>
                                        <div class="user-info">
                                            <div class="user-info-text">Gender</div>
                                            {#if !toChange[4]}
                                                <div class="user-info-field">
                                                    <div>
                                                        {user.male ? "Male" : "Female"}
                                                    </div>
                                                    <a on:click={() => {
                                                        cancel()
                                                        toChange[4] = true
                                                    }}>Change</a>
                                                </div>
                                            {:else}
                                                <div class="user-info-field-change">
                                                    <!--todo:change to select -->
                                                    In development
                                                    <div class="user-info-field-buttons">
                                                        <button class="save__button" type="button" on:click={save}>
                                                            Save
                                                        </button>
                                                        <button class="cancel__button" type="button" on:click={cancel}>
                                                            Cancel
                                                        </button>
                                                    </div>
                                                </div>
                                            {/if}
                                        </div>
                                        <div class="user-info">
                                            <div class="user-info-text">Locality</div>
                                            {#if !toChange[5]}
                                                <div class="user-info-field">
                                                    <div>
                                                        {user.locality}
                                                    </div>
                                                    <a on:click={() => {
                                                        cancel()
                                                        toChange[5] = true
                                                    }}>Change</a>
                                                </div>
                                            {:else}
                                                <div class="user-info-field-change">
                                                    <!--todo:change to select -->
                                                    In development
                                                    <div class="user-info-field-buttons">
                                                        <button class="save__button" type="button" on:click={save}>
                                                            Save
                                                        </button>
                                                        <button class="cancel__button" type="button" on:click={cancel}>
                                                            Cancel
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
                                <span class="user-photo-text">Avatar</span>
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
                        </div>
                        {#if wasChanged}
                            <div class="user-info-field-buttons">
                                <button class="save__button" type="button" on:click={mainSave}>Save</button>
                                <button class="cancel__button" type="button" on:click={mainCancel}>Cancel</button>
                            </div>
                        {/if}
                    </div>
                </div>
            </div>
        </div>
        <div class="footer">
            Footer
        </div>
    </div>
</div>

<style>

    body {
        overflow-y: scroll;
    }

    /* Main content */
    .main-content {
        display: block;
        flex-direction: column;
        position: absolute;

        bottom: 0;

        height: calc(100vh - 4rem - 5px);
        width: 100%;
    }

    .main-content-wrapper {
        display: flex;
        flex-direction: column;
        height: 100%;
    }

    .content {
        display: flex;
        flex-direction: column;
    }

    .container {
        margin: 0 auto;
        padding: 0 20px;
        min-width: 75vw;
        box-sizing: border-box;
        min-height: 70vh;
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
        flex-wrap: wrap;
    }

    .content-right-block {
        display: flex;
        justify-content: normal;

    }

    .content-block-name {
        max-width: 970px;
        width: 100%;
        margin: 8px 0;
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
        width: calc(100% - 4rem);
    }

    .content-block-user-photo {
        padding: 0 8px;
        box-sizing: border-box;
    }

    .content-block-user-photo input {
        margin: 8px 0 0 0;
        padding: 4px;
        border-radius: 0;
    }

    .input_field {
        border-radius: 0;
        width: 300px;
        height: 40px;
    }

    .user-info-top {
        width: 100%;
        padding: 20px 0;
        background: #f9f9f9;
        border-top: 1px solid #e5e5e5;
        border-bottom: 1px solid #e5e5e5;
        display: flex;
        justify-content: space-between;
    }

    .user-info {
        width: 100%;
        padding: 20px 0;
        background: #f9f9f9;
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
        height: 40px;
    }

    .save__button {
        background: #2283df;
        border: 1px solid #2283df;
        margin-right: 8px;
    }

    .cancel__button {
        background: #ff3737;
        border: 1px solid #ff3737;
        margin-left: 8px;
    }

    .content-block-user-photo {
        height: 355px;
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
        /* todo: */
    }

    /* Footer */
    .footer {
        height: calc(100vh - 70vh - 4rem - 5px);
        width: 100%;
        bottom: 0;
        position: absolute;
        background: rgba(17, 17, 17, 0.99);
    }

</style>