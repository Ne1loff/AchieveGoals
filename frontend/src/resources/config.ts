import type {HomeUrl, SettingsUrl} from "./basicFilter.config";
import {getProjectPath} from "../utils/location-utils";

const base = 'http://localhost:8080';

const hrefs = {
    login: '/auth/login',
    registration: '/auth/registration',
    home: '/app/goals',
    basicFilter: (filter: HomeUrl) => `/app/${filter}`,
    task: (id: number | string) => `${getProjectPath()}/task/${id}`,
    label: (id: number) => `/app/label/${id}`,
    teams: '/user/teams',
    settings: (tab: SettingsUrl) => `/app/settings/${tab}`,
    mobile: '/application',
    premium: '/premium'
}

export {base, hrefs};