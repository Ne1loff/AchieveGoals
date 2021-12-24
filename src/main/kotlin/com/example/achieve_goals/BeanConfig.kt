package com.example.achieve_goals

import com.example.achieve_goals.controller.frontend.DevFrontendController
import com.example.achieve_goals.controller.frontend.FrontendController
import com.example.achieve_goals.controller.frontend.ProductionFrontendController
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class BeanConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun frontendController(
        @Value("\${app.devMode:false}") devModeEnabled: Boolean,
        @Value("\${app.frontendAddress}") frontendAddress: String,
        @Value("\${app.frontendPort}") frontendPort: Int
    ): FrontendController {
        return if (devModeEnabled)
            DevFrontendController(
                frontendAddress,
                frontendPort
            ) else ProductionFrontendController()
    }
}