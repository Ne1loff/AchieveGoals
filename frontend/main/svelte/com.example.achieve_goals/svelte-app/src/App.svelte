<script>
    import {Route, Router} from "svelte-routing";
    import Login from "./routes/Login.svelte";
    import Registration from "./routes/Registration.svelte"
    import Main from "./routes/Main.svelte"
    import Notfound from "./routes/Notfound.svelte";
    import Home from "./routes/Home.svelte";
    import Profile from "./routes/Profile.svelte";
</script>


<Router>
    <div>
        <Route path="/login">
            <Login/>
        </Route>
        <Route path="/registration">
            <Registration/>
        </Route>
        <Route path="/home">
            <Home/>
        </Route>
        <Route path="/user/profile">
            <Profile/>
        </Route>
        <Route path="/">
            <Main/>
        </Route>
        <Route path="/*">
            <Notfound/>
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