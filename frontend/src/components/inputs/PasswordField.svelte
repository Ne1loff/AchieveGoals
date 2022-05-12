<script lang="ts">
    import Icon from "@iconify/svelte"
    import {onMount} from "svelte";

    export let password: string;
    export let placeholderText: string = ' ';
    export let label: string = undefined;
    export let newPass: boolean = false;
    export let showPassword: boolean = false;
    export let error: boolean = false;
    export let validate: boolean = false;
    export let required: boolean = false;
    export let pattern: RegExp = undefined;
    export let errorMessage: string = undefined;

    export let IconWhenPass = 'akar-icons:eye-closed';
    export let IconWhenText = 'akar-icons:eye-open';

    let inputEl: HTMLInputElement;
    const inputId = Date.now().toString(36) + Math.random().toString(36).substring(2);

    $: {
        if (inputEl) {
            inputEl.type = showPassword ? 'text' : 'password';
        }
    }

    $: error = (password && validate && pattern) ? !pattern?.test(password) : error;

    const isRequired = () => {
        if (required) error = password === '';
    }

    onMount(() => {
        if (pattern) validate = true;
        inputEl.required = required;
        inputEl.autocomplete = newPass ? 'new-password' : 'on'
    });

</script>

<div class="input_container" class:error>

    <input id={inputId}
           class="input_field"
           bind:this={inputEl}
           bind:value={password}
           on:focusout={isRequired}
           on:focusin={() => error = false}
           placeholder={placeholderText}>
    {#if label}
        <label for={inputId} class="input_label">{label}</label>
    {/if}
    <div class="icon_holder" style="visibility: {password === '' ? 'hidden' : 'visible'}"
         on:click={() => {showPassword = !showPassword}}>
        <Icon class="input_box_icon"
              icon="{showPassword ? IconWhenText : IconWhenPass }"/>
    </div>
</div>
{#if errorMessage && error}
    <span class="error-message">{errorMessage}</span>
{/if}


<style lang="scss">

  :root {
    --custom-height: 22px;
    --custom-width: 100%;
    --custom-margin: 0;
    --custom-border-color: #dddfe2;
    --custom-transparent-duration: 200ms;
    --own-input-error-color: #fa4d56;
  }

  .input_container {
    position: relative;

    background: white;
    width: var(--custom-width);
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

  .error-message {
    color: var(--own-input-error-color);
  }

  .input_container.error {
    color: var(--own-input-error-color);
    border-color: var(--own-input-error-color);
  }

  .input_label {
    position: absolute;

    top: 50%;
    left: 1rem;
    transform: translate(0, -50%);

    cursor: text;
    transition: top var(--custom-transparent-duration) ease-in,
    left var(--custom-transparent-duration) ease-in,
    font-size var(--custom-transparent-duration) ease-in,
    transform var(--custom-transparent-duration) ease-in;
    background: inherit;
  }

  .input_field:focus ~ .input_label,
  .input_field:not(:placeholder-shown).input_field:not(:focus) ~ .input_label {
    top: -10%;
    font-size: 0.8rem;
    left: 0.8rem;
    transform: translate(0px, -10%);
  }

  .input_field:-webkit-autofill.input_field:not(:focus) ~ .input_label {
    top: -10%;
    font-size: 0.8rem;
    left: 0.8rem;
    transform: translate(0px, -10%);
  }

  .input_field {
    height: 100%;
    flex: 1;
    border: none;

    padding: 0;
    margin: 0;
  }

  input::-webkit-input-placeholder {
    opacity: 0;
    transition: var(--custom-transparent-duration) ease-in;
  }

  input::-moz-placeholder {
    opacity: 0;
    transition: var(--custom-transparent-duration) ease-in;
  }

  input:-moz-placeholder {
    opacity: 0;
    transition: var(--custom-transparent-duration) ease-in;
  }

  input:-ms-input-placeholder {
    opacity: 0;
    transition: var(--custom-transparent-duration) ease-in;
  }

  input:focus::-webkit-input-placeholder {
    opacity: 1;
    transition: var(--custom-transparent-duration) ease-in;
  }

  input:focus::-moz-placeholder {
    opacity: 1;
    transition: var(--custom-transparent-duration) ease-in;
  }

  input:focus:-moz-placeholder {
    opacity: 1;
    transition: var(--custom-transparent-duration) ease-in;
  }

  input:focus:-ms-input-placeholder {
    opacity: 1;
    transition: var(--custom-transparent-duration) ease-in;
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