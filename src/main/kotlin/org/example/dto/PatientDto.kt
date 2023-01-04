package org.example.dto

import java.time.LocalDate

data class PatientDto(
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val gender: Boolean,
    val birthDate: LocalDate
)