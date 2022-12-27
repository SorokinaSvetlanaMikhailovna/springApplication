package org.example.entity

import javax.persistence.*

@Entity
class Clinic(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    var address: String,
    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "clinic")
    val appointmentToDoctors: List<AppointmentToDoctor> = emptyList(),
    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "clinic")
    val placeOfWorks: List<PlaceOfWork> = emptyList(),
    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "clinic")
    val vacations: List<Vacation> = emptyList(),
    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "clinic")
    val workingDays: List<WorkingDay> = emptyList(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}