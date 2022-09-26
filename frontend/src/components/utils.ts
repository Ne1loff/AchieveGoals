interface styleObject {
    [k: string]: string
}

function isStyleObject(obj: any): obj is styleObject {
    return obj !== undefined && !Array.isArray(obj) && obj !== null;
}

const getStyle = (style: string | styleObject): string => {
    if (!style) return '';

    if (isStyleObject(style)) {
        let keys = Object.keys(style);
        return keys.map((it) => `${it}: ${style[it]};`).join('');
    } else {
        return style;
    }
}

const getClasses = (classes: string[] | string): string => {
    if (!classes) return '';

    if (Array.isArray(classes)) {
        return classes.join(' ');
    } else {
        return classes;
    }
}

interface Task {
    accept: () => void;
    reject: () => void;
}

export {getClasses, getStyle};
export type {Task};