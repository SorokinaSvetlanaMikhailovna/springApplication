package org.example.entity

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
@Table(name = "working_day")
data class WorkingDay(
    @Column(name = "start_time", nullable = false)
    val startTime: OffsetDateTime,

    @Column(name = "end_time", nullable = false)
    val endTime: OffsetDateTime,
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    val doctor: Doctor,
    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    val clinic: Clinic
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
