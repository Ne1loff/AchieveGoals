<script lang="ts">

    import {onDestroy} from "svelte";

    export let text: string = "";

    let lastTitle = document.title;
    $: title = text + (text.length > 0 ? ': ' : '');

    onDestroy(() => document.title = lastTitle);

</script>

<svelte:head>
    <title>{title}AchieveGoals</title>
</svelte:head>