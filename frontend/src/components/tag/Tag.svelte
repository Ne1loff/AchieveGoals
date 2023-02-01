<script lang="ts">
    import {createEventDispatcher, SvelteComponent} from "svelte";
    import {TagSkeleton} from "carbon-components-svelte";
    import type {TagTypeOptional} from "./TagOptions.ts";
    import InteractiveFilterTag from "./InteractiveFilterTag.svelte";
    import FilterTag from "./FilterTag.svelte";
    import InteractiveTag from "./InteractiveTag.svelte";

    export let type: TagTypeOptional = undefined;
    export let filter: boolean = false;
    export let disabled: boolean = false;
    export let interactive: boolean = false;
    export let href: string;
    export let skeleton: boolean = false;
    export let title: string = "Clear filter";
    export let icon: SvelteComponent | undefined = undefined;

    const dispatch = createEventDispatcher();

</script>

{#if (skeleton)}
    <TagSkeleton
            {...$$restProps}
            on:click
            on:mouseover
            on:mouseenter
            on:mouseleave
    />
{:else if (filter)}
    {#if (interactive)}
        <InteractiveFilterTag {type} {title} {href} {disabled} {icon}>
            <slot/>
        </InteractiveFilterTag>
    {:else}
        <FilterTag {type} {title} {disabled} {icon}>
            <slot/>
        </FilterTag>
    {/if}
{:else if (interactive)}
    <InteractiveTag {type} {href} {disabled} {icon}>
        <slot/>
    </InteractiveTag>
{:else}
    <div
            class:bx--tag={true}
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
    </div>
{/if}

<style lang="scss">

  .bx--tag {
    margin-left: 0;
  }

  .bx--tag--interactive {
    &:not(:disabled) .bx--tag__close-icon {
      &:hover {
        background-color: var(--cds-interactive)
      }
    }
  }

</style>