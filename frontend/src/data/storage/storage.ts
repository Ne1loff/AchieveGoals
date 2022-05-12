import {readable, writable} from "svelte/store";
import type Goal from "../models/Goal";

const USER = writable(null);

const goals: Goal[] = [];
const GOALS = readable(goals);

export {USER, GOALS}