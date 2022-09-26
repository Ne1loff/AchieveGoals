const alpha = "abcdefghijklmnopqrstuvwxyz";
const upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
const upper_punct = "~`!@#$%^&*()-_+=";
const digits = "1234567890";

const totalChars = 0x7f - 0x20;
const alphaChars = alpha.length;
const upperChars = upper.length;
const upper_punctChars = upper_punct.length;
const digitChars = digits.length;
const otherChars = totalChars - (alphaChars + upperChars + upper_punctChars + digitChars);

const calculateBits = (password: string) => {

    if (password.length === 0) {
        return 0;
    }

    let fAlpha = false;
    let fUpper = false;
    let fUpperPunct = false;
    let fDigit = false;
    let fOther = false;
    let charset = 0;

    for (let i = 0; i < password.length && i < 16; i++) {
        const char = password.charAt(i);

        if (!fAlpha && alpha.indexOf(char) != -1)
            fAlpha = true;
        else if (!fUpper && upper.indexOf(char) != -1)
            fUpper = true;
        else if (!fDigit && digits.indexOf(char) != -1)
            fDigit = true;
        else if (upper_punct.indexOf(char) != -1)
            fUpperPunct = true;
        else
            fOther = true;

        if (fAlpha && fUpper && fDigit && fUpperPunct && fOther) break;
    }


    if (fAlpha)
        charset += alphaChars;
    if (fUpper)
        charset += upperChars;
    if (fDigit)
        charset += digitChars;
    if (fUpperPunct)
        charset += upper_punctChars;
    if (fOther)
        charset += otherChars;

    const bits = Math.log(charset) * (password.length / Math.log(2));

    return Math.floor(bits);
}

const getPasswordDifficult = (password: string) => {
    const bits = calculateBits(password);
    if (bits >= 128) {
        return 4;
    } else if (bits < 128 && bits >= 64) {
        return 3;
    } else if (bits < 64 && bits >= 56) {
        return 2;
    } else if (bits < 56 && bits >= 28) {
        return 1;
    } else {
        return 0;
    }
}

export {getPasswordDifficult};