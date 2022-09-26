import {hasProperty} from "../../../utils/objects";
import {writable} from "svelte/store";

enum Languages {
    ru = 'ru',
    en = 'en'
}

type Language = {
    value: Languages,
    name: string
}

const CurrentLanguage = writable<Languages>(Languages.en);

const LanguagesNames: Language[] = [
    {value: Languages.en, name: "English"},
    {value: Languages.ru, name: "Русский"}
];

const getLanguage = (langName: string): string =>
    hasProperty(Languages, langName) ? Languages[langName] : Languages.en;

export {Languages, CurrentLanguage, LanguagesNames, getLanguage};