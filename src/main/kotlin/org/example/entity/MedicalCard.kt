package org.example.entity

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "medical_card")
data class MedicalCard(
    @Column(name = "record_date", nullable = false)
    val recordDate: LocalDate,
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    val doctor: Doctor,
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    val patient: Patient,
    val record: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}