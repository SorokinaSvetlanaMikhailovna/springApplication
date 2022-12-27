package org.example.dto

import java.time.OffsetDateTime

class MedicalCardDto(val recordDate: OffsetDateTime,
                     val doctorId:Long,
                     val patientId : Long,
                     val record:String) {
}