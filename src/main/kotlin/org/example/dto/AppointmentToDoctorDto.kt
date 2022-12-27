package org.example.dto

import java.time.OffsetDateTime

data class AppointmentToDoctorDto(
    val startTime: OffsetDateTime,
    val doctorId:Long,
    val specializationId: Long,
    val patientId: Long,
    val clinicId: Long
)
