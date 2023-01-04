package org.example.dto

import java.time.LocalDate

class MedicalCardDto(val recordDate: LocalDate,
                     val doctorId:Long,
                     val patientId : Long,
                     val record:String)