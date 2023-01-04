package org.example.services

import org.example.dao.ClinicDao
import org.example.dto.ClinicDto
import org.example.dto.DoctorDto
import org.example.entity.Clinic
import org.springframework.stereotype.Service


@Service
class ClinicService(val clinicDao: ClinicDao) {
    fun findAllClinic(): List<ClinicDto> {
        return clinicDao.findAllClinics()
            .map { ClinicDto(it.id, it.name, it.address) }
    }

    fun addClinic(clinicDto: ClinicDto) {
        val clinic = Clinic(clinicDto.name, clinicDto.address)
        clinicDao.addClinic(clinic)
    }

    fun deleteClinic(clinicId: Long) {
        clinicDao.deleteClinic(clinicId)
    }

    fun getClinicDoctors(clinicId: Long): List<DoctorDto> {
        return clinicDao.findDoctorsByClinicId(clinicId)
            .map { DoctorDto(it.id, it.firstName, it.lastName, it.gender, it.birthDate) }

    }
}