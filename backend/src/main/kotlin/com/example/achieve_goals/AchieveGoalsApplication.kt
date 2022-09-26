package com.example.achieve_goals

import com.example.achieve_goals.controller.frontend.DevFrontendController
import com.example.achieve_goals.controller.frontend.ProductionFrontendController
import com.example.achieve_goals.entities.User
import com.example.achieve_goals.repository.UserRepository
import com.example.achieve_goals.service.UserService
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import java.util.*


@ComponentScan(
    excludeFilters = [ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = [
            DevFrontendController::class,
            ProductionFrontendController::class
        ]
    )]
)
@OpenAPIDefinition
@SpringBootApplication
class AchieveGoalsApplication

fun main(args: Array<String>) {
    val context = runApplication<AchieveGoalsApplication>(*args)
    val userRepo = context.getBean(UserRepository::class.java)
    if (!userRepo.existsUserByAdminIsTrue()) {
        val userService = context.getBean(UserService::class.java)
        val password = UUID.randomUUID().toString().replace("-", "")
        val adminAccount = User()
        adminAccount.usernameSalt = "admin"
        adminAccount.passwordHash = password
        adminAccount.admin = true
        val user = userService.saveAdmin(adminAccount)
        userService.setupUserSettings(user)

        val logger: Logger = LoggerFactory.getLogger(AchieveGoalsApplication::class.java)
        logger.info("Admin account created with password: $password")
    }
}
