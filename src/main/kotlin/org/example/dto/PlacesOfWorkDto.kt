package org.example.dto

import org.example.entity.Specialization
import java.time.OffsetDateTime

class PlacesOfWorkDto(
    val id:Long,
    val offerDate: OffsetDateTime,
    val doctorDto: DoctorDto,
    val clinicDto: ClinicDto,
    val specialization: String
) {
}