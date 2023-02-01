<script lang="ts">

    import NotfoundPage from "./pages/NotfoundPage.svelte";
    import WelcomePage from "./pages/WelcomePage.svelte";
    import PopoverGlobal from "./components/popover/global/PopoverGlobal.svelte";
    import Settings from "./components/settings-components/Settings.svelte";
    import AuthPages from "./pages/setup/AuthPages.svelte";
    import AppHomePages from "./pages/setup/AppHomePages.svelte";
    import {Route, Router} from "svelte-routing";
    import AgToasts from "./components/toasts/AgToasts.svelte";
    import ThemeManager from "./components/theme/ThemeManager.svelte";

    // onMount(() => {
    //     ServiceFactory.INSTANCE.toastService.success("test", 'test', new Date().toLocaleString(), 60 * 5 * 1000);
    //     ServiceFactory.INSTANCE.toastService.dialog("test", {
    //         accept: () => alert("test"),
    //         reject: () => alert("reject")
    //     }, 60 * 5 * 1000);
    // });
</script>

<svelte:head>
    <link rel="stylesheet" href="https://unpkg.com/carbon-components-svelte/css/all.css"/>
</svelte:head>
<ThemeManager/>
<Router>
    <Route path="/:lang" let:params>
        <WelcomePage/>
    </Route>
    <Route path="/auth/:page" let:params>
        <AuthPages {params}/>
    </Route>
    <Route path="/app/*">
        <Router>
            <AppHomePages/>
            <Route path="settings/:tab/*subTab" let:params>
                <Settings props={params}/>
            </Route>
        </Router>
    </Route>
    <Route path="/*">
        <NotfoundPage/>
    </Route>
</Router>
<AgToasts/>
<PopoverGlobal/>

<style>

    :global(body) {
        min-height: 100vh;
        min-width: 100vw;

        padding: 0;
        margin: 0;

        overflow: hidden;
    }

</style>