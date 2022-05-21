package com.example.achieve_goals.exceptions.badRequest

import com.example.achieve_goals.exceptions.abstracts.ApiBadRequestException

class InvalidLoginOrPasswordException : ApiBadRequestException {
    constructor() : super("Invalid login or password")
    constructor(cause: Throwable?) : super("Invalid login or password", cause)
}