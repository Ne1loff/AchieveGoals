package com.example.achieve_goals.exceptions.abstracts

open class ApiBadRequestException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}