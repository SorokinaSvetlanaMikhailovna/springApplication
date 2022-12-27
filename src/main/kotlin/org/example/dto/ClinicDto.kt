package org.example.dto

import javax.persistence.Column

data class ClinicDto(
    val id: Long,
    val name: String,
    val address: String
) {
}