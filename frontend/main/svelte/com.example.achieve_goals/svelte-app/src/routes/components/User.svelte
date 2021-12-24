<script lang="ts">
    export let user;
</script>

<style>

    .user-body {
        min-width: 400px;
        width: 100%;
        border-bottom: 1px solid #f4f4f4;
        user-select: none;
    }

    .body-container {
        display: flex;
        position: relative;
        align-items: stretch;
        padding: 8px;
    }

    .container-avatar {
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .avatar-inner {
        margin-right: 16px;
    }

    .avatar {
        width: 40px;
        height: 40px;
        border-radius: 50%;
    }

    .container-info {
        display: flex;
        flex-direction: column;
    }

    .container-username, .container-user-info {
        width: 100%;
    }

    .container-username {
        font-size: 18px;
        font-weight: 700;
    }

</style>

<div class="user-body">
    <div class="body-container">
        <div class="container-avatar">
            <div class="avatar-inner">
                <img class="avatar" src="{user.avatar}"
                     alt="avatar">
            </div>
        </div>
        <div class="container-info">
            <div class="container-username">{user.username}</div>
            <div class="container-user-info">{user.email}</div>
            <div class="container-user-info">{(user.name ? user.name : 'Не задано') + ' ' + (user.surname ? user.surname : '')}</div>
            <div class="container-user-info">{user.male ? 'Мужчина,' : 'Женщина,'}{"\tСтрана: " + user.locality}</div>
        </div>
    </div>
</div>