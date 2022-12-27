package org.example.controllers

import org.example.dto.*
import org.example.model.AddPlaceOfWorkRequest
import org.example.services.DoctorService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.OffsetDateTime

@RestController
@RequestMapping("v1/doctors")
class DoctorController(private val doctorService: DoctorService) {
    @GetMapping("{doctorId}")
    fun findDoctorById(@PathVariable doctorId: Long): DoctorDto {
        return doctorService.findDoctorById(doctorId)
    }

    @PostMapping
    fun addDoctor(@RequestBody doctorDto: DoctorDto) {
        doctorService.addDoctor(doctorDto)
    }

    @DeleteMapping("{doctorId}")
    fun deleteDoctor(@PathVariable doctorId: Long) {
        doctorService.deleteDoctor(doctorId)
    }

    @GetMapping("{doctorId}/place-of-works")
    fun doctorPlacesOfWork(@PathVariable doctorId: Long): List<PlacesOfWorkDto> {
        return doctorService.findDoctorPlacesOfWorks(doctorId)
    }

    @PostMapping("place-of-works")
    fun addDoctorPlaceOfWork(@RequestBody addPlaceOfWorkRequest: AddPlaceOfWorkRequest) {
        doctorService.addDoctorPlaceOfWork(addPlaceOfWorkRequest)
    }

    @DeleteMapping("place-of-works/{placeOfWorkId}")
    fun deleteDoctorPlaceOfWork(@PathVariable placeOfWorkId: Long) {
        doctorService.deleteDoctorPlaceOfWork(placeOfWorkId)
    }

    @PostMapping("working-day")
    fun addWorkingDay(@RequestBody workingDayDto: WorkingDayDto) {
        doctorService.addWorkingDay(workingDayDto)
    }

    @PostMapping("vacation")
    fun addVacation(@RequestBody vacationDto: VacationDto) {
        doctorService.addVacation(vacationDto)
    }

    @PostMapping("specialization")
    fun addDoctorSpecialization(@RequestBody doctorSpecializationDto: DoctorSpecializationDto) {
        doctorService.addDoctorSpetialication(doctorSpecializationDto)
    }

    @GetMapping("weekly-schedule")
    fun getWeeklySchedule(
        @RequestParam doctorId: Long,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) startDay: OffsetDateTime
    ): List<WorkingDayDto> {
        return doctorService.getWeeklySchedule(doctorId, startDay)
    }

}