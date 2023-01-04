package org.example.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
    val login: String,
    val password: String,
    val enabled: Boolean,
    @ManyToMany
    @JoinTable(
        name = "user_roles", joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "roles_id", referencedColumnName = "id")]
    )
    val roles: List<Role> = emptyList()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}