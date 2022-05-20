package com.example.achieve_goals.service

import com.example.achieve_goals.config.RegistrationRequest
import com.example.achieve_goals.dto.UserDTO
import com.example.achieve_goals.entities.User
import com.example.achieve_goals.entities.UserAvatar
import com.example.achieve_goals.exceptions.ApiBadRequestException
import com.example.achieve_goals.exceptions.ApiInvalidLoginOrPasswordException
import com.example.achieve_goals.mapper.UserMapperImpl
import com.example.achieve_goals.repository.LocalityRepository
import com.example.achieve_goals.repository.UserAvatarRepository
import com.example.achieve_goals.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.io.InputStream
import javax.transaction.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userAvatarRepository: UserAvatarRepository,
    private val minioService: MinioService,
    localityRepository: LocalityRepository,
    private val passwordEncoder: PasswordEncoder,
    private val mapper: UserMapperImpl
) : UserDetailsService {

    private val localityNames = localityRepository.findAll().associate { it.id to it.name }

    private val maleDefaultAvatar: String = "male_default_avatar.png"
    private val femaleDefaultAvatar: String = "female_default_avatar.png"

    override fun loadUserByUsername(login: String): UserDetails {
        return userRepository.findUserByUsernameSalt(login) ?: userRepository.findUserByEmail(login)
        ?: throw ApiInvalidLoginOrPasswordException("Invalid login or password")
    }

    fun getAllUsers(): MutableList<UserDTO> {
        return userRepository.findAll()
            .map { user ->
                mapper.dtoFromUser(user, localityNames.getOrDefault(user.locality, ""), getUserAvatarLink(user.id))
            }.toMutableList()
    }

    fun getUserById(id: Long): UserDTO {
        val user = userRepository.findUserById(id)
        val avatarLink = getUserAvatarLink(id)
        return mapper.dtoFromUser(user, localityNames.getOrDefault(user.locality, ""), avatarLink)
    }

    fun getUserAvatarLink(id: Long): String {
        val user = userRepository.findUserById(id)

        val filename = if (user.male) maleDefaultAvatar else femaleDefaultAvatar

        if (user.userPhoto == null) return minioService.getPhoto(filename)
        return minioService.getPhoto(user.id, user.userPhoto!!.fileExtension)
    }

    @Transactional
    fun uploadUserAvatar(id: Long, file: InputStream, fileExtension: String) {
        val user = userRepository.findUserById(id)
        var userAvatar = user.userPhoto

        if (userAvatar != null) userAvatar.fileExtension = fileExtension
        else userAvatar = UserAvatar(id, fileExtension)

        userAvatarRepository.save(userAvatar)
    }

    @Transactional
    fun deleteUserPhoto(id: Long) {
        userAvatarRepository.deleteUserAvatarById(id)
    }


    fun saveUser(newUser: RegistrationRequest): Boolean {
        if (userRepository.existsUserByEmail(newUser.email))
            throw ApiBadRequestException("A user with such an email already exists")
        val user = mapper.userFromRegistrationRequest(newUser)
        user.passwordHash = passwordEncoder.encode(user.password)
        userRepository.save(user)
        return true
    }

    fun saveAdmin(user: User) {
        user.passwordHash = passwordEncoder.encode(user.password)
        userRepository.save(user)
    }

    @Transactional
    fun updateUser(userDTO: UserDTO, id: Long) {
        val user = userRepository.findUserById(id)

        if (userDTO.email != null)
            if (userDTO.email != user.email)
                if (userRepository.existsUserByEmail(userDTO.email))
                    throw ApiBadRequestException("User with this email already exist!")
        if (userDTO.username != null)
            if (userDTO.username != user.usernameSalt)
                if (userRepository.existsUserByUsernameSalt(userDTO.username))
                    throw ApiBadRequestException("User with this username already exist!")

        user.usernameSalt = userDTO.username ?: user.usernameSalt
        user.email = userDTO.email ?: user.email
        user.name = userDTO.name ?: user.name
        user.surname = userDTO.surname ?: user.surname
        user.male = userDTO.male ?: user.male
        if (userDTO.country != null) {
            val keyFromLocalityName = getKeyFromLocalityName(userDTO.country)
            user.locality = if (keyFromLocalityName == -1L) user.locality else keyFromLocalityName
        }
    }

    fun changeUserPassword(id: Long, oldPassword: String, newPassword: String): Boolean {
        val user = userRepository.findUserById(id)
        if (passwordEncoder.matches(oldPassword, user.password)) {
            user.passwordHash = passwordEncoder.encode(newPassword)
            userRepository.save(user)
            return true
        }
        return false
    }

    fun getKeyFromLocalityName(name: String): Long {
        localityNames.forEach { (t, u) -> if (u == name) return t }
        return -1
    }
}