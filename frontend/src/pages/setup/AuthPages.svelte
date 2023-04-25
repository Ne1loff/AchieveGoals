<script lang="ts">
    import LoginPage from "../LoginPage.svelte";
    import {AuthPagesUrl} from "../../resources/router-config/urls";
    import RegistrationPage from "../RegistrationPage.svelte";
    import NotfoundPage from "../NotfoundPage.svelte";

    export let params: { page: string };

</script>

{#if params.page === AuthPagesUrl.LOGIN}
    <LoginPage/>
{:else if params.page === AuthPagesUrl.REGISTRATION}
    <RegistrationPage/>
{:else}
    <NotfoundPage/>
{/if}