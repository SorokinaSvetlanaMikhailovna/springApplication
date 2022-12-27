package org.example

import org.example.entity.Doctor
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.annotation.PostConstruct
import javax.persistence.EntityManager
import javax.transaction.Transactional
import kotlin.random.Random

@Component
class AddDataInDB(
    private val reader: Reader,
    private val entityManager: EntityManager,
    private val platformTransactionManager: PlatformTransactionManager
) {
    private fun doctor() {
        val count = 100000
        for (i in (1..count)) {
            var listFirstName: MutableList<String> = mutableListOf()
            var listLastName: MutableList<String> = mutableListOf()
            val random = Random(System.currentTimeMillis())
            val gender = random.nextBoolean()

            if (gender) {
                listFirstName = reader.womenFirstName
                listLastName = reader.womenLastName
            } else {
                listFirstName = reader.menFirstName
                listLastName = reader.menLastName
            }
            val randomFirstName = random.nextInt(listFirstName.size)
            val randomLastName = random.nextInt(listLastName.size)
            val randomBirthday = OffsetDateTime.of(
                random.nextInt(1970, 2005),
                random.nextInt(1, 12),
                random.nextInt(1, 28),
                0,
                0,
                0,
                0,
                ZoneOffset.UTC,
            )

            entityManager.persist(
                Doctor(
                    listFirstName.get(randomFirstName),
                    listLastName.get(randomLastName),
                    gender,
                    randomBirthday
                )
            )
        }

    }

    private fun clinic() {}
    private fun patient() {}
    private fun specialization() {}
    private fun place_of_work() {}
    private fun medical_card() {}
    private fun working_day() {}
    private fun vacation() {}
    private fun appointment_to_doctor() {}
    private fun doctor_specialization() {}
    private fun users() {}
    private fun roles() {}
    private fun user_roles() {}

    @Transactional
    fun cleanAll() {
        entityManager.createNativeQuery("delete from doctor").executeUpdate()
        val executeUpdate = entityManager.createNativeQuery("delete from clinic").executeUpdate()
        println(executeUpdate)
        entityManager.createNativeQuery("delete from patient").executeUpdate()
        entityManager.createNativeQuery("delete from specialization").executeUpdate()
        entityManager.createNativeQuery("delete from place_of_work").executeUpdate()
        entityManager.createNativeQuery("delete from medical_card").executeUpdate()
        entityManager.createNativeQuery("delete from working_day").executeUpdate()
        entityManager.createNativeQuery("delete from vacation").executeUpdate()
        entityManager.createNativeQuery("delete from appointment_to_doctor").executeUpdate()
        entityManager.createNativeQuery("delete from doctor_specialization").executeUpdate()
        entityManager.createNativeQuery("delete from users").executeUpdate()
        entityManager.createNativeQuery("delete from doctor_specialization").executeUpdate()
        entityManager.createNativeQuery("delete from roles").executeUpdate()
        entityManager.createNativeQuery("delete from user_roles").executeUpdate()
    }

    @PostConstruct
    fun postConstruct() {
        TransactionTemplate(platformTransactionManager).execute {
            cleanAll()
            reader.readeFile()
            doctor()
        }

    }
}