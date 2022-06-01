import {writable} from "svelte/store";
import type User from "../models/User";
import type Goal from "../models/Goal";

const USER = writable<User>(null);
let GOALS = writable<Goal[]>([]);

export {USER, GOALS}