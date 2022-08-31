<script lang="ts">
    import {SvelteToast} from '@zerodevx/svelte-toast'
    import NotfoundPage from "./pages/NotfoundPage.svelte";
    import WelcomePage from "./pages/WelcomePage.svelte";
    import {Theme} from "carbon-components-svelte";
    import PopoverGlobal from "./components/popover/global/PopoverGlobal.svelte";
    import Settings from "./components/settings-components/Settings.svelte";
    import AuthPages from "./pages/setup/AuthPages.svelte";
    import AppHomePages from "./pages/setup/AppHomePages.svelte";
    import {Route, Router} from "svelte-routing";
</script>

<svelte:head>
    <link rel="stylesheet" href="https://unpkg.com/carbon-components-svelte/css/all.css"/>
</svelte:head>
<svelte:window on:pushstate={() => console.log(window.location)}/>
<Theme persist persistKey="__carbon-theme"/>
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
            <Route path="settings/:tab" let:params>
                <Settings props={params}/>
            </Route>
        </Router>
    </Route>
    <Route path="/*">
        <NotfoundPage/>
    </Route>
</Router>
<SvelteToast options={{ pausable: true }}/>
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