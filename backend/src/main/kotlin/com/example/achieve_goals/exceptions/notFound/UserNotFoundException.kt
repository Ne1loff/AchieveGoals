package com.example.achieve_goals.exceptions.notFound

import com.example.achieve_goals.exceptions.abstracts.ApiNotfoundException

class UserNotFoundException: ApiNotfoundException {
    constructor() : super("User not found")
    constructor(cause: Throwable?) : super("User not found", cause)
}
