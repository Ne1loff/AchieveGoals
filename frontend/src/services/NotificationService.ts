import {toast} from "@zerodevx/svelte-toast";
import NotificationComponent from "../components/NotificationComponent.svelte";
import {NotificationType} from "../data/enums/_enums";
import type ApiError from "../data/api/ApiError";

class ErrorMessage {
    title?: string;
    subtitle?: string;
    caption?: string;
    duration?: number;

    fromApiError(apiError: ApiError, duration?: number): ErrorMessage {
        this.title = `Error: (HTTP ${apiError.status}: ${apiError.error})`;
        this.subtitle = apiError.message;
        this.caption = apiError.timestamp.toString();
        this.duration = duration;
        return this;
    }
}

export {ErrorMessage};

export default class NotificationService {

    private static showNotification(
        notificationType: NotificationType,
        title?: string,
        subtitle?: string,
        caption?: string,
        duration?: number
    ) {
        toast.push({
            component: {
                src: NotificationComponent,
                props: {
                    notificationType: notificationType,
                    title: title,
                    subtitle: subtitle,
                    caption: caption
                },
                sendIdTo: 'toastId'
            },
            initial: 1,
            duration: duration ?? 10000,
            theme: {
                '--toastPadding': '0',
                '--toastMsgPadding': '0',
                '--toastBackground': 'var(--cds-notification-background-info, #393939)'
            }
        })
    }

    closeAll() {
        toast.pop(0);
    }

    info(title?: string, subtitle?: string, caption?: string, duration?: number) {
        NotificationService.showNotification(NotificationType.INFO, title, subtitle, caption, duration);
    }

    success(title?: string, subtitle?: string, caption?: string, duration?: number) {
        NotificationService.showNotification(NotificationType.SUCCESS, title, subtitle, caption, duration);
    }

    warning(title?: string, subtitle?: string, caption?: string, duration?: number) {
        NotificationService.showNotification(NotificationType.WARNING, title, subtitle, caption, duration);
    }

    error(title?: string, subtitle?: string, caption?: string, duration?: number) {
        NotificationService.showNotification(NotificationType.ERROR, title, subtitle, caption, duration);
    }

    errorFromErrorMessage(errorMessage: ErrorMessage) {
        this.error(errorMessage.title, errorMessage.subtitle, errorMessage.caption, errorMessage.duration)
    }

}