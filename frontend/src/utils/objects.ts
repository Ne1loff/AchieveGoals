function getProperty<T extends object, K extends keyof T>(obj: T, key: K): T[K] {
    return obj[key];
}

function setProperty<T extends object, K extends keyof T, V extends T[K]>(obj: T, key: K, value: V): void {
    obj[key] = value;
}

function hasProperty<T extends object>(obj: T, key: PropertyKey): key is keyof T {
    return key in obj;
}

function isTypeOf<T extends object>(obj: any): obj is T {
    return obj !== undefined;
}

export {hasProperty, getProperty, setProperty, isTypeOf};