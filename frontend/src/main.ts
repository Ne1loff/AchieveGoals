import "carbon-components-svelte/css/all.css";
import "flatpickr/dist/flatpickr.css"

import App from './App.svelte';
import {setLanguage} from "./resources/localization/l10n";
import {getLanguage} from "./resources/localization/languages/languages";

setLanguage(getLanguage(navigator.language));

const app = new App({
    target: document.body,
});

export default app;