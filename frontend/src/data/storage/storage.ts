import {writable} from "svelte/store";
import type User from "../models/User";
import type Goal from "../models/Goal";
import {HomeUrl} from "../../resources/basicFilter.config";

const USER = writable<User>(null);
let GOALS = writable<Goal[]>([]);
const ACTIVE_HOME_PAGE = writable<HomeUrl>(HomeUrl.GOALS);


export {USER, GOALS, ACTIVE_HOME_PAGE}