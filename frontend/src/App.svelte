<script lang="ts">
    import {SvelteToast} from '@zerodevx/svelte-toast'
    import {Route, Router} from "svelte-routing";
    import {onMount} from "svelte";
    import LoginPage from "./pages/LoginPage.svelte";
    import RegistrationPage from "./pages/RegistrationPage.svelte";
    import HomePage from "./pages/HomePage.svelte";
    import ProfilePage from "./pages/ProfilePage.svelte";
    import NotfoundPage from "./pages/NotfoundPage.svelte";
    import MainPage from "./pages/MainPage.svelte";
    import {setLanguage} from "./resources/localization/l10n";
    import {getLanguage} from "./resources/localization/languages/languages";

    onMount(() => {
        setLanguage(getLanguage(navigator.language));
    });

</script>

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
    <Route path="/home">
        <HomePage/>
        <!--        <Route path="/home/goal/:id" let:params><Route/>    -->
    </Route>
    <Route path="/user/profile">
        <ProfilePage/>
    </Route>
    <Route path="/*">
        <NotfoundPage/>
    </Route>
</Router>
<SvelteToast options={{pausable: true}}/>

<style>

    :global(body) {
        min-height: 100vh;
        min-width: 100vw;

        padding: 0;
        margin: 0;

        overflow: hidden;
    }

</style>