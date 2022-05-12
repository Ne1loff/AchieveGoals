import type {Dictionary} from "./languages/config";
import {en} from "./languages/en";
import {ru} from "./languages/ru";
import {Languages} from "./languages/languages";

const languages = {
    en: en,
    ru: ru
} as const;

const setLanguage = (lang: Languages) => l10n = languages[lang];

let l10n: Dictionary = languages[Languages.EN];

export {l10n, setLanguage};