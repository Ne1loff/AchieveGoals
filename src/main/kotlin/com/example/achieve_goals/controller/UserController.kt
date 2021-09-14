package com.example.achieve_goals.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/users/"])
class UserController {

    @GetMapping
    fun hello() : String {
        return "Hello World"
    }
}