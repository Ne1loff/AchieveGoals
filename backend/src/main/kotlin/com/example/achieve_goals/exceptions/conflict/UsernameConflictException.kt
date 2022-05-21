package com.example.achieve_goals.exceptions.conflict

import com.example.achieve_goals.exceptions.abstracts.ApiConflictException

class UsernameConflictException : ApiConflictException {
    constructor() : super("Username is already in use")
    constructor(cause: Throwable?) : super("Username is already in use", cause)
}