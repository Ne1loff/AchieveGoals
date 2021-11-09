<script lang="ts">
    import {navigate} from "svelte-routing";
    import Header from "./components/Header.svelte";

    let avatarUrl = ["/static/img_avatar.png", "/static/img_avatar2.png"]

    let user = {
        username: '',
        name: '',
        surname: '',
        male: '',
        email: '',
        locality: '',
    };


    fetch('http://localhost:8080/api/user')
        .then(response => {
            console.log(response.status)
            if (response.status === 200)
                return response.json()
            else {
                navigate('/login')
            }
        })
        .then(commit => {
            user = commit
            console.log(user)
        })
</script>

<div id="app">
    <div class="root_page">
        <Header {user}/>
        <div class="root_wrapper">
            <div class="sidebar">
                <div class="sidebar_buttons">
                    <div class="create_goal_button">
                        <div class="button_icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-journal-plus" viewBox="0 0 16 16">
                              <path fill-rule="evenodd" d="M8 5.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V10a.5.5 0 0 1-1 0V8.5H6a.5.5 0 0 1 0-1h1.5V6a.5.5 0 0 1 .5-.5z"/>
                              <path d="M3 0h10a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2v-1h1v1a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H3a1 1 0 0 0-1 1v1H1V2a2 2 0 0 1 2-2z"/>
                              <path d="M1 5v-.5a.5.5 0 0 1 1 0V5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0V8h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0v.5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1z"/>
                            </svg>
                        </div>
                        <div class="button_text">
                            <div class="create_goal">
                                <button type="button">Create goal</button>
                            </div>
                        </div>
                    </div>
                    <div class="create_task_button">
                        <div class="button_icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-journal-plus" viewBox="0 0 16 16">
                              <path fill-rule="evenodd" d="M8 5.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V10a.5.5 0 0 1-1 0V8.5H6a.5.5 0 0 1 0-1h1.5V6a.5.5 0 0 1 .5-.5z"/>
                              <path d="M3 0h10a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2v-1h1v1a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H3a1 1 0 0 0-1 1v1H1V2a2 2 0 0 1 2-2z"/>
                              <path d="M1 5v-.5a.5.5 0 0 1 1 0V5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0V8h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0v.5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1z"/>
                            </svg>
                        </div>
                        <div class="button_text">
                            <div class="create_goal">
                                <button type="button">Create goal</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="sidebar_navigation">
                    <div>OLa</div>
                    <div>OLa</div>
                    <div>OLa</div>
                    <div>OLa</div>
                </div>
                <div class="sidebar_app-link-wrapper">
                    <a href="https://google.com">Hello world!</a>
                </div>
            </div>
            <div class="root_content">
                <div class="root_content-inner">

                </div>
                <div class="footer">

                </div>
            </div>
        </div>
    </div>
</div>


<style>
    #app {
        min-height: 100vh;
    }

    .root_page {
        height: 100vh;

        display: flex;
        justify-content: space-between;
    }

    .root_wrapper {
        display: block;
        flex-direction: column;
        position: absolute;

        bottom: 0;

        height: calc(100vh - 4rem - 5px);
        width: 100%;
    }

    .sidebar {
        display: flex;
        flex-direction: column;
        justify-content: space-between;

        height: 100%;
        width: 16rem;
    }

    .sidebar_buttons {
        margin: 1px 32px 10px 8px;
        padding-top: 1px;

        display: flex;
        flex-direction: column;
        justify-content: space-between;

        height: 90px;
        width: calc(186px - 16px);
    }

    .create_goal_button, .create_task_button {
        width: 100%;
        border-radius: 5px;

        display: flex;
        align-items: center;
        justify-content: center;

        height: 40px;
        margin: 8px 0;
        background: #f4f4f4;
    }

    .button_icon {
        justify-content: center;
        align-items: center;
        z-index: 1000;
        display: block;
    }

    .button_text {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 70%;
    }

    .create_goal {
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .create_goal button {
        height: 40px;
        width: 186px;
        margin: 0;
        border: none;
        padding: 0;
        font-size: 20px;
        font-weight: 500;
    }
</style>