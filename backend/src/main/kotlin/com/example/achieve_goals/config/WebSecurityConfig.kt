package com.example.achieve_goals.config

import com.example.achieve_goals.entities.Roles
import com.example.achieve_goals.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val rememberMeServices: RememberMeServices,
    @Value("\${app.devMode:false}") private val devMode: Boolean
) : WebSecurityConfigurerAdapter() {

    private val csrfPolicy = "default-src 'self'; " +
            "style-src 'self' https://fonts.googleapis.com; " +
            "font-src 'self' https://fonts.gstatic.com"

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder)
        super.configure(auth)
    }

    override fun configure(http: HttpSecurity) {

        if (!devMode) http.headers { headers: HeadersConfigurer<HttpSecurity?> ->
            headers.contentSecurityPolicy(csrfPolicy)
        }

        http
            .addFilterAt(
                customUsernamePasswordAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .authorizeRequests { authorize ->
                authorize
                    .antMatchers("/api/login").permitAll()
                    .antMatchers("/api/registration").permitAll()
                    .antMatchers("/api/countries/").permitAll()
                    .antMatchers("/api/docs/**").permitAll()
                    .antMatchers("/api/admin/**").hasAuthority(Roles.ADMIN)
                    .antMatchers("/api/**").hasAnyAuthority(Roles.ADMIN, Roles.USER)
                    .and().rememberMe().rememberMeServices(rememberMeServices)

            }
            .logout { logout: LogoutConfigurer<HttpSecurity> ->
                logout
                    .deleteCookies("JSESSIONID")
                    .logoutRequestMatcher { request: HttpServletRequest ->
                        request.requestURI == "/api/logout" && request.method == HttpMethod.POST.name
                    }
                    .logoutSuccessHandler { _: HttpServletRequest?,
                                            response: HttpServletResponse,
                                            _: Authentication? ->
                        response.status = HttpStatus.OK.value()
                        response.writer.print(jsonHttpOkResponse())
                    }
            }
            .formLogin().disable()
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint { _: HttpServletRequest?,
                                        response: HttpServletResponse,
                                        _: AuthenticationException? ->
                response.status = HttpStatus.FORBIDDEN.value()
                response.sendError(response.status, "You don't have permission!")
            }
    }

    fun customUsernamePasswordAuthenticationFilter(): CustomUsernamePasswordAuthenticationFilter {
        val filter = CustomUsernamePasswordAuthenticationFilter(rememberMeServices)
        filter.setAuthenticationManager(authenticationManagerBean())
        filter.setAuthenticationFailureHandler { _: HttpServletRequest,
                                                 response: HttpServletResponse,
                                                 _: AuthenticationException ->
            response.status = HttpStatus.BAD_REQUEST.value()
            response.sendError(response.status, "Invalid login or password")
        }
        filter.setAuthenticationSuccessHandler { _: HttpServletRequest,
                                                 response: HttpServletResponse,
                                                 _: Authentication? ->
            response.status = HttpStatus.OK.value()
            response.writer.print(jsonHttpOkResponse())
        }
        filter.setFilterProcessesUrl("/api/login")
        filter.setPostOnly(true)
        return filter
    }

    fun jsonHttpOkResponse(): String {
        return "{ \"status\": \"${HttpStatus.OK.value()}\", \"message\": \"${HttpStatus.OK.name}\" }"
    }
}