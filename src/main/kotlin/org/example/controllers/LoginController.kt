package org.example.controllers

import org.example.dao.UserDao
import org.example.dto.UserDto
import org.example.dto.UserTokenDto
import org.example.util.JwtTokenUtil
import org.example.services.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/login")
class LoginController(
    private val userDao: UserDao,
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil
) {

    @PostMapping("patient")
    fun addPatient(@RequestBody userDto: UserDto) {
        userService.addPatient(userDto)
    }

    @PostMapping("doctor")
    fun addDoctor(@RequestBody userDto: UserDto) {
        userService.addDoctor(userDto)
    }

    @PostMapping
    fun login(@RequestBody userDto: UserDto): ResponseEntity<UserTokenDto> {
        val authentication: Authentication =
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(userDto.login, userDto.password))
        val token = jwtTokenUtil.generateAccessToken(userDao.findByUsername(userDto.login))
        val userTokenDto = UserTokenDto(token)


        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            .body(userTokenDto)
    }
}