package org.example.controllers

import org.example.dto.AppointmentToDoctorDto
import org.example.dto.FreeDoctorDto
import org.example.dto.MedicalCardDto
import org.example.dto.PatientDto
import org.example.model.FreeDoctorRequest
import org.example.services.PatientService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

@RestController
@RequestMapping("v1/patients")
class PatientController(private val patientService: PatientService) {
    @GetMapping("{patientId}")
    fun findPatientById(@PathVariable patientId: Long): PatientDto {
        return patientService.findPatientById(patientId)
    }

    @PostMapping
    fun addPatient(@RequestBody patientDto: PatientDto) {
        patientService.addPatient(patientDto)

    }

    @PostMapping("medical-cards")
    fun addMedicalCard(@RequestBody medicalCardDto: MedicalCardDto) {
        patientService.addMedicalCard(medicalCardDto)
    }

    @PostMapping("appointment-to-doctor")
    fun addAppointmentToDoctor(@RequestBody appointmentToDoctorDto: AppointmentToDoctorDto) {
        patientService.addAppointmentToDoctor(appointmentToDoctorDto)
    }

    @PostMapping("appointment-to-doctor/{appointmentId}/cancel")
    fun canselAppointment(@PathVariable appointmentId: Long) {
        patientService.cancelAppointment(appointmentId)
    }

    @GetMapping("free-doctors")
    fun getFreeDoctors(
        @RequestParam specializationDoctorId: Long,
        @RequestParam clinicId: Long,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) startTime: OffsetDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) endTime: OffsetDateTime
    ): List<FreeDoctorDto> {
        val freeDoctorRequest = FreeDoctorRequest(specializationDoctorId, clinicId, startTime, endTime)
        return patientService.getFreeDoctor(freeDoctorRequest)
    }
}