<script lang="ts">
    import Icon from "@iconify/svelte"

    let inputTypeBool


    export let password = '';
    export let placeholderText;
    export let newPass;

    export let IconWhenPass = 'akar-icons:eye-closed';
    export let IconWhenText = 'akar-icons:eye-open';

</script>

    <div class="input_container">
        {#if inputTypeBool}
            <input class="input_field" type="text"
                   placeholder="{placeholderText}"
                   bind:value={password}
                   autocomplete="{newPass ? 'new-password' : 'on'}">
        {:else}
            <input class="input_field" type="password"
                   placeholder="{placeholderText}"
                   bind:value={password}
                   autocomplete="{newPass ? 'new-password' : 'on'}">
        {/if}
        <div class="icon_holder" style="visibility: {password === '' ? 'hidden' : 'visible'}"
             on:click={() => {inputTypeBool = !inputTypeBool}}>
            <Icon class="input_box_icon"
                  icon="{inputTypeBool ? IconWhenText : IconWhenPass }"/>
        </div>
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
        max-width: var(--custom-width);
        max-height: var(--custom-height);
        margin: var(--custom-margin);

        padding: 12px 14px;
        font-size: 17px;
        border: 1px solid var(--custom-border-color);
        border-radius: 6px;

        display: flex;
        align-items: center;
        justify-content: space-between;
        user-select: none;
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

    .icon_holder {
        width: 30px;
        height: 30px;
        cursor: pointer;

        display: flex;
        align-items: center;
        justify-content: center;

        border-radius: 50%;
    }

    .icon_holder:hover {
        background: rgba(0, 0, 0, .05);
    }

    :global(.input_box_icon) {
        width: 20px;
        height: 20px;
    }

</style>