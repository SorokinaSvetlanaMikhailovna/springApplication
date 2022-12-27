package org.example.entity

import java.time.OffsetDateTime
import javax.persistence.*


@Entity
@Table(name = "appointment_to_doctor")
class AppointmentToDoctor(
    @Column(name = "start_time", nullable = false)
    val startTime: OffsetDateTime,
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    val doctor: Doctor,
    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = false)
    val specialization: Specialization,
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    val patient: Patient,
    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    val clinic: Clinic,
    @Enumerated(EnumType.STRING)
    val status: AppointmentStatus
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}