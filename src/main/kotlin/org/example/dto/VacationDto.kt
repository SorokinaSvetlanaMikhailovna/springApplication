package org.example.dto

import java.time.OffsetDateTime

class VacationDto(
    val startTime: OffsetDateTime,
    val endTime: OffsetDateTime,
    val clinicId: Long,
    val doctorId: Long
) {
}