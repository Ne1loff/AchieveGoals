<script lang="ts">

    import {TagTypeOptional} from "./TagOptions.ts";
    import {SvelteComponent} from "svelte";

    export let type: TagTypeOptional = undefined;
    export let disabled: boolean = false;
    export let href: string;
    export let icon: SvelteComponent | undefined = undefined;

</script>

{#if (href)}
    <div
            disabled={disabled}
            aria-disabled={disabled}
            tabindex={disabled ? '-1' : undefined}
            class:bx--tag={true}
            class:bx--tag--interactive={true}
            class:bx--tag--disabled={disabled}
            class:bx--tag--red={type === 'red'}
            class:bx--tag--magenta={type === 'magenta'}
            class:bx--tag--purple={type === 'purple'}
            class:bx--tag--blue={type === 'blue'}
            class:bx--tag--cyan={type === 'cyan'}
            class:bx--tag--teal={type === 'teal'}
            class:bx--tag--green={type === 'green'}
            class:bx--tag--gray={type === 'gray'}
            class:bx--tag--cool-gray={type === 'cool-gray'}
            class:bx--tag--warm-gray={type === 'warm-gray'}
            class:bx--tag--high-contrast={type === 'high-contrast'}
            class:bx--tag--outline={type === 'outline'}
            {...$$restProps}
    >
        {#if $$slots.icon || icon}
            <div class:bx--tag__custom-icon={true}>
                <slot name="icon">
                    <svelte:component this={icon}/>
                </slot>
            </div>
        {/if}
        <a {href}>
            <span><slot/></span>
        </a>
    </div>
{:else}
    <button
            type="button"
            disabled={disabled}
            aria-disabled={disabled}
            tabindex={disabled ? '-1' : undefined}
            class:bx--tag={true}
            class:bx--tag--interactive={true}
            class:bx--tag--disabled={disabled}
            class:bx--tag--red={type === 'red'}
            class:bx--tag--magenta={type === 'magenta'}
            class:bx--tag--purple={type === 'purple'}
            class:bx--tag--blue={type === 'blue'}
            class:bx--tag--cyan={type === 'cyan'}
            class:bx--tag--teal={type === 'teal'}
            class:bx--tag--green={type === 'green'}
            class:bx--tag--gray={type === 'gray'}
            class:bx--tag--cool-gray={type === 'cool-gray'}
            class:bx--tag--warm-gray={type === 'warm-gray'}
            class:bx--tag--high-contrast={type === 'high-contrast'}
            class:bx--tag--outline={type === 'outline'}
            {...$$restProps}
            on:click
            on:mouseover
            on:mouseenter
            on:mouseleave
    >
        {#if $$slots.icon || icon}
            <div class:bx--tag__custom-icon={true}>
                <slot name="icon">
                    <svelte:component this={icon}/>
                </slot>
            </div>
        {/if}
        <span><slot/></span>
    </button>
{/if}

<style lang="scss">

  div.bx--tag {
    position: relative;
  }

  .bx--tag {
    margin-left: 0;

    &:disabled, &.bx--tag--disabled {
      pointer-events: none;
    }

    & a:before {
      box-sizing: border-box;
      border-radius: 0.9375rem;
      content: " ";
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
    }
  }

  .bx--tag--interactive {
    &:not(:disabled) .bx--tag__close-icon {
      &:hover {
        background-color: var(--cds-interactive)
      }
    }
  }
</style>