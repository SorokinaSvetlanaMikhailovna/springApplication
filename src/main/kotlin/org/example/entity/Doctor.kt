package org.example.entity

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
class Doctor(
    @Column(nullable = false, name = "first_name")
    val firstName: String,

    @Column(nullable = false, name = "last_name")
    val lastName: String,

    @Column(nullable = false)
    val gender: Boolean,

    @Column(nullable = false, name = "birth_date")
    val birthDate: OffsetDateTime,
    @ManyToMany
    @JoinTable(
        name = "doctor_specialization", joinColumns = [JoinColumn(name = "doctor_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "specialization_id", referencedColumnName = "id")]
    )
    val specializations: MutableList<Specialization> = mutableListOf()

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}

