<script lang="ts">

    import {TagTypeOptional} from "./TagOptions.ts";
    import {createEventDispatcher, SvelteComponent} from "svelte";
    import Close from "carbon-icons-svelte/lib/Close.svelte";

    export let type: TagTypeOptional = undefined;
    export let disabled: boolean = false;
    export let title: string = "Clear filter";
    export let icon: SvelteComponent | undefined = undefined;

    const dispatch = createEventDispatcher();

</script>

<div
        class:bx--tag={true}
        class:bx--tag--disabled={disabled}
        class:bx--tag--filter={true}
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
    <slot props={{ class: 'bx--tag__label' }}>
        <span class:bx--tag__label={true}>{type}</span>
    </slot>
    <button
            type="button"
            class:bx--tag__close-icon={true}
            disabled={disabled}
            title={title}
            on:click|stopPropagation
            on:click|stopPropagation={() => dispatch('close')}
            on:mouseover
            on:mouseenter
            on:mouseleave
    >
        <Close/>
    </button>
</div>

<style lang="scss">

  .bx--tag {
    margin-left: 0;
  }

</style>