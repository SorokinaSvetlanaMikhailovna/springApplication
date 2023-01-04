package org.example.entity

import javax.persistence.*

@Entity
@Table(name = "roles")
data class Role(val name: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}