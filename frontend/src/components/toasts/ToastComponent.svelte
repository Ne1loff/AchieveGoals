<script lang="ts">

    export let toastId: number;
    export let lowContrast: boolean = false;

</script>

<style>

    :global(:root) {
        --toastWidth: auto;
    }

    :global(.notification-body.bx--toast-notification) {
        margin: 0 !important;
        padding: 0 !important;
        box-shadow: none !important;
    }

    :global(._toastBtn) {
        color: var(--ag-toast-btn) !important;
    }

    :root {
        --own-notification-min-height: 110px;
        --own-notification-min-widht: 250px;
    }

    .notification {
        height: var(--toastHeight);
        width: calc(var(--toastWidth) - 2rem);
        display: flex;
        flex-direction: row;
        align-items: center;

        border-left: 3px solid var(--cds-inverse-support-03, #f1c21b);
    }

    .dialog.notification {
        border-left: none !important;
    }

    .notification-icon {
        width: 15%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: start;
        align-items: center;
    }

    .notification-icon-container {
        margin-top: 1rem;
        font-size: 1.25rem;
    }

    .notification-body .bx--toast-notification__subtitle {
        margin: 0;
    }

    .notification-body {
        display: flex;
        flex-direction: column;

        height: 100%;
        width: 100%;
    }

</style>

<div class="notification {$$props.class}" style={$$props.style}>
    {#if $$slots.icon}
        <div class="notification-icon">
            <div class="notification-icon-container">
                <slot name="icon"/>
            </div>
        </div>
    {/if}
    <div class="notification-body bx--toast-notification { lowContrast ? 'bx--toast-notification--low-contrast' : ''}">
        {#if $$slots.title}
            <h3 class="bx--toast-notification__title">
                <slot name="title"/>
            </h3>
        {/if}
        {#if $$slots.subtitle}
            <div class="bx--toast-notification__subtitle">
                <slot name="subtitle"/>
            </div>
        {/if}
        {#if $$slots.caption}
            <div class="bx--toast-notification__caption">
                <slot name="caption"/>
            </div>
        {/if}
    </div>
</div>