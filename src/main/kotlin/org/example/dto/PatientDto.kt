package org.example.dto

import java.time.OffsetDateTime

data class PatientDto(
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val gender: Boolean,
    val birthDate: OffsetDateTime
)