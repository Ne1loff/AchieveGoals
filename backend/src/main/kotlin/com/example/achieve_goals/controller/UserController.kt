package com.example.achieve_goals.controller

import com.example.achieve_goals.dto.ChangePasswordRequest
import com.example.achieve_goals.dto.RegistrationRequest
import com.example.achieve_goals.dto.UserDTO
import com.example.achieve_goals.exceptions.notFound.FileNotFoundException
import com.example.achieve_goals.service.MinioService
import com.example.achieve_goals.service.UserService
import com.example.achieve_goals.utils.getUserEntity
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
        val user = userService.saveUser(newUser)
        userService.setupUserSettings(user)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @GetMapping("user")
    fun getCurrentUser(auth: Authentication): UserDTO {
        val user = auth.getUserEntity()
        return userService.getUserById(user.id)
    }

    @PutMapping("user/changePassword")
    fun changePassword(
        auth: Authentication,
        @RequestBody passwordDTO: ChangePasswordRequest
    ): ResponseEntity<Any> {
        val user = auth.getUserEntity()
        return if (userService.changeUserPassword(user.id, passwordDTO.oldPassword, passwordDTO.newPassword))
            ResponseEntity.ok().build()
        else
            ResponseEntity.badRequest().build()
    }

    @GetMapping("user/avatar")
    fun getUserAvatar(auth: Authentication): ResponseEntity<String> {
        val user = auth.getUserEntity()
        return ResponseEntity.ok()
            .body(userService.getUserAvatarLink(user.id))
    }

    @PutMapping("user/avatar")
    fun updateAvatar(@RequestParam("avatar") avatar: MultipartFile, auth: Authentication): ResponseEntity<Any> {
        val user = auth.getUserEntity()
        val fileExtension = FilenameUtils.getExtension(avatar.originalFilename)

        minioService.uploadPhoto(user.id, avatar.inputStream, fileExtension)
        userService.uploadUserAvatar(user.id, avatar.inputStream, fileExtension)

        return ResponseEntity.ok().build()
    }

    @DeleteMapping("user/avatar")
    fun deleteUserAvatar(auth: Authentication): ResponseEntity<Any> {
        val user = auth.getUserEntity()

        user.userPhoto?.let {
            userService.deleteUserPhoto(user.id)
            minioService.deletePhoto(it.id, it.fileExtension)
            return ResponseEntity.ok().build()
        }

        throw FileNotFoundException()
    }

    @PutMapping("user")
    fun updateUserInfo(@RequestBody userDTO: UserDTO, auth: Authentication): ResponseEntity<Any> {
        val user = auth.getUserEntity()
        userService.updateUser(userDTO, user.id)

        return ResponseEntity.ok().build()
    }
}