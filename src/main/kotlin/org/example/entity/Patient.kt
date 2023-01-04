package org.example.entity

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Patient(
    @Column(name = "first_name", nullable = false)
    val firstName: String,
    @Column(nullable = false, name = "last_name")
    val lastName: String,
    @Column(nullable = false)
    val gender: Boolean,
    @Column(nullable = false, name = "birth_date")
    val birthDate: LocalDate
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0


}
