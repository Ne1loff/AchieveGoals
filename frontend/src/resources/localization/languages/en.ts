import type {Dictionary} from "./config";

const en: Dictionary = {
    alreadyHaveAccount: 'Already have an account',
    confirmPass: 'Confirm your password',
    email: 'Email',
    gender(male: boolean): string {
        return male ? 'Man' : 'Woman';
    },
    incorrectLoginOrPass: 'Invalid username or password',
    logInAction: 'Log In',
    rememberMe: 'Remember me',
    logUpAction: 'Register',
    noAccount: 'No account',
    password: 'Password',
    registration: 'Registration',
    selectCountry: 'Select a country',
    username: 'Username',
    login: 'Login',
    emailPatternErrorText: 'Example of mail: example@example.com',
    passwordPatternErrorText: 'The minimum password length is 8 characters.' +
        ' The password must contain at least one digit, uppercase and lowercase letters.',
    requiredErrorText: 'Please fill in this field',
    passwordsNotMatch: 'Passwords don\'t match'
} as const;

export {en}