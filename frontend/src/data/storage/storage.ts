import {writable} from "svelte/store";
import type User from "../models/User";
import type Goal from "../models/Goal";
import {HomeUrl} from "../../resources/basicFilter.config";
import type UserSettings from "../models/UserSettings";

const USER = writable<User>();
const GOALS = writable<Goal[]>([]);
const ACTIVE_HOME_PAGE = writable<HomeUrl>(HomeUrl.GOALS);
const USER_SETTINGS = writable<UserSettings>(undefined);


export {USER, USER_SETTINGS, GOALS, ACTIVE_HOME_PAGE}