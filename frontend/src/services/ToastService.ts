import {toast} from "@zerodevx/svelte-toast";
import NotificationComponent from "../components/toasts/NotificationComponent.svelte";
import {NotificationType} from "../data/enums/_enums";
import type ApiError from "../data/api/ApiError";
import DialogComponent from "../components/toasts/DialogComponent.svelte";
import type {Action} from "../components/utils";

class ErrorMessage {
    title?: string;
    subtitle?: string;
    caption?: string;
    duration?: number;

    fromApiError(apiError: ApiError, duration?: number): ErrorMessage {
        this.title = `Error: (HTTP ${apiError.status}: ${apiError.error})`;
        this.subtitle = apiError.message;
        this.caption = apiError.timestamp?.toString();
        this.duration = duration;
        return this;
    }
}

export {ErrorMessage};

export default class ToastService {

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
                    caption: caption,
                    lowContrast: true
                },
                sendIdTo: 'toastId'
            },
            initial: 1,
            duration: duration ?? 10000,
            theme: {
                '--toastWidth': '20rem',
                '--toastHeight': '6rem',
                '--toastPadding': '0',
                '--toastMsgPadding': '0',
                '--toastBackground': 'var(--cds-notification-background-info, #393939)',
                '--ag-toast-btn': 'var(--cds-icon-01)'
            }
        })
    }

    private static showDialog(
        onAccept: () => void,
        onReject: () => void,
        text?: string,
        duration?: number,
    ) {
        toast.push({
            target: 'new',
            component: {
                src: DialogComponent,
                props: {
                    onReject: onReject,
                    text: text,
                },
                sendIdTo: 'toastId'
            },
            initial: 1,
            duration: duration ?? 10000,
            theme: {
                '--toastPadding': '0',
                '--toastMsgPadding': '0.25rem',
                '--toastBackground': 'var(--cds-inverse-02, #393939)',
                '--ag-toast-btn': 'var(--cds-inverse-01)'
            },
            onpop: onAccept
        })
    }

    close(id: number) {
        toast.pop(id);
    }

    closeAll() {
        toast.pop(0);
    }

    info(title?: string, subtitle?: string, caption?: string, duration?: number) {
        ToastService.showNotification(NotificationType.INFO, title, subtitle, caption, duration);
    }

    success(title?: string, subtitle?: string, caption?: string, duration?: number) {
        ToastService.showNotification(NotificationType.SUCCESS, title, subtitle, caption, duration);
    }

    warning(title?: string, subtitle?: string, caption?: string, duration?: number) {
        ToastService.showNotification(NotificationType.WARNING, title, subtitle, caption, duration);
    }

    error(title?: string, subtitle?: string, caption?: string, duration?: number) {
        ToastService.showNotification(NotificationType.ERROR, title, subtitle, caption, duration);
    }

    errorFromErrorMessage(errorMessage: ErrorMessage) {
        this.error(errorMessage.title, errorMessage.subtitle, errorMessage.caption, errorMessage.duration)
    }

    dialog(text: string, {accept, reject}: Action, duration?: number) {
        ToastService.showDialog(accept, reject, text, duration);
    }

}