package com.example.achieve_goals.config

import com.example.achieve_goals.dto.LoginRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class CustomUsernamePasswordAuthenticationFilter(private val rememberMeService: RememberMeServices) :
    UsernamePasswordAuthenticationFilter() {

    init {
        rememberMeServices = rememberMeService
    }

    private var login: String? = null
    private var password: String? = null

    override fun obtainPassword(request: HttpServletRequest?): String? {
        return password
    }

    override fun obtainUsername(request: HttpServletRequest?): String? {
        return login
    }

    private fun isValidRequest(request: HttpServletRequest?): Boolean {
        val contentType = request!!.getHeader("Content-Type")
        return contentType != null && contentType == "application/json"
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        if (isValidRequest(request)) {
            try {
                val builder = StringBuilder()
                var line: String?
                val reader = request.reader
                while (reader.readLine().also { line = it } != null) {
                    builder.append(line)
                }
                val mapper = jacksonObjectMapper()
                val loginRequest = mapper.readValue(builder.toString(), LoginRequest::class.java)
                login = loginRequest.login
                password = loginRequest.password
                if (rememberMeService is CustomRememberMeService) {
                    rememberMeService.setRememberMe(loginRequest.rememberMe)
                }
            } catch (e: IOException) {
                throw AuthenticationServiceException("Unable to parse credentials!")
            }
        }

        return super.attemptAuthentication(request, response)
    }

}