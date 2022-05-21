package com.example.achieve_goals.exceptions.conflict

import com.example.achieve_goals.exceptions.abstracts.ApiConflictException

class EmailConflictException : ApiConflictException {
    constructor() : super("Email is already in use")
    constructor(cause: Throwable?) : super("Email is already in use", cause)
}