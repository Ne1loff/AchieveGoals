package com.example.achieve_goals.controller

import com.example.achieve_goals.config.RegistrationRequest
import com.example.achieve_goals.dto.ChangePasswordRequestDTO
import com.example.achieve_goals.dto.UserDTO
import com.example.achieve_goals.entities.User
import com.example.achieve_goals.exceptions.ApiNotfoundException
import com.example.achieve_goals.service.MinioService
import com.example.achieve_goals.service.UserService
import org.apache.commons.io.FilenameUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(value = ["/api/"])
class UserController(
    val userService: UserService,
    val minioService: MinioService
) {

    @PostMapping("registration")
    fun registration(@RequestBody newUser: RegistrationRequest): ResponseEntity<Any> {
        return ResponseEntity
            .status(if (userService.saveUser(newUser)) HttpStatus.CREATED else HttpStatus.CONFLICT)
            .build()
    }

    @GetMapping("user")
    fun getCurrentUser(auth: Authentication): UserDTO {
        val principal = auth.principal
        if (principal is User) {
            return userService.getUserById(principal.id)
        }
        throw ApiNotfoundException("User not found!")
    }

    @PutMapping("user/changePassword")
    fun changePassword(
        auth: Authentication,
        @RequestBody passwordDTO: ChangePasswordRequestDTO
    ): ResponseEntity<Any> {
        val principal = auth.principal
        if (principal is User) {
            return if (userService.changeUserPassword(principal.id, passwordDTO.oldPassword, passwordDTO.newPassword))
                ResponseEntity.ok().build()
            else
                ResponseEntity.badRequest().build()
        }
        throw ApiNotfoundException("User not found!")
    }

    @GetMapping("user/avatar")
    fun getUserAvatar(auth: Authentication): ResponseEntity<String> {
        val principal = auth.principal
        if (principal is User) {
            return ResponseEntity.ok()
                .body(userService.getUserAvatarLink(principal.id))
        }
        throw ApiNotfoundException("User not found!")
    }

    @PutMapping("user/avatar")
    fun updateAvatar(@RequestParam("avatar") avatar: MultipartFile, auth: Authentication): ResponseEntity<Any> {
        val principal = auth.principal
        val fileExtension = FilenameUtils.getExtension(avatar.originalFilename)
        if (principal is User) {
            minioService.uploadPhoto(principal.id, avatar.inputStream, fileExtension)
            userService.uploadUserAvatar(principal.id, avatar.inputStream, fileExtension)
            return ResponseEntity.ok().build()
        }
        throw ApiNotfoundException("User not found!")
    }

    @DeleteMapping("user/avatar")
    fun deleteUserAvatar(auth: Authentication): ResponseEntity<Any> {
        val principal = auth.principal
        if (principal is User) {
            if (principal.userPhoto != null) {
                userService.deleteUserPhoto(principal.id)
                minioService.deletePhoto(principal.userPhoto!!.id, principal.userPhoto!!.fileExtension)
                return ResponseEntity.ok().build()
            }
            throw ApiNotfoundException("File not found!")
        }
        throw ApiNotfoundException("User not found!")
    }

    @PutMapping("user")
    fun updateUserInfo(@RequestBody userDTO: UserDTO, auth: Authentication): ResponseEntity<Any> {
        val principal = auth.principal
        if (principal is User) {
            userService.updateUser(userDTO, principal.id)
            return ResponseEntity.ok().build()
        }
        throw ApiNotfoundException("User not found!")
    }
}