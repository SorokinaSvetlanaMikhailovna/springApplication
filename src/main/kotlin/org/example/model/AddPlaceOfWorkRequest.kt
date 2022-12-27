package org.example.model

import org.example.entity.Specialization
import java.time.OffsetDateTime

class AddPlaceOfWorkRequest(
    val offerDate: OffsetDateTime,
    val doctorId: Long,
    val clinicId: Long,
    val specialization:Long
) {
}