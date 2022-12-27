package org.example.dto

import java.time.OffsetDateTime

data class DoctorDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val gender: Boolean,
    val birthDate: OffsetDateTime
)