const PASSWORD_REGEX = new RegExp(/^((?=\S*?[A-Z])(?=\S*?[a-z])(?=\S*?[0-9]).{8,})\S$/g);
const EMAIL_REGEX = new RegExp(/^(\w+)(?:\.?\w+)*@\w+(\.\w{2,})$/gu);
export {PASSWORD_REGEX, EMAIL_REGEX};