package org.example.model

import java.time.OffsetDateTime

data class FreeDoctorRequest(
    val specialization: Long,
    val clinicId:Long,
    val startTime:OffsetDateTime,
    val endTime: OffsetDateTime
    
    ) {
}