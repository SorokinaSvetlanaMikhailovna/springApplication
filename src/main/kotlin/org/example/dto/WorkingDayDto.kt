package org.example.dto

import java.time.OffsetDateTime

data class WorkingDayDto(
    val id: Long? = null,
    val startTime: OffsetDateTime,
    val endTime: OffsetDateTime,
    val clinicId: Long,
    val doctorId: Long
) {

}