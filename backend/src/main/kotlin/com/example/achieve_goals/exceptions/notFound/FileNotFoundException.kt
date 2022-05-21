package com.example.achieve_goals.exceptions.notFound

import com.example.achieve_goals.exceptions.abstracts.ApiNotfoundException

class FileNotFoundException : ApiNotfoundException {
    constructor() : super("File not found")
    constructor(cause: Throwable?) : super("File not found", cause)
}