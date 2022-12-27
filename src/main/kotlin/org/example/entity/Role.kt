package org.example.entity

import javax.persistence.*

@Entity
@Table(name = "roles")
class Role(val name: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}