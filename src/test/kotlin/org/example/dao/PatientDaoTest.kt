package org.example.dao

import org.example.entity.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.persistence.EntityManager
import javax.transaction.Transactional

class PatientDaoTest : DBDockerInit() {
    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var patientDao: PatientDao

    @Test
    fun addPatientTest() {
        val patient = Patient(
            "Иван",
            "Федоров",
            false,
            LocalDate.of(1979, 8, 2)
        )
        patientDao.addPatient(patient)
        val patient2 =
            entityManager.createQuery("select pt from Patient pt where pt.firstName = :firstName and pt.lastName = :lastName")
                .setParameter("firstName", patient.firstName)
                .setParameter("lastName", patient.lastName).singleResult as Patient
        Assertions.assertEquals(patient, patient2)
    }

    @Test
    fun findPatientByIdTest() {
        val patient = Patient(
            "Иван",
            "Федоров",
            false,
            LocalDate.of(1979, 8, 2)
        )
        patientDao.addPatient(patient)
        Assertions.assertEquals(patient, patientDao.findPatientById(patient.id))
    }

    @Transactional
    @Test
    fun addMedicalCardTest() {
        val doctor = Doctor(
            "Мария",
            "Герасимова",
            true,
            LocalDate.of(1993, 11, 3)
        )
        val patient = Patient(
            "Иван",
            "Федоров",
            false,
            LocalDate.of(1979, 8, 2)
        )
        val medicalCard = MedicalCard(
            LocalDate.of(2021, 5, 3),
            doctor,
            patient,
            "жалобы на боль в голове, выпесано лекарство-'ГоловаНеБолин'"
        )
        entityManager.persist(doctor)
        entityManager.persist(patient)
        patientDao.addMedicalCard(medicalCard)
        Assertions.assertEquals(medicalCard, entityManager.find(MedicalCard::class.java, medicalCard.id))
    }

    @Transactional
    @Test
    fun addAppointmentToDoctorTest() {
        val clinic = Clinic("Академия здоровья", "Академика Курчатова, улица")
        val specialization = Specialization("ВРАЧ-АЛЛЕРГОЛОГ")
        val doctor = Doctor(
            "Мария",
            "Герасимова",
            true,
            LocalDate.of(1993, 11, 3)
        )
        val patient = Patient(
            "Иван",
            "Федоров",
            false,
            LocalDate.of(1979, 8, 2)
        )
        val appointmentToDoctor = AppointmentToDoctor(
            OffsetDateTime.of(
                2022,
                12,
                3,
                11,
                30,
                0,
                0,
                ZoneOffset.UTC
            ), doctor, specialization, patient, clinic, AppointmentStatus.PLANNED
        )
        entityManager.persist(doctor)
        entityManager.persist(specialization)
        entityManager.persist(patient)
        entityManager.persist(clinic)
        patientDao.addAppointmentToDoctor(appointmentToDoctor)
        Assertions.assertEquals(
            appointmentToDoctor.id,
            entityManager.createQuery("select ap.id from AppointmentToDoctor ap where ap.doctor.firstName = :firstNameDoctor")
                .setParameter("firstNameDoctor", doctor.firstName).singleResult as Long
        )
    }

    @Transactional
    @Test
    fun cancelAppointmentTest() {
        val clinic = Clinic("Академия здоровья", "Академика Курчатова, улица")
        val specialization = Specialization("ВРАЧ-АЛЛЕРГОЛОГ")
        val doctor = Doctor(
            "Мария",
            "Герасимова",
            true,
            LocalDate.of(1993, 11, 3)
        )
        val patient = Patient(
            "Иван",
            "Федоров",
            false,
            LocalDate.of(1979, 8, 2)
        )
        val appointmentToDoctor = AppointmentToDoctor(
            OffsetDateTime.of(
                2022,
                12,
                3,
                11,
                30,
                0,
                0,
                ZoneOffset.UTC
            ), doctor, specialization, patient, clinic, AppointmentStatus.PLANNED
        )
        entityManager.persist(doctor)
        entityManager.persist(specialization)
        entityManager.persist(patient)
        entityManager.persist(clinic)
        entityManager.persist(appointmentToDoctor)
        patientDao.cancelAppointment(appointmentToDoctor.id)
        Assertions.assertEquals(
            AppointmentStatus.CANCELLED,
            entityManager.createQuery("select ap.status from AppointmentToDoctor ap where ap.doctor.firstName = :firstNameDoctor")
                .setParameter("firstNameDoctor", doctor.firstName).singleResult as AppointmentStatus)
    }

    fun getFreeDoctorTest() {
    }
}