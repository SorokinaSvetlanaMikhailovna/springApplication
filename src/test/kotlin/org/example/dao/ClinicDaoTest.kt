package org.example.dao

import org.example.AddDataInDB
import org.example.Application
import org.example.entity.Clinic
import org.junit.Assert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.EntityManager


@RunWith(SpringRunner::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [Application::class]
)
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:application-integrationtest.properties"])
class ClinicDaoTest {

    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var clinicDao: ClinicDao

    @Autowired
    private lateinit var addDataInDB: AddDataInDB


    @Test
    fun addClinic() {
        try {
            val clinic = Clinic("clinic health", "jalan Padma")
            clinicDao.addClinic(clinic)
            Assert.assertEquals(
                1,
                entityManager.createQuery("select cl.name from Clinic cl where cl.name = 'clinic health'").resultList.size
            )
        } finally {
            addDataInDB.cleanAll()
        }
    }

    @Test
    fun addDoubleClinic() {
        try {
            val clinic = Clinic("clinic health", "jalan Padma")
            val clinic2 = Clinic("clinic health", "jalan Padma")
            clinicDao.addClinic(clinic)
            Assertions.assertThrows(
                Exception::class.java,
                {
                    clinicDao.addClinic(clinic2)
                }
            )
        } finally {
            addDataInDB.cleanAll()

        }
    }

    @Test
    fun findClinicById() {
        try {
            val clinic = Clinic("clinic health", "jalan Padma")
            clinicDao.addClinic(clinic)
            val id: Long =
                entityManager.createQuery("select cl.id from Clinic cl where cl.name = 'clinic health' and cl.address = 'jalan Padma'").singleResult as Long
            Assert.assertEquals("clinic health", clinicDao.findClinicById(id).name)
            Assert.assertEquals("jalan Padma", clinicDao.findClinicById(id).address)

        } finally {
            addDataInDB.cleanAll()
        }

    }

    @Test
    fun deleteClinic() {
    }

    @Test
    fun findDoctorsByClinicId() {
    }
}