<script lang="ts">

    import Checkbox from "./checkbox/Checkbox.svelte";

    export let position: 'left' | 'center' | 'right' = 'center';
    export let checked: boolean;
    export let labelText: string;
    export let activeContainer: boolean = false;
    export const props: {
        size: string, secondaryColor: string, primaryColor: string
    } = {
        size: "1.5rem", secondaryColor: "var(--cds-icon-01)",
        primaryColor: "var(--cds-button-primary)"
    }

    let id = "id" + Math.random().toString(16).slice(2);
    let canChange;
</script>

<div class="checkbox-line" style="justify-content: {position}">
    <div class="checkbox-container" on:click={() => {if (activeContainer && canChange) checked = !checked}}
         style="cursor: {activeContainer ? 'pointer' : 'default'};">
        <Checkbox {id} class="custom-checkbox" {...props} bind:checked bind:canChange duration={350}/>
        <label for={id} style="cursor: {activeContainer ? 'pointer' : 'default'};">{labelText}</label>
    </div>
</div>

<style>

    :root {
        --line-padding: 0;
    }

    :global(.custom-checkbox) {
        display: inline-block;
        margin: 0;
        --checkbox-border-width: 7% !important;
        --checkbox-border-width-active: 10% !important;
    }

    .checkbox-line {
        display: flex;
        padding: var(--line-padding);
        width: 100%;
    }

    .checkbox-container {
        display: flex;
        align-items: center;
        height: 100%;
    }

    .checkbox-container label {
        padding-left: .5rem;
        user-select: none;
    }

</style>