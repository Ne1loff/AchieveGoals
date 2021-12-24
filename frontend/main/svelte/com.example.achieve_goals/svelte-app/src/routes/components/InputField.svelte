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

    .input_container {
        background: white;
        height: 22px;
        max-width: 100%;

        padding: 12px 14px;
        font-size: 17px;
        border: 1px solid #dddfe2;
        border-radius: 6px;

        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .input_container:hover {
        border: 1px solid #b0b0b0;
    }


    .input_field {
        height: 100%;
        width: 85%;
        max-width: 90%;
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