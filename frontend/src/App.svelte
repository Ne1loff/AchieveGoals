<script lang="ts">
    import {SvelteToast} from '@zerodevx/svelte-toast'
    import {Route, Router} from "svelte-routing";
    import LoginPage from "./pages/LoginPage.svelte";
    import RegistrationPage from "./pages/RegistrationPage.svelte";
    import HomePage from "./pages/HomePage.svelte";
    import NotfoundPage from "./pages/NotfoundPage.svelte";
    import MainPage from "./pages/MainPage.svelte";
    import GoalsHolder from "./components/homeComponents/GoalsHolder.svelte";
    import {Theme} from "carbon-components-svelte";
    import PopoverGlobal from "./components/popover/global/PopoverGlobal.svelte";
</script>

<svelte:head>
    <link rel="stylesheet" href="https://unpkg.com/carbon-components-svelte/css/all.css"/>
</svelte:head>
<Theme persist persistKey="__carbon-theme"/>
<Router>
    <Route path="/">
        <MainPage/>
    </Route>
    <Route path="/login">
        <LoginPage/>
    </Route>
    <Route path="/registration">
        <RegistrationPage/>
    </Route>
    <Route path="/home/*">
        <HomePage>
            <svelte:fragment slot="content">
                <Router>
                    <Route path="goals/*">
                        <GoalsHolder/>
                        <!--        <Route path="/home/goals/:id" let:params><Route/>    -->
                    </Route>
                </Router>
            </svelte:fragment>
        </HomePage>
    </Route>
    <Route path="/user/profile">
        <!--        <ProfilePage/>-->
    </Route>
    <Route path="/*">
        <NotfoundPage/>
    </Route>
</Router>
<SvelteToast options={{ pausable: true }}/>
<PopoverGlobal />

<style>

    :global(body) {
        min-height: 100vh;
        min-width: 100vw;

        padding: 0;
        margin: 0;

        overflow: hidden;
    }

</style>