import type {Dictionary} from "./languages/config";
import {en} from "./languages/en";
import {ru} from "./languages/ru";
import {CurrentLanguage, Languages} from "./languages/languages";

import dayjs from "dayjs";

import 'dayjs/locale/ru';
import 'dayjs/locale/en';
import {hasProperty} from "../../utils/objects";

const languages = {
    [Languages.en]: en,
    [Languages.ru]: ru
};

const setLanguage = (lang: string) => {
    if (hasProperty(languages, lang)) {
        l10n = languages[lang];

        CurrentLanguage.set(lang);
        dayjs.locale(lang);
        document.documentElement.setAttribute('lang', lang);
    }
}

let l10n: Dictionary = languages[Languages.en];

export {l10n, setLanguage};