package org.example.dao

import org.example.dto.AppointmentToDoctorDto
import org.example.dto.DoctorDto
import org.example.dto.FreeDoctorDto
import org.example.entity.*
import org.example.model.FreeDoctorRequest
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Repository
class PatientDao(private val entityManager: EntityManager) {
    @Transactional
    fun addPatient(patient: Patient) {
        entityManager.persist(patient)
    }

    fun findPatientById(id: Long): Patient {
        return entityManager.find(Patient::class.java, id)
    }

    @Transactional
    fun addMedicalCard(medicalCard: MedicalCard) {
        entityManager.persist(medicalCard)
    }

    @Transactional
    fun addAppointmentToDoctor(appointmentToDoctor: AppointmentToDoctor) {
        entityManager.persist(appointmentToDoctor)
    }

    @Transactional
    fun cancelAppointment(appointmentId: Long) {
        entityManager.createQuery("update AppointmentToDoctor set status = :cancelStatus where id = :id")
            .setParameter("cancelStatus", AppointmentStatus.CANCELLED)
            .setParameter("id", appointmentId)
            .executeUpdate()
    }

    fun getFreeDoctor(freeDoctorRequest: FreeDoctorRequest): List<FreeDoctorDto> {
        val resultList = entityManager.createQuery(
            "select wd.doctor , ad.startTime from AppointmentToDoctor ad " +
                    "join ad.doctorSpecialization ds " +
                    "join ad.clinic.workingDays wd " +
                    "where ds.id = :specialization " +
                    "and wd.startTime >= :startTime " +
                    "and wd.endTime >=:endTime " +
                    "and wd.clinic.id = :clinic"
        )
            .setParameter("specialization", freeDoctorRequest.specialization)
            .setParameter("clinic",freeDoctorRequest.clinicId)
            .setParameter("startTime", freeDoctorRequest.startTime)
            .setParameter("endTime", freeDoctorRequest.endTime).resultList
val g:String = " "
        return emptyList()

    }
}