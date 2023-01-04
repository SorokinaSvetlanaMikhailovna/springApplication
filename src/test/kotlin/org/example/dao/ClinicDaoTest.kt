package org.example.dao

import org.example.entity.Clinic
import org.example.entity.Doctor
import org.example.entity.PlaceOfWork
import org.example.entity.Specialization
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.transaction.Transactional

class ClinicDaoTest : DBDockerInit() {

    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var clinicDao: ClinicDao

    @Test
    fun addClinicTest() {
        val clinic = Clinic("clinic health", "jalan Padma")
        clinicDao.addClinic(clinic)
        Assertions.assertEquals(
            1,
            entityManager.createQuery("select cl.name from Clinic cl where cl.name = 'clinic health'").resultList.size
        )

    }

    @Test
    fun addDoubleClinicTest() {
        val clinic = Clinic("clinic health", "jalan Padma")
        val clinic2 = Clinic("clinic health", "jalan Padma")
        clinicDao.addClinic(clinic)
        Assertions.assertThrows(RuntimeException::class.java) { // TODO add my exception
            clinicDao.addClinic(clinic2)
        }
    }

    @Transactional
    @Test
    fun findClinicByIdTest() {
        val clinic = Clinic("clinic health", "jalan Padma")
        clinicDao.addClinic(clinic)
        val id: Long =
            entityManager.createQuery("select cl.id from Clinic cl where cl.name = 'clinic health' and cl.address = 'jalan Padma'").singleResult as Long
        val clinicFromDB = clinicDao.findClinicById(id)
        Assertions.assertEquals(clinic, clinicFromDB)
    }

    @Test
    fun deleteClinicTest() {
        val clinic = Clinic("clinic health", "jalan Padma")
        clinicDao.addClinic(clinic)
        val id: Long =
            entityManager.createQuery("select cl.id from Clinic cl where cl.name = 'clinic health' and cl.address = 'jalan Padma'").singleResult as Long
        clinicDao.deleteClinic(id)
        Assertions.assertThrows(NoResultException::class.java) {
            entityManager.createQuery(
                "select cl.id from Clinic cl where cl.name = " +
                        "'clinic health' and cl.address = 'jalan Padma'"
            ).singleResult
        }
    }

    @Test
    @Transactional
    fun findDoctorsByClinicIdTest() {
        val clinic = Clinic("Академия здоровья", "Академика Курчатова, улица")
        val clinic1 = Clinic("Док+", "Крюковский тупик")
        val doctor = Doctor(
            "Мария",
            "Герасимова",
            true,
            LocalDate.of(1993, 11, 3)
        )
        val doctor1 = Doctor(
            "Александр",
            "Пушкин",
            false,
            LocalDate.of(1988, 2, 5)
        )
        val doctor2 = Doctor(
            "Ганс",
            "Андерсен",
            false,
            LocalDate.of(1994, 9, 8)
        )
        val specialization = Specialization("ВРАЧ-АЛЛЕРГОЛОГ")
        val specialization1 = Specialization("ВРАЧ-АНЕСТЕЗИОЛОГ-РЕАНИМАТОЛОГ")
        entityManager.persist(doctor)
        entityManager.persist(doctor1)
        entityManager.persist(doctor2)
        entityManager.persist(clinic)
        entityManager.persist(clinic1)
        entityManager.persist(specialization)
        entityManager.persist(specialization1)
        entityManager.persist(
            PlaceOfWork(
                OffsetDateTime.of(2005, 2, 9, 0, 0, 0, 0, ZoneOffset.UTC),
                doctor, clinic, specialization
            )
        )

        entityManager.persist(
            PlaceOfWork(
                OffsetDateTime.of(2005, 2, 9, 0, 0, 0, 0, ZoneOffset.UTC),
                doctor1, clinic, specialization1
            )
        )
        entityManager.persist(
            PlaceOfWork(
                OffsetDateTime.of(2005, 2, 9, 0, 0, 0, 0, ZoneOffset.UTC),
                doctor2, clinic1, specialization
            )
        )
        val listDoctor = clinicDao.findDoctorsByClinicId(clinic.id)
        val listMyDoctor = listOf(doctor, doctor1)
        Assertions.assertEquals(listDoctor, listMyDoctor)
    }

    @Test
    @Transactional
    fun findAllClinicsTest() {
        val clinic = Clinic("Академия здоровья", "Академика Курчатова, улица")
        val clinic1 = Clinic("Док+", "Крюковский тупик")
        val clinic2 = Clinic("Веселый медик", "Курьяновский 2-й, проезд")
        entityManager.persist(clinic)
        entityManager.persist(clinic1)
        entityManager.persist(clinic2)
        val listMyClinic = listOf(clinic, clinic1, clinic2)
        val listClinic = clinicDao.findAllClinics()
        Assertions.assertEquals(listClinic, listMyClinic)
    }
}