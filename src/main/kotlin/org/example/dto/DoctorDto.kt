package org.example.dto

import java.time.LocalDate

data class DoctorDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val gender: Boolean,
    val birthDate: LocalDate
)