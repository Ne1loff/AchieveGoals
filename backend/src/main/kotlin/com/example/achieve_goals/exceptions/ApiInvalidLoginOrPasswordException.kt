package com.example.achieve_goals.exceptions

class ApiInvalidLoginOrPasswordException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}