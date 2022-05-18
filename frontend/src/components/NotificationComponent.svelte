<script lang="ts">
    import {NotificationType} from "../data/enums/_enums";
    import Icon from "@iconify/svelte";
    import {ICONS} from "../resources/constants";
    import dayjs from "dayjs";

    export let toastId: number;

    export let notificationType: NotificationType = NotificationType.ERROR;
    export let title: string = "Error";
    export let subtitle: string = "Something went wrong!";
    export let caption: string = dayjs().format('DD MMMM HH:mm');

    const icons = ICONS;

</script>

<style>

    :global(:root) {
        --toastWidth: auto;
    }

    :global(._toastBtn) {
        color: var(--cds-text-01, #ffffff)
    }

    :root {
        --own-notification-min-height: 110px;
        --own-notification-min-widht: 250px;
    }

    .notification {
        min-height: var(--own-notification-min-height);
        min-width: var(--own-notification-min-widht);
        display: flex;
        flex-direction: row;
        align-items: center;

        color: var(--cds-text-01, #ffffff);
        border-left: 3px solid var(--cds-inverse-support-03, #f1c21b);
        background: var(--cds-notification-background-info, #393939)
    }

    .notification-icon {
        width: 15%;
        min-height: var(--own-notification-min-height);
        display: flex;
        flex-direction: column;
        justify-content: start;
        align-items: center;
    }

    .notification-icon-container {
        margin-top: 1rem;
        font-size: 1.25rem;
    }

    .notification-body {
        flex-grow: 1;
        height: 100%;
        color: var(--cds-text-primary)
    }

</style>

<div class="notification" style="border-left-color: {icons[notificationType].color}">
    <div class="notification-icon">
        <div class="notification-icon-container">
            <Icon icon={icons[notificationType].name} color={icons[notificationType].color}/>
        </div>
    </div>
    <div class="notification-body bx--toast-notification--low-contrast ">
        <h3 class="bx--toast-notification__title">{title}</h3>
        <div class="bx--toast-notification__subtitle">{subtitle}</div>
        <div class="bx--toast-notification__caption">{caption}</div>
    </div>
</div>