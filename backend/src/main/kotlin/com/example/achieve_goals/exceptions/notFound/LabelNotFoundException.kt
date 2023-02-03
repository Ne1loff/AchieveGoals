package com.example.achieve_goals.exceptions.notFound

import com.example.achieve_goals.exceptions.abstracts.ApiNotfoundException

class LabelNotFoundException : ApiNotfoundException {
    constructor() : super("Label not found")
    constructor(cause: Throwable?) : super("Label not found", cause)
}