package org.example.dao

import org.example.entity.Clinic
import org.example.entity.Doctor
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Repository
class ClinicDao(private val entityManager: EntityManager) {
    fun findAllClinics(): List<Clinic> {
        return entityManager.createQuery("from Clinic").resultList as List<Clinic>
    }

    @Transactional
    fun addClinic(clinic: Clinic) {
        entityManager.persist(clinic)
    }

    fun findClinicById(id: Long): Clinic {
        return entityManager.find(Clinic::class.java, id)
    }

    @Transactional
    fun deleteClinic(clinicId: Long) {
        val clinic: Clinic = findClinicById(clinicId);
        entityManager.remove(clinic)
    }

    fun findDoctorsByClinicId(clinicId: Long): List<Doctor> {
      return  entityManager.createQuery("select pow.doctor from PlaceOfWork pow where pow.clinic.id = :id")
            .setParameter("id", clinicId).resultList as List<Doctor>
    }

}