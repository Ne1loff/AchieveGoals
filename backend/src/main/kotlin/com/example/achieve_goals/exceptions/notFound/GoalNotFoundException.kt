package com.example.achieve_goals.exceptions.notFound

import com.example.achieve_goals.exceptions.abstracts.ApiNotfoundException

class GoalNotFoundException : ApiNotfoundException {
    constructor() : super("Goal not found")
    constructor(cause: Throwable?) : super("Goal not found", cause)
}