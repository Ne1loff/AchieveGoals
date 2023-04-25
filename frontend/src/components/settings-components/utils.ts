interface onSettingsChange {
    accept: () => void;
    reject: () => void;
    disableAcceptBtn: boolean,
    disableRejectBtn: boolean,
}

const isEmptyString = (str: string) => str?.length === 0 ?? true;
const isEqualsStrings = (str1: string, str2: string) => str1 === str2;
const isEqualsNonNullStrings = (str1: string, str2: string) =>
    (str1?.length && str2?.length && str1 === str2) ?? false;

export type {onSettingsChange}
export {isEmptyString, isEqualsStrings, isEqualsNonNullStrings}