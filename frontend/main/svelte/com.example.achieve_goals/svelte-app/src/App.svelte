<script>
    import {navigate, Route, Router} from "svelte-routing";
    import Login from "./routes/Login.svelte";
    import Registration from "./routes/Registration.svelte"
    import Main from "./routes/Main.svelte"

    export let url = '/test'
</script>


<Router url="{url}">
    <div>
        <Route path="/login">
            <Login/>
        </Route>
        <Route path="/registration">
            <Registration/>
        </Route>
        <Route path="/*">
            <Main/>
        </Route>
    </div>
</Router>

<style>
    :global(body) {
        margin: 0;
        padding: 0;
        overflow: hidden;
    }
</style>