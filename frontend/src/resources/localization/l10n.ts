import type {Dictionary} from "./languages/config";
import {en} from "./languages/en";
import {ru} from "./languages/ru";
import {Languages} from "./languages/languages";

import dayjs from "dayjs";

import 'dayjs/locale/ru';
import 'dayjs/locale/en';

const languages = {
    en: en,
    ru: ru
} as const;

const setLanguage = (lang: Languages) => {
    l10n = languages[lang];
    dayjs.locale(lang === Languages.RU ? 'ru' : 'en');
};

let l10n: Dictionary = languages[Languages.EN];

export {l10n, setLanguage};