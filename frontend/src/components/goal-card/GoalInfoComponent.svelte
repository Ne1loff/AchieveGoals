<script lang="ts">
    import ModalWindow from "../modals/ModalWindow.svelte";
    import {navigate} from "svelte-routing";

    export let props: { id: string };

    const navigateToPrev = () => {
        const prevUrl = document.referrer;
        navigate(prevUrl ?? '/home/goals');
    }

</script>

<ModalWindow on:close={navigateToPrev}>

</ModalWindow>

<style>

</style>