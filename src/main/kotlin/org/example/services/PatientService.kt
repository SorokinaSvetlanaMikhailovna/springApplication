package org.example.services

import org.example.dao.ClinicDao
import org.example.dao.DoctorDao
import org.example.dao.PatientDao
import org.example.dto.AppointmentToDoctorDto
import org.example.dto.FreeDoctorDto
import org.example.dto.MedicalCardDto
import org.example.dto.PatientDto
import org.example.entity.AppointmentStatus
import org.example.entity.AppointmentToDoctor
import org.example.entity.MedicalCard
import org.example.entity.Patient
import org.example.model.FreeDoctorRequest
import org.springframework.stereotype.Service

@Service
class PatientService(val patientDao: PatientDao, val doctorDao: DoctorDao, val clinicDao: ClinicDao) {
    fun addPatient(patientDto: PatientDto) {
        var patient: Patient = Patient(
            patientDto.firstName,
            patientDto.lastName,
            patientDto.gender,
            patientDto.birthDate
        )
        patientDao.addPatient(patient)
    }

    fun findPatientById(id: Long): PatientDto {
        var patient = patientDao.findPatientById(id)
        return PatientDto(
            patient.id,
            patient.firstName,
            patient.lastName,
            patient.gender,
            patient.birthDate
        )
    }

    fun addMedicalCard(medicalCardDto: MedicalCardDto) {
        val medicalCard = MedicalCard(
            medicalCardDto.recordDate,
            doctorDao.findDoctorById(medicalCardDto.doctorId),
            patientDao.findPatientById(medicalCardDto.patientId),
            medicalCardDto.record
        )
        patientDao.addMedicalCard(medicalCard)
    }

    fun addAppointmentToDoctor(appointmentToDoctorDto: AppointmentToDoctorDto) {
        val appointmentToDoctor = AppointmentToDoctor(
            appointmentToDoctorDto.startTime,
            doctorDao.findDoctorById(appointmentToDoctorDto.doctorId),
            doctorDao.findSpecializationById(appointmentToDoctorDto.specializationId),
            patientDao.findPatientById(appointmentToDoctorDto.patientId),
            clinicDao.findClinicById(appointmentToDoctorDto.clinicId),
            AppointmentStatus.PLANNED
        )
        patientDao.addAppointmentToDoctor(appointmentToDoctor)
    }

    fun cancelAppointment(appointmentId: Long) {
        patientDao.cancelAppointment(appointmentId)
    }

    fun getFreeDoctor(freeDoctorRequest: FreeDoctorRequest): List<FreeDoctorDto> {
        return patientDao.getFreeDoctor(freeDoctorRequest)
    }

}