package org.example.dao

import org.example.dto.DoctorDto
import org.example.dto.FreeDoctorDto
import org.example.entity.*
import org.example.model.BusyDoctorDto
import org.example.model.FreeDoctorRequest
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.Objects
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Repository
class PatientDao(private val entityManager: EntityManager) {
    val TIME_FOR_ONE_PATIENT = 30

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
        var mapDoctor = mutableMapOf<DoctorDto, MutableList<OffsetDateTime>>()
        val resultList = entityManager.createQuery(
            "select wd.doctor , ad.startTime from doctor d" +
                    "left join AppointmentToDoctor ad " +
                    "join ad.doctorSpecialization ds " +
                    "join ad.clinic.workingDays wd " +
                    "where ds.id = :specialization " +
                    "and wd.startTime >= :startTime " +
                    "and wd.endTime >=:endTime " +
                    "and wd.clinic.id = :clinic"
        )
            .setParameter("specialization", freeDoctorRequest.specialization)
            .setParameter("clinic", freeDoctorRequest.clinicId)
            .setParameter("startTime", freeDoctorRequest.startTime)
            .setParameter("endTime", freeDoctorRequest.endTime)
            .resultList
            .map {
                val element = it as Array<*>
                val doctor = element[0] as Doctor
                val startTime = element[1] as OffsetDateTime
                BusyDoctorDto(
                    DoctorDto(doctor.id, doctor.firstName, doctor.lastName, doctor.gender, doctor.birthDate),
                    startTime
                )
            }.forEach {
                val get = mapDoctor.get(it.doctorDto)
                if (get != null) {
                    get.add(it.BusyTime)
                } else {
                    mapDoctor.put(it.doctorDto, mutableListOf(it.BusyTime))
                }
            }
        val freeDoctor = mutableListOf<FreeDoctorDto>()
        for (doctorDto in mapDoctor) {
            var freeTime = mutableListOf<OffsetDateTime>()
            val startOffsetDateTime = freeDoctorRequest.startTime
            val endOffsetDateTime = freeDoctorRequest.endTime
            while (true) {
                if (startOffsetDateTime.equals(endOffsetDateTime) || startOffsetDateTime > endOffsetDateTime) {
                    break
                }
                var reservedTime = false
                for (time in doctorDto.value) {
                    if (time.equals(startOffsetDateTime)) {
                        reservedTime = true
                    }
                }
                if (reservedTime == false) {
                    freeTime.add(startOffsetDateTime)
                }
                startOffsetDateTime.minusMinutes(-30)

            }
            freeDoctor.add(FreeDoctorDto(doctorDto.key, freeTime))
        }





        return freeDoctor

    }
}