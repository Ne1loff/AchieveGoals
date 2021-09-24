package com.example.achieve_goals.service

import com.example.achieve_goals.config.RegistrationRequest
import com.example.achieve_goals.dto.UserDTO
import com.example.achieve_goals.entities.User
import com.example.achieve_goals.exceptions.ApiBadRequestException
import com.example.achieve_goals.mapper.UserMapperImpl
import com.example.achieve_goals.repository.LocalityRepository
import com.example.achieve_goals.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    localityRepository: LocalityRepository,
    private val passwordEncoder: PasswordEncoder,
    private val mapper: UserMapperImpl
) : UserDetailsService {

    private val localityNames = localityRepository.findAll().associate { it.id to it.name }

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findUserByUsernameSalt(username) // TODO*(Пересмотреть реализацию!)
    }

    fun getUserById(id: Long): UserDTO {
        val user = userRepository.findUserById(id)
        return mapper.dtoFromUser(user, localityNames.getOrDefault(user.locality, ""))
    }

    fun getAllUsers(): MutableList<UserDTO> {
        return userRepository.findAll()
            .map { user -> mapper.dtoFromUser(user, localityNames.getOrDefault(user.locality, "")) }
            .toMutableList()
    }

    fun saveUser(newUser: RegistrationRequest) : Boolean {
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
}