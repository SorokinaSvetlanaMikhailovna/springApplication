package org.example.entity

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
@Table(name = "place_of_work")
class PlaceOfWork(
    @Column(name = "date_of_start_work", nullable = false)
    val dateOfStartWork: OffsetDateTime,
    @ManyToOne
    @JoinColumn(name = "doctor_id",nullable = false)
    val doctor: Doctor,
    @ManyToOne
    @JoinColumn(name = "clinic_id",nullable = false)
    val clinic: Clinic,
    @ManyToOne
    @JoinColumn(name = "specialization_id")
    val specialization: Specialization
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
