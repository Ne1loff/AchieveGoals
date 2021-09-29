package com.example.achieve_goals.controller

import com.example.achieve_goals.config.RegistrationRequest
import com.example.achieve_goals.dto.UserDTO
import com.example.achieve_goals.entities.User
import com.example.achieve_goals.exceptions.ApiNotfoundException
import com.example.achieve_goals.service.UserService
import org.apache.commons.io.FilenameUtils
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(value = ["/api/"])
class UserController(
    val userService: UserService
) {

    @PostMapping("registration")
    fun registration(@RequestBody newUser: RegistrationRequest): HttpStatus {
        return if (userService.saveUser(newUser)) HttpStatus.CREATED else HttpStatus.CONFLICT
    }

    @GetMapping("user")
    fun getCurrentUser(auth: Authentication): UserDTO {
        val principal = auth.principal
        if (principal is User) {
            return userService.getUserById(principal.id)
        }
        throw ApiNotfoundException("User not found!")
    }

    @GetMapping("user/avatar")
    fun getUserAvatar(auth: Authentication): String {
        val principal = auth.principal
        if (principal is User) {
            return userService.getUserAvatarLink(principal.id)
        }
        throw ApiNotfoundException("User not found!")
    }

    @PutMapping("user/avatar")
    fun updateAvatar(@RequestParam("avatar") avatar: MultipartFile, auth: Authentication): HttpStatus {
        val principal = auth.principal
        val fileExtension = FilenameUtils.getExtension(avatar.originalFilename)
        if (principal is User) {
            userService.uploadUserAvatar(principal.id, avatar.inputStream, fileExtension)
        }
        return HttpStatus.OK
    }

    @DeleteMapping("user/avatar")
    fun deleteUserAvatar(auth: Authentication): HttpStatus {
        val principal = auth.principal
        if (principal is User) {
            userService.deleteUserPhoto(principal.id)
            return HttpStatus.OK
        }
        throw ApiNotfoundException("User not found!")
    }

    @PutMapping("user")
    fun updateUserInfo(@RequestBody userDTO: UserDTO, auth: Authentication): HttpStatus {
        val principal = auth.principal
        if (principal is User) {
            userService.updateUser(userDTO, principal.id)
            return HttpStatus.OK
        }
        throw ApiNotfoundException("User not found!")
    }
}