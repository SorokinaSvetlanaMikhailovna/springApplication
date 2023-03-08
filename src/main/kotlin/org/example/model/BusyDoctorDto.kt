package org.example.model

import org.example.dto.DoctorDto
import java.time.OffsetDateTime
class BusyDoctorDto(
    val doctorDto: DoctorDto,
    val BusyTime: OffsetDateTime
)