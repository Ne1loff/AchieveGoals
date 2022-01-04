<script lang="ts">

    export let bindText;
    export let placeholderText;

</script>


<div class="input_container">
    <input class="input_field" type="text" placeholder={placeholderText} autocomplete="on"
           bind:value={bindText}>
    <div class="void"></div>
</div>


<style>

    :root {
        --custom-height: 22px;
        --custom-width: 100%;
        --custom-margin: 0;
        --custom-border-color: #dddfe2;
    }

    .input_container {
        background: white;
        height: 22px;
        max-width: var(--custom-width, 100%);
        max-height: var(--custom-height);
        margin: var(--custom-margin);

        padding: 12px 14px;
        font-size: 17px;
        border: 1px solid var(--custom-border-color);
        border-radius: 6px;

        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .input_field {
        height: 100%;
        flex: 1;
        border: none;

        padding: 0;
        margin: 0;
    }

    .input_field:focus {
        outline: none;
    }

    .void {
        width: 30px;
        height: 30px;
        visibility: hidden;
    }

</style>