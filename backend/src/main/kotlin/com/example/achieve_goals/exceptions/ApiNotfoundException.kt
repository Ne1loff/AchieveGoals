package com.example.achieve_goals.exceptions

class ApiNotfoundException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}