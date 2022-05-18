<script lang="ts">

    import {onMount} from "svelte";
    import Icon from "@iconify/svelte";
    import {l10n} from "../../resources/localization/l10n";

    export let value: string;
    export let type: 'text' | 'password' | 'email' | 'number' | 'url' = 'text';
    export let label: string = undefined;
    export let placeholderText: string = ' ';
    export let newPass: boolean = false;
    export let showPassword: boolean = false;
    export let validate: boolean = false;
    export let required: boolean = false;
    export let pattern: RegExp = undefined;
    export let forceError: boolean = false;
    export let requiredErrorMessage: string = l10n.requiredErrorText;
    export let patternErrorMessage: string = undefined;

    export let IconWhenPass = 'akar-icons:eye-closed';
    export let IconWhenText = 'akar-icons:eye-open';

    let error: boolean = false;
    let requiredError: boolean = false;
    let patternError: boolean = false;

    onMount(() => {
        if (pattern) validate = true;
        inputEl.type = type;
        inputEl.required = required;
        if (type === 'password')
            inputEl.autocomplete = newPass ? 'new-password' : 'on';
    });

    let inputEl: HTMLInputElement;
    const inputId = Date.now().toString(36) + Math.random().toString(36).substring(2);

    const isRequired = () => {
        if (required) {
            requiredError = value === '';
            patternError = requiredError ? false : patternError;
        }
    }

    $: patternError = (value && validate && pattern !== undefined) ? !pattern.test(value) : patternError;
    $: error = requiredError || patternError;
    $: {
        if (type === 'password' && inputEl) inputEl.type = showPassword ? 'text' : 'password';
    }

</script>


<div class="input_container" class:error={error || forceError}>
    <input bind:this={inputEl}
           bind:value
           on:focusout={isRequired}
           id={inputId}
           class="input_field"
           placeholder={placeholderText}
           autocomplete="on">
    {#if label}
        <label for={inputId} class="input_label">{label}</label>
    {/if}
    {#if type === 'password'}
        <div class="icon_holder" style="visibility: {value === '' ? 'hidden' : 'visible'}"
             on:click={() => {showPassword = !showPassword}}>
            <Icon class="input_box_icon"
                  icon="{showPassword ? IconWhenText : IconWhenPass }"/>
        </div>
    {/if}
</div>
{#if error}
    <div class="error-container">
        <span class="error-message">
            {patternError ? patternErrorMessage : requiredError ? requiredErrorMessage : ''}
        </span>
    </div>
{/if}

<style lang="scss">

  :root {
    --custom-background-color: #fff;
    --custom-height: 22px;
    --custom-width: 100%;
    --custom-margin: 0;
    --custom-border-color: #dddfe2;
    --custom-transparent-duration: 200ms;
  }

  .input_container {
    position: relative;

    background: var(--custom-background-color);
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
  }

  .error-container {
    width: var(--custom-width);
    display: flex;
    justify-content: start;
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
    left: 14px;
    transform: translate(0, -50%);

    cursor: text;
    transition: top var(--custom-transparent-duration) ease-in,
    left var(--custom-transparent-duration) ease-in,
    font-size var(--custom-transparent-duration) ease-in,
    transform var(--custom-transparent-duration) ease-in;
    background: var(--custom-background-color);

    user-select: none;
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

    background: var(--custom-background-color);
    color: var(--cds-text-01);

    padding: 0;
    margin: 0;
  }

  input::-webkit-input-placeholder {
    opacity: 0;
    color: var(--cds-text-placeholder);
    transition: var(--custom-transparent-duration) ease-in;
  }

  input::-moz-placeholder {
    opacity: 0;
    color: var(--cds-text-placeholder);
    transition: var(--custom-transparent-duration) ease-in;
  }

  input:-moz-placeholder {
    opacity: 0;
    color: var(--cds-text-placeholder);
    transition: var(--custom-transparent-duration) ease-in;
  }

  input:-ms-input-placeholder {
    opacity: 0;
    color: var(--cds-text-placeholder);
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
    background: var(--cds-hover-ui);
  }

  :global(.input_box_icon) {
    width: 20px;
    height: 20px;
  }

  .void {
    width: 30px;
    height: 30px;
    visibility: hidden;
  }

</style>