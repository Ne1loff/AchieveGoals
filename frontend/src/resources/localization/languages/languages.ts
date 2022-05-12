enum Languages {
    RU = 'ru',
    EN = 'en'
}

const getLanguage = (langName: string): Languages => {
    for (let languagesKey in Languages) {
        if (langName.includes(languagesKey.toLowerCase())) return Languages[languagesKey];
    }
    return Languages.EN;
}

export {Languages, getLanguage};