package org.example.init_db

import org.example.entity.Clinic
import org.example.entity.Doctor
import org.example.entity.Patient
import org.example.entity.Specialization
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import java.time.LocalDate
import java.util.stream.Stream
import javax.annotation.PostConstruct
import javax.persistence.EntityManager
import javax.transaction.Transactional
import kotlin.random.Random

@Component
@org.springframework.context.annotation.Lazy(true)
class AddDataInDB(
    private val reader: Reader,
    private val entityManager: EntityManager,
    private val platformTransactionManager: PlatformTransactionManager
) {
    private fun doctor() {
        val count = 1000
        val random = Random(System.currentTimeMillis())
        for (i in (1..count)) {
            var listFirstName: MutableList<String> = mutableListOf()
            var listLastName: MutableList<String> = mutableListOf()
            val gender = random.nextBoolean()

            if (gender) {
                listFirstName = reader.womenFirstName
                listLastName = reader.womenLastName
            } else {
                listFirstName = reader.menFirstName
                listLastName = reader.menLastName
            }
            val randomFirstName = random.nextInt(listFirstName.size)
            val randomLastName = random.nextInt(listLastName.size)
            val randomBirthday = LocalDate.of(
                random.nextInt(1970, 2005),
                random.nextInt(1, 12),
                random.nextInt(1, 28))


            entityManager.persist(
                Doctor(
                    listFirstName.get(randomFirstName),
                    listLastName.get(randomLastName),
                    gender,
                    randomBirthday
                )
            )
        }

    }

    private fun clinic() {
        val countMax = Stream.of(reader.clinicName.size, reader.address.size).min(Integer::compare).get()
        for (i in (0..countMax - 1)) {
            val clinic: Clinic = Clinic(reader.clinicName.get(i), reader.address.get(i))
            entityManager.persist(clinic)
        }

    }

    private fun patient() {
        val count = 100000
        val random = Random(System.currentTimeMillis())
        for (i in (1..count)) {
            var listFirstName: MutableList<String> = mutableListOf()
            var listLastName: MutableList<String> = mutableListOf()
            val gender = random.nextBoolean()

            if (gender) {
                listFirstName = reader.womenFirstName
                listLastName = reader.womenLastName
            } else {
                listFirstName = reader.menFirstName
                listLastName = reader.menLastName
            }
            val randomFirstName = random.nextInt(listFirstName.size)
            val randomLastName = random.nextInt(listLastName.size)
            val randomBirthday = LocalDate.of(
                random.nextInt(1970, 2005),
                random.nextInt(1, 12),
                random.nextInt(1, 28)
            )
            entityManager.persist(
                Patient(
                    listFirstName.get(randomFirstName),
                    listLastName.get(randomLastName),
                    gender,
                    randomBirthday
                )
            )
        }
    }

    private fun specialization() {
        val count = reader.specialization.size
        for (i in (0..count - 1))
            entityManager.persist(Specialization(reader.specialization.get(i)))
    }

    private fun doctorSpecialization() {
        val random = Random(System.currentTimeMillis())
        val doctorSpecializations: MutableSet<Pair<Long, Long>> = mutableSetOf()
        val listDoctorId: List<Long> =
            entityManager.createQuery("select doc.id from Doctor doc").resultList as List<Long>
        val listSpecializationId: List<Long> =
            entityManager.createQuery("select sp.id from Specialization sp").resultList as List<Long>
        listDoctorId.forEach {
            doctorSpecializations.add(
                it to random.nextInt(
                    listSpecializationId[0].toInt(),
                    listSpecializationId[listSpecializationId.size - 1].toInt()
                ).toLong()
            )
        } //each doctor has at least one specialization


        val countMax = (listDoctorId.size * listSpecializationId.size) - listDoctorId.size
        val count: Int = listDoctorId.size * 2
        for (i in (0 until count)) {
            doctorSpecializations.add(
                Pair(
                    random.nextInt(
                        listDoctorId[0].toInt(),
                        listDoctorId[listSpecializationId.size - 1].toInt()
                    ).toLong(), random.nextInt(
                        listSpecializationId[0].toInt(),
                        listSpecializationId[listSpecializationId.size - 1].toInt()
                    ).toLong()
                )
            )

        }
        doctorSpecializations.forEach {
            entityManager.createNativeQuery("insert into doctor_specialization(doctor_id, specialization_id)values(:doctor, :specialization)")
                .setParameter("doctor", it.first)
                .setParameter("specialization", it.second).executeUpdate()
        }

    }

    private fun placeOfWork() {
    }

    private fun medicalCard() {}
    private fun workingDay() {}
    private fun vacation() {}
    private fun appointmentToDoctor() {}

    private fun users() {}
    private fun roles() {}
    private fun userRoles() {}

    @Transactional
    fun cleanAll() {
        entityManager.createNativeQuery("delete from doctor_specialization").executeUpdate()
        entityManager.createNativeQuery("delete from doctor").executeUpdate()
        entityManager.createNativeQuery("delete from clinic").executeUpdate()
        entityManager.createNativeQuery("delete from patient").executeUpdate()
        entityManager.createNativeQuery("delete from specialization").executeUpdate()
        entityManager.createNativeQuery("delete from place_of_work").executeUpdate()
        entityManager.createNativeQuery("delete from medical_card").executeUpdate()
        entityManager.createNativeQuery("delete from working_day").executeUpdate()
        entityManager.createNativeQuery("delete from vacation").executeUpdate()
        entityManager.createNativeQuery("delete from appointment_to_doctor").executeUpdate()
        entityManager.createNativeQuery("delete from users").executeUpdate()
        entityManager.createNativeQuery("delete from roles").executeUpdate()
        entityManager.createNativeQuery("delete from user_roles").executeUpdate()
    }

    @PostConstruct
    fun postConstruct() {
        reader.readeFile()

        TransactionTemplate(platformTransactionManager).execute {
            cleanAll()
        }
        TransactionTemplate(platformTransactionManager).execute {
            doctor()
        }
        TransactionTemplate(platformTransactionManager).execute {
            clinic()
        }
        TransactionTemplate(platformTransactionManager).execute {
            patient()
        }
        TransactionTemplate(platformTransactionManager).execute {
            specialization()
        }
        TransactionTemplate(platformTransactionManager).execute {
            doctorSpecialization()
        }


    }


}