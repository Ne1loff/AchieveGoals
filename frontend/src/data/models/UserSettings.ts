import type {Languages} from "../../resources/localization/languages/languages";

type SupportedLanguages = typeof Languages;
type UserTheme = {
    autoDarkTheme: boolean;
    theme: string;
}

export default class UserSettings {
    language?: SupportedLanguages;
    startedPage?: string;
    timeFormat?: string;
    dateFormat?: string;
    userTheme?: UserTheme;
}

export type {SupportedLanguages}