package org.example.dto

import java.time.OffsetDateTime

data class FreeDoctorDto(
    val doctorDto: DoctorDto,
    val listFreeTime:List<OffsetDateTime>
) {

}