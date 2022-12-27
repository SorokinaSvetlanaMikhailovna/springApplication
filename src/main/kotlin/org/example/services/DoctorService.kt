package org.example.services

import org.example.dao.ClinicDao
import org.example.dao.DoctorDao
import org.example.dto.*
import org.example.entity.*
import org.example.model.AddPlaceOfWorkRequest
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class DoctorService(val doctorDao: DoctorDao, val clinicDao: ClinicDao) {
    fun findDoctorById(id: Long): DoctorDto {
        val doctor = doctorDao.findDoctorById(id)
        return DoctorDto(doctor.id, doctor.firstName, doctor.lastName, doctor.gender, doctor.birthDate)
    }

    fun addDoctor(doctorDto: DoctorDto) {
        val doctor = Doctor(
            firstName = doctorDto.firstName,
            lastName = doctorDto.lastName,
            birthDate = doctorDto.birthDate,
            gender = doctorDto.gender,
        )
        doctorDao.addDoctor(doctor)
    }

    fun addDoctorPlaceOfWork(request: AddPlaceOfWorkRequest) {
        doctorDao.addDoctorPlaceOfWork(
            PlaceOfWork(
                request.offerDate,
                doctorDao.findDoctorById(request.doctorId),
                clinicDao.findClinicById(request.clinicId),
                doctorDao.findSpecializationById(request.specialization)

            )
        )
    }

    fun findDoctorPlacesOfWorks(id: Long): List<PlacesOfWorkDto> {
        return doctorDao.findDoctorPlaceOfWorks(id).map {
            PlacesOfWorkDto(
                it.id,
                it.dateOfStartWork,
                DoctorDto(it.doctor.id, it.doctor.firstName, it.doctor.lastName, it.doctor.gender, it.doctor.birthDate),
                ClinicDto(it.clinic.id, it.clinic.name, it.clinic.address),
                it.specialization.specialization

            )
        }
    }

    fun deleteDoctor(doctorId: Long) {
        doctorDao.deleteDoctor(doctorId)
    }

    fun deleteDoctorPlaceOfWork(id: Long) {
        doctorDao.deletePlaceOfWork(id)
    }

    fun addWorkingDay(workingDayDto: WorkingDayDto) {
        val doctor = doctorDao.findDoctorById(workingDayDto.doctorId)
        val clinic = clinicDao.findClinicById(workingDayDto.clinicId)
        val workingDay = WorkingDay(
            workingDayDto.startTime,
            workingDayDto.endTime,
            doctor,
            clinic
        )
        doctorDao.addWorkingDay(workingDay)
    }

    fun addVacation(vacationDto: VacationDto) {
        val doctor = doctorDao.findDoctorById(vacationDto.doctorId)
        val clinic = clinicDao.findClinicById(vacationDto.clinicId)
        doctorDao.addVacation(Vacation(vacationDto.startTime, vacationDto.endTime, doctor, clinic))
    }

    fun addDoctorSpetialication(doctorSpecializationDto: DoctorSpecializationDto) {
        val specialization =doctorDao.findSpecializationById(doctorSpecializationDto.specializationId)
        val doctor = doctorDao.findDoctorById(doctorSpecializationDto.doctorId)
        doctor.specializations.add(specialization)
        doctorDao.addDoctorSpecialization(doctor)
    }

    fun getWeeklySchedule(doctorId: Long, startDay: OffsetDateTime): List<WorkingDayDto> {
        val workingDay = doctorDao.weeklySchedule(doctorId, startDay)
        return workingDay.map {
            WorkingDayDto(
                id = it.id,
                startTime = it.startTime,
                endTime = it.endTime,
                clinicId = it.clinic.id,
                doctorId = it.doctor.id
            )
        }

    }


}