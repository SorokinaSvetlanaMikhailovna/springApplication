package org.example.entity

import java.time.OffsetDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Patient(
    @Column(name = "first_name", nullable = false)
    val firstName: String,
    @Column(nullable = false, name = "last_name")
    val lastName: String,
    @Column(nullable = false)
    val gender: Boolean,
    @Column(nullable = false, name = "birth_date")
    val birthDate: OffsetDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0


}
