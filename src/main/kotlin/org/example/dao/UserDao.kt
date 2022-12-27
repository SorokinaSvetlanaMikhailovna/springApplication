package org.example.dao

import org.example.entity.Role
import org.example.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Repository
class UserDao(private val entityManager: EntityManager) : UserDetailsService {


    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails? {
        if (username == null) {
            return null
        }
        val user = findByUsername(username)
        val roles = user.roles.map { SimpleGrantedAuthority(it.name) }.toMutableList()
        return object : UserDetails {
            override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
                return roles
            }

            override fun getPassword(): String {
                return user.password
            }

            override fun getUsername(): String {
                return user.login
            }

            override fun isAccountNonExpired(): Boolean {
                return true
            }

            override fun isAccountNonLocked(): Boolean {
                return true
            }

            override fun isCredentialsNonExpired(): Boolean {
                return true
            }

            override fun isEnabled(): Boolean {
                return user.enabled
            }
        }
    }

    fun findRoleByName(name: String): Role {
        return entityManager.createQuery("select rl from Role rl where rl.name = :name")
            .setParameter("name", name).singleResult as Role
    }

    @Transactional
    fun addUser(user: User) {
        entityManager.persist(user)
    }

    fun findByUsername(username: String): User {
        return entityManager.createQuery("select u from User u where u.login = :name")
            .setParameter("name", username).singleResult as User
    }

}