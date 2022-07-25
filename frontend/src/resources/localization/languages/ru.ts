import type {Dictionary} from "./config";

const ru: Dictionary = {
    alreadyHaveAccount: 'Уже есть аккаунт',
    confirmPass: 'Подтвердите пароль',
    email: 'Почта',
    gender(male: boolean): string {
        return male ? 'Мужчина' : 'Женщина';
    },
    incorrectLoginOrPass: 'Неверный логин или пароль',
    rememberMe: "Запомнить меня",
    logInAction: 'Войти',
    logUpAction: 'Зарегистрироваться',
    noAccount: 'Нет аккаунта',
    password: 'Пароль',
    registration: 'Регистрация',
    selectCountry: 'Выберите страну',
    username: 'Имя пользователя',
    login: 'Логин',
    emailPatternErrorText: 'Пример почты: example@example.com',
    passwordPatternErrorText: 'Минимальная длина пароля составляет 8 символов.' +
        ' Пароль должен содержать по крайней мере одну цифру, прописную и строчную букву.',
    requiredErrorText: 'Пожалуйста, заполните это поле',
    passwordsNotMatch: 'Пароли не совпадают'
} as const;

export {ru}