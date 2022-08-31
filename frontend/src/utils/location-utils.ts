import {ACTIVE_HOME_PAGE} from "../data/storage/storage";

const getProjectPath = () => {
    let path: string;
    ACTIVE_HOME_PAGE.subscribe((value) => path = value)
    return `/app/${path}`;
}

export {getProjectPath}