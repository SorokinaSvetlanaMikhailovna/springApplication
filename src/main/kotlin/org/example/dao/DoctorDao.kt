package org.example.dao

import org.example.entity.*
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Repository
class DoctorDao(private val entityManager: EntityManager) {
    @Transactional
    fun addDoctor(doctor: Doctor) {
        entityManager.persist(doctor)
    }

    @Transactional
    fun addDoctorPlaceOfWork(placeOfWork: PlaceOfWork) {
        entityManager.persist(placeOfWork)
    }

    fun findDoctorById(id: Long): Doctor {
        return entityManager.find(Doctor::class.java, id)
    }

    fun findDoctorPlaceOfWorks(doctorId: Long): List<PlaceOfWork> {
        return entityManager.createQuery("select pow from PlaceOfWork pow where pow.doctor.id = :id ")
            .setParameter("id", doctorId)
            .resultList as List<PlaceOfWork>

    }

    fun deleteDoctor(doctorId: Long) {
        val doctor = findDoctorById(doctorId)
        entityManager.remove(doctor)
    }

    @Transactional
    fun deletePlaceOfWork(id: Long) {
        val placeOfWork = entityManager.find(PlaceOfWork::class.java, id)
        entityManager.remove(placeOfWork)
    }

    @Transactional
    fun addWorkingDay(workingDay: WorkingDay) {
        entityManager.persist(workingDay)
    }

    @Transactional
    fun addVacation(vacation: Vacation) {
        entityManager.persist(vacation)
    }

    @Transactional
    fun addDoctorSpecialization(doctor: Doctor) {
        entityManager.merge(doctor)
    }

    fun findSpecializationById(id: Long): Specialization {
        return entityManager.find(Specialization::class.java, id)
    }

    fun weeklySchedule(doctorId: Long, startDay: OffsetDateTime): List<WorkingDay> {
        return entityManager.createQuery("select wd from WorkingDay wd where wd.doctor.id = :doctorId and wd.startTime >= :startDay order by wd.startTime")
            .setParameter("doctorId", doctorId)
            .setParameter("startDay", startDay)
            .setMaxResults(7)
            .resultList as List<WorkingDay>
    }


}