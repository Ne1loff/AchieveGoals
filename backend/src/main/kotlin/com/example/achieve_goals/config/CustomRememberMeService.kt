package com.example.achieve_goals.config

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
class CustomRememberMeService(userDetailsService: UserDetailsService) :
    TokenBasedRememberMeServices(UUID.randomUUID().toString(), userDetailsService) {

    private var rememberMe: Boolean = false

    fun setRememberMe(value: Boolean) {
        rememberMe = value
    }

    override fun rememberMeRequested(request: HttpServletRequest?, parameter: String?): Boolean {
        return rememberMe
    }
}