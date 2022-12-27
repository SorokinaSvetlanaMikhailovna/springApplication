package org.example.controllers

import org.example.dto.ClinicDto
import org.example.dto.DoctorDto
import org.example.services.ClinicService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/clinics")
class ClinicController(private val clinicService: ClinicService) {
    @GetMapping
    fun findAllClinic(): List<ClinicDto> {
        return clinicService.findAllClinic()
    }

    @PostMapping
    fun addClinic(@RequestBody clinicDto: ClinicDto) {
        clinicService.addClinic(clinicDto)
    }

    @DeleteMapping("{id}")
    fun deleteClinic(@PathVariable("id") clinicId: Long) {
        clinicService.deleteClinic(clinicId)
    }

    @GetMapping("{clinicId}/doctors")
    fun getClinicDoctors(@PathVariable clinicId: Long): List<DoctorDto> {
        return clinicService.getClinicDoctors(clinicId)
    }
}