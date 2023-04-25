<script lang="ts">
    import {SvelteToast} from '@zerodevx/svelte-toast'
</script>

<SvelteToast options={{ pausable: true }}/>
<div class:wrap={true}>
    <SvelteToast target="new" options={{ reversed: true, pausable: true, intro: { y: 12 } }}/>
</div>

<style>

    .wrap {
        --toastContainerTop: auto;
        --toastContainerRight: calc(50vw + var(--toastWidth) / 2);
        --toastContainerBottom: 1rem;
        --toastContainerLeft: calc(50vw - var(--toastWidth) / 2);
        --toastWidth: 20rem;
        --toastMinHeight: 1.5rem;
    }

</style>