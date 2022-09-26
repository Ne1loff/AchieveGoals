<script lang="ts">

    import {SkeletonPlaceholder} from "carbon-components-svelte";

    export let size: 'small' | 'middle' | 'big' | 'large' = 'middle';
    export let radius: string = '50%'

    export let src: string;
    export let alt: string;

</script>

<div class="avatar"
     class:small={size === 'small'}
     class:medium="{size === 'middle'}"
     class:big={size === 'big'}
     class:large={size === 'large'}
     style="border-right: {radius}"
>
    {#if src}
        <img {src} {alt}/>
    {:else}
        <SkeletonPlaceholder style="height: 100%; width: 100%"/>
    {/if}
</div>

<style>

    .avatar {
        overflow: hidden;
        border-radius: 50%;
        display: flex;

        align-items: center;
        justify-content: center;
    }

    .avatar img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        display: block
    }

    .avatar.small {
        width: calc(1rem * 1.5);
        height: calc(1rem * 1.5);
    }

    .avatar.medium {
        width: calc(1rem * 3);
        height: calc(1rem * 3);
    }

    .avatar.big {
        width: calc(1rem * 5);
        height: calc(1rem * 5);
    }

    .avatar.large {
        width: calc(1rem * 7);
        height: calc(1rem * 7);
    }

</style>