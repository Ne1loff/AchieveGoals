package com.example.achieve_goals.service

import com.example.achieve_goals.config.RegistrationRequest
import com.example.achieve_goals.entities.User
import com.example.achieve_goals.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findUserByUsernameSalt(username) // TODO*(Пересмотреть реализацию!)
    }

    fun saveUser(user: RegistrationRequest) {
        val userSalt = User(
            id = -1,
            usernameSalt = user.username,
            email = user.email,
            locality = user.locality,
            passwordHash = passwordEncoder.encode(user.password),
            admin = false,
            name = null,
            surname = null
        )
        //TODO()
        userRepository.save(userSalt)
    }
    fun saveAdmin(user: User) {
        user.passwordHash = passwordEncoder.encode(user.password)
        userRepository.save(user)
    }
}