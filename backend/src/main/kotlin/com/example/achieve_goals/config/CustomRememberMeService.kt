package com.example.achieve_goals.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class CustomRememberMeService(
    @Value("\${app.secret-key:s3cr3t-k37}") private val secretKey: String,
    userDetailsService: UserDetailsService
) : TokenBasedRememberMeServices(secretKey, userDetailsService) {

    private var rememberMe: Boolean = false

    fun setRememberMe(value: Boolean) {
        rememberMe = value
    }

    override fun rememberMeRequested(request: HttpServletRequest?, parameter: String?): Boolean {
        return rememberMe
    }
}