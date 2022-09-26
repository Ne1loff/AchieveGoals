import {NotificationType} from "../data/enums/_enums";
import {SettingsSubUrl, SettingsUrl} from "./basicFilter.config";
import {hrefs} from "./config";

const PRIORITIES = [
    {
        priority: 1, icon: "bi:flag-fill", color: "#de4c4a"
    },
    {
        priority: 2, icon: "bi:flag-fill", color: "#f49c18"
    },
    {
        priority: 3, icon: "bi:flag-fill", color: "#3077e1"
    },
    {
        priority: 4, icon: "bi:flag-fill", color: "#808080"
    },
];

const PRIORITY_COLORS = {
    1: {
        icon: '#de4c4a',
        background: 'rgba(222,76,74,0.125)'
    },
    2: {
        icon: '#f49c18',
        background: 'rgba(244,156,24,0.125)'
    },
    3: {
        icon: '#3077e1',
        background: 'rgba(48,119,225,0.125)'
    },
    4: {
        icon: '#808080',
        background: 'rgba(128,128,128,0.125)'
    }
} as const;

const THEMES = [
    {value: "white", text: "White"},
    {value: "g10", text: "Gray 10"},
    {value: "g80", text: "Gray 80"},
    {value: "g90", text: "Gray 90"},
    {value: "g100", text: "Gray 100"}
] as const;

const ICONS = {
    [NotificationType.ERROR]: {name: 'carbon:error-filled', color: 'var(--cds-support-01, #fa4d56)'},
    [NotificationType.SUCCESS]: {name: 'carbon:checkmark-filled', color: 'var(--cds-support-02, #42be65)'},
    [NotificationType.WARNING]: {name: 'carbon:warning-filled', color: 'var(--cds-support-03, #f1c21b)'},
    [NotificationType.INFO]: {name: 'carbon:information-filled', color: 'var(--cds-support-04, #4589ff)'}

} as const;

const SETTING_BUTTONS = [
    {
        obj: SettingsUrl.ACCOUNT,
        title: 'Аккаунт',
        icon: 'ic:outline-account-circle',
        href: hrefs.settings(SettingsUrl.ACCOUNT)
    },
    {
        obj: SettingsUrl.GENERAL,
        title: 'Основные',
        icon: 'carbon:settings',
        href: hrefs.settings(SettingsUrl.GENERAL)
    },
    {
        obj: SettingsUrl.THEME,
        title: 'Тема',
        icon: 'carbon:color-palette',
        href: hrefs.settings(SettingsUrl.THEME)
    },
    {
        obj: SettingsUrl.PRODUCTIVITY,
        title: 'Продуктивность',
        icon: 'carbon:growth',
        href: hrefs.settings(SettingsUrl.PRODUCTIVITY)
    },
    {
        obj: SettingsUrl.REMINDERS,
        title: 'Напоминания',
        icon: 'carbon:alarm',
        href: hrefs.settings(SettingsUrl.REMINDERS)
    },
    {
        obj: SettingsUrl.NOTIFICATIONS,
        title: 'Уведомления',
        icon: 'akar-icons:bell',
        href: hrefs.settings(SettingsUrl.NOTIFICATIONS)
    }
]

const SETTINGS_SUB_TABS = [
    {
        obj: SettingsSubUrl.EMAIL,
        title: 'Изменить Email-адрес',
        icon: 'akar-icons:bell',
        href: `${hrefs.settings(SettingsUrl.NOTIFICATIONS)}/${SettingsSubUrl.EMAIL}`
    },
    {
        obj: SettingsSubUrl.PASSWORD,
        title: 'Изменить пароль',
        icon: 'akar-icons:bell',
        href: `${hrefs.settings(SettingsUrl.NOTIFICATIONS)}/${SettingsSubUrl.PASSWORD}`
    }
]

export {PRIORITIES, PRIORITY_COLORS, THEMES, ICONS, SETTING_BUTTONS, SETTINGS_SUB_TABS}