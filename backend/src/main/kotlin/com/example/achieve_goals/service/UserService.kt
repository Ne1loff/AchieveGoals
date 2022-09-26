package com.example.achieve_goals.service

import com.example.achieve_goals.dto.RegistrationRequest
import com.example.achieve_goals.dto.UserDTO
import com.example.achieve_goals.dto.UserSettingsDto
import com.example.achieve_goals.entities.User
import com.example.achieve_goals.entities.UserAvatar
import com.example.achieve_goals.entities.UserSettings
import com.example.achieve_goals.exceptions.badRequest.InvalidLoginOrPasswordException
import com.example.achieve_goals.exceptions.conflict.EmailConflictException
import com.example.achieve_goals.exceptions.conflict.UsernameConflictException
import com.example.achieve_goals.exceptions.notFound.UserNotFoundException
import com.example.achieve_goals.mapper.UserMapper
import com.example.achieve_goals.repository.LocalityRepository
import com.example.achieve_goals.repository.UserAvatarRepository
import com.example.achieve_goals.repository.UserRepository
import com.example.achieve_goals.repository.UserSettingsRepository
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
    private val userSettingsRepository: UserSettingsRepository,
    private val minioService: MinioService,
    localityRepository: LocalityRepository,
    private val passwordEncoder: PasswordEncoder,
    private val mapper: UserMapper
) : UserDetailsService {

    companion object {
        const val MALE_DEFAULT_AVATAR: String = "male_default_avatar.png"
        const val FEMALE_DEFAULT_AVATAR: String = "female_default_avatar.png"
    }

    private val localityNames = localityRepository.findAll().associate { it.id to it.name }

    override fun loadUserByUsername(login: String): UserDetails {
        return userRepository.findUserByUsernameSalt(login)
            ?: userRepository.findUserByEmail(login)
            ?: throw InvalidLoginOrPasswordException()
    }

    fun setupUserSettings(user: User) {
        val settings = UserSettings(
            uid = user.id,
            language = "en",
            startedPage = "/app/today",
            timeFormat = "HH:mm",
            dateFormat = "DD.MM.YYYY",
            theme = "white",
            autoDarkTheme = false
        )

        userSettingsRepository.save(settings)
    }

    fun getAllUsers(): MutableList<UserDTO> {
        return userRepository.findAll()
            .map { user ->
                mapper.dtoFromUser(
                    user,
                    localityNames.getOrDefault(user.locality, ""),
                    getUserAvatarLink(user.id)
                )
            }.toMutableList()
    }

    fun getUserById(id: Long): UserDTO {
        val user = userRepository.findUserById(id) ?: throw UserNotFoundException()
        val avatarLink = getUserAvatarLink(id)
        return mapper.dtoFromUser(user, localityNames.getOrDefault(user.locality, ""), avatarLink)
    }

    fun getUserSettingsById(id: Long): UserSettingsDto {
        val userSettings = userSettingsRepository.getUserSettingsByUid(id)
            ?: throw UserNotFoundException()
        return mapper.dtoFromUserSettings(userSettings)
    }

    fun getUserAvatarLink(id: Long): String {
        val user = userRepository.findUserById(id) ?: throw UserNotFoundException()

        val filename = if (user.male) MALE_DEFAULT_AVATAR else FEMALE_DEFAULT_AVATAR

        if (user.userPhoto == null) return minioService.getPhoto(filename)
        return minioService.getPhoto(user.id, user.userPhoto!!.fileExtension)
    }

    @Transactional
    fun uploadUserAvatar(id: Long, file: InputStream, fileExtension: String) {
        val user = userRepository.findUserById(id) ?: throw UserNotFoundException()
        var userAvatar = user.userPhoto

        if (userAvatar != null) userAvatar.fileExtension = fileExtension
        else userAvatar = UserAvatar(id, fileExtension)

        userAvatarRepository.save(userAvatar)
    }

    @Transactional
    fun deleteUserPhoto(id: Long) {
        userAvatarRepository.deleteUserAvatarById(id)
    }


    fun saveUser(newUser: RegistrationRequest): User {
        if (userRepository.existsUserByEmail(newUser.email))
            throw EmailConflictException()

        val user = mapper.userFromRegistrationRequest(newUser)
        user.passwordHash = passwordEncoder.encode(user.password)

        return userRepository.save(user)
    }

    fun saveAdmin(user: User): User {
        user.passwordHash = passwordEncoder.encode(user.password)

        return userRepository.save(user)
    }

    @Transactional
    fun updateUser(userDTO: UserDTO, id: Long) {
        val user = userRepository.findUserById(id) ?: throw UserNotFoundException()

        if (
            userDTO.email != null &&
            userDTO.email != user.email &&
            userRepository.existsUserByEmail(userDTO.email)
        )
            throw UsernameConflictException()

        if (
            userDTO.username != null &&
            userDTO.username != user.usernameSalt &&
            userRepository.existsUserByUsernameSalt(userDTO.username)
        )
            throw EmailConflictException()


        userDTO.username?.let { user.usernameSalt = it }
        userDTO.email?.let { user.email = it }
        userDTO.name?.let { user.name = it }
        userDTO.surname?.let { user.surname = it }
        userDTO.male?.let { user.male = it }
        userDTO.country?.let {
            getKeyFromLocalityName(it)?.let { locality -> user.locality = locality }
        }
    }

    fun changeUserPassword(id: Long, oldPassword: String, newPassword: String): Boolean {
        val user = userRepository.findUserById(id) ?: throw UserNotFoundException()
        if (passwordEncoder.matches(oldPassword, user.password)) {
            user.passwordHash = passwordEncoder.encode(newPassword)
            userRepository.save(user)
            return true
        }
        return false
    }

    fun getKeyFromLocalityName(name: String): Long? {
        return localityNames.firstNotNullOfOrNull { it.takeIf { map -> map.value == name }?.key }
    }
}