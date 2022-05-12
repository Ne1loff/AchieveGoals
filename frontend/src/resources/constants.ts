import {NotificationType} from "../data/enums/_enums";

const FILTERS = [
    {
        priority: 1, active: false, icon: "bi:flag-fill", color: "#de4c4a"
    },
    {
        priority: 2, active: false, icon: "bi:flag-fill", color: "#f49c18"
    },
    {
        priority: 3, active: false, icon: "bi:flag-fill", color: "#3077e1"
    },
    {
        priority: 4, active: false, icon: "bi:flag", color: ""
    },
];

const PRIORITY_COLORS = {
    1: {
        icon: '#de4c4a',
        background: '#faeceb'
    },
    2: {
        icon: '#f49c18',
        background: '#fdf3e6'
    },
    3: {
        icon: '#3077e1',
        background: '#e9f1fc'
    },
    4: {
        icon: '#808080',
        background: 'none'
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
    [NotificationType.ERROR]: {name: 'carbon:error-filled', color: 'var(--cds-inverse-support-01, #fa4d56)'},
    [NotificationType.SUCCESS]: {name: 'carbon:checkmark-filled', color: 'var(--cds-inverse-support-02, #42be65)'},
    [NotificationType.WARNING]: {name: 'carbon:warning-filled', color: 'var(--cds-inverse-support-03, #f1c21b)'},
    [NotificationType.INFO]: {name: 'carbon:information-filled', color: 'var(--cds-inverse-support-04, #4589ff)'}

} as const;

export {FILTERS, PRIORITY_COLORS, THEMES, ICONS}