<script lang="ts">
    import ToastComponent from "./ToastComponent.svelte";
    import Button from "../button/Button.svelte";
    import ServiceFactory from "../../services/ServiceFactory";

    export let text: string;
    export let onReject: () => void;
    export let toastId: number;

    const reject = () => {
        onReject();
        ServiceFactory.INSTANCE.toastService.close(toastId);
    }

</script>

<ToastComponent
        class="dialog"

>
    <svelte:fragment slot="subtitle">
        <div class:container={true}>
            <div class="message">
                {text}
            </div>
            <Button size="small" on:click={reject}>
                Отмена
                <!--TODO: l10n-->
            </Button>
        </div>
    </svelte:fragment>
</ToastComponent>

<style>

    :global(.dialog .bx--toast-notification__subtitle) {
        margin: 0;
        padding: .25rem;
    }

    .container {
        display: flex;
        flex-direction: row;
        flex-wrap: nowrap;

        gap: .5rem;
        align-items: center;
        justify-content: space-around;
    }

</style>