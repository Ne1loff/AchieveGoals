import {writable} from "svelte/store";
import type User from "../models/User";
import type Task from "../models/Task";
import {HomeUrl} from "../../resources/basicFilter.config";
import type UserSettings from "../models/UserSettings";
import type TaskLabel from "../models/TaskLabel";

const USER = writable<User>();
const TASKS = writable<Task[]>([]);
const TASK_LABELS = writable<TaskLabel[]>([]);
const ACTIVE_HOME_PAGE = writable<HomeUrl>(HomeUrl.GOALS);
const USER_SETTINGS = writable<UserSettings>(undefined);


export {USER, USER_SETTINGS, TASKS, TASK_LABELS, ACTIVE_HOME_PAGE}