<script lang="ts">

    import {onMount} from "svelte";
    import Icon from "@iconify/svelte";
    import {l10n} from "../../resources/localization/l10n";
    import Indicator from "../indicator/Indicator.svelte";
    import {getPasswordDifficult} from "./utils";

    export let value: string = '';
    export let type: 'text' | 'password' | 'email' | 'number' | 'url' = 'text';
    export let label: string | undefined = undefined;
    export let placeholderText: string = ' ';
    export let newPass: boolean = false;
    export let showPassword: boolean = false;
    export let validate: boolean = false;
    export let required: boolean = false;
    export let pattern: RegExp = /.*/;
    export let forceError: boolean = false;
    export let requiredErrorMessage: string = l10n.requiredErrorText;
    export let patternErrorMessage: string | undefined = undefined;
    export let skeleton: boolean = false;
    export let autocomplete: string = 'on';
    export let disableAutocomplete: boolean = false;
    export let showPasswordDifficult: boolean = false;

    export let IconWhenPass = 'akar-icons:eye-closed';
    export let IconWhenText = 'akar-icons:eye-open';

    let error: boolean;
    let requiredError: boolean = false;
    let patternError: boolean = false;

    onMount(() => {
        if (pattern) validate = true;
        inputEl.type = type;
        inputEl.required = required;
        if (disableAutocomplete) {
            inputEl.removeAttribute('autocomplete');
        } else {
            inputEl.autocomplete = autocomplete ?? 'on';
            if (type === 'password' && newPass)
                inputEl.autocomplete = 'new-password';
        }
        if (skeleton) inputEl.tabIndex = -1;
    });

    let inputEl: HTMLInputElement;
    const inputId = Date.now().toString(36) + Math.random().toString(36).substring(2);

    const isRequired = () => {
        if (required) {
            requiredError = value === '';
            patternError = requiredError ? false : patternError;
        }
        unfocused = true;
    }

    let unfocused: boolean;

    $: patternError = (value && validate) ? !pattern?.test(value) ?? false : patternError;
    $: error = requiredError || patternError
    $: {
        if (type === 'password' && inputEl) inputEl.type = showPassword ? 'text' : 'password';
    }

    $: showPasDiff = showPasswordDifficult && !unfocused && type === 'password' && newPass && value.length > 3;
</script>

<div class="input_container"
     class:error={error || forceError}
     class:bx--skeleton={skeleton}
     class:bx--text-input={skeleton}
     class:ag--pas-diff={showPasDiff}
>
    <input bind:this={inputEl}
           bind:value
           on:focusout={isRequired}
           on:focusin={() => unfocused = false}
           id={inputId}
           class="input_field"
           placeholder={placeholderText}/>
    {#if label && !skeleton}
        <label for={inputId} class="input_label">{label}</label>
    {/if}
    {#if type === 'password'}
        <div class="icon_holder" style="visibility: {value === '' ? 'hidden' : 'visible'}"
             on:click={() => {showPassword = !showPassword}}>
            <Icon class="input_box_icon"
                  icon="{showPassword ? IconWhenText : IconWhenPass }"/>
        </div>
    {/if}
    {#if showPasDiff}
        <div class="pass-indicator">
            <Indicator count={5}
                       currentStep={getPasswordDifficult(value)}
                       colors={['#FF0000', '#FFA500', '#FFD700', '#ADFF2F', '#32CD32']}
                       secondary="inherit"
                       size={{h: '.5rem', w: 'calc(var(--custom-width) / 5)'}}
                       radius="6px"
                       singleText/>
        </div>
    {/if}
</div>
{#if error && (patternErrorMessage || requiredErrorMessage)}
    <div class="error-container">
        <span class="error-message">
            {patternError && patternErrorMessage ? patternErrorMessage : requiredError ? requiredErrorMessage ?? '' : ''}
        </span>
    </div>
{/if}


<style lang="scss">

  :root {
    --custom-background-color: inerhit;
    --custom-height: 22px;
    --custom-width: 100%;
    --custom-margin: 0;
    --custom-border-color: #dddfe2;
    --custom-transparent-duration: 200ms;
    --own-input-error-color: #fa4d56;
  }

  .input_container {
    position: relative;

    background: var(--custom-background-color);
    width: var(--custom-width);
    height: var(--custom-height);
    margin: var(--custom-margin);

    padding: 4px 8px;
    font-size: 17px;
    border: 1px solid var(--custom-border-color);
    border-radius: 6px;

    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .input_container.ag--pas-diff {
    height: calc(var(--custom-height) + .5rem);
    padding-bottom: .75rem;
  }

  .pass-indicator {
    position: absolute;
    width: calc(100% - 2px);

    overflow: hidden;

    bottom: 2px;
    left: 1px;
  }

  .input_container:focus-within {
    outline: 2px solid var(--cds-focus, #0f62fe);
    outline-offset: -2px;
  }

  .input_container.bx--skeleton {
    overflow: hidden;
  }

  .error-container {
    width: var(--custom-width);
    display: flex;
    justify-content: start;
  }

  .error-message {
    color: var(--own-input-error-color);
  }

  .input_container:focus-within.error {
    border-color: var(--custom-border-color);
    color: inherit;
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