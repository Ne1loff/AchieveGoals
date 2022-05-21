package com.example.achieve_goals.exceptions.badRequest

import com.example.achieve_goals.exceptions.abstracts.ApiBadRequestException

class IncorrectDataException : ApiBadRequestException {
    constructor() : super("Incorrect data")
    constructor(cause: Throwable?) : super("Incorrect data", cause)
}