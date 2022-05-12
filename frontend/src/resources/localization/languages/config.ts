type Dictionary = {
    login: string,
    password: string,
    logInAction: string,
    noAccount: string,
    registration: string,
    incorrectLoginOrPass: string,
    username: string,
    email: string,
    selectCountry: string,
    confirmPass: string,
    gender: (male: boolean) => string,
    logUpAction: string,
    alreadyHaveAccount: string,
    requiredErrorText: string,
    emailPatternErrorText: string,
    passwordPatternErrorText: string,
    passwordsNotMatch: string
}

export type {Dictionary}