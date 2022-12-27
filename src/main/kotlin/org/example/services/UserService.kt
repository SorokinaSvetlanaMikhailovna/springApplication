package org.example.services

import org.example.dao.UserDao
import org.example.dto.UserDto
import org.example.entity.Role
import org.example.entity.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userDao: UserDao, private  val passwordEncoder: PasswordEncoder) {
    fun addPatient(userDto: UserDto) {
        val listRole: List<Role> = listOf(userDao.findRoleByName("patient"))
        val user = User(
            userDto.login,
            passwordEncoder.encode(userDto.password), true, listRole
        )
        userDao.addUser(user)
    }

    fun addDoctor(userDto: UserDto) {
        val listRole: List<Role> = listOf(userDao.findRoleByName("doctor"))
        val user = User(
            userDto.login,
            passwordEncoder.encode(userDto.password), true, listRole
        )
        userDao.addUser(user)
    }
}