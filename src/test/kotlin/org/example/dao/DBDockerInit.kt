package org.example.dao

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.containers.PostgreSQLContainer
@SpringBootTest
@Sql("/dataSql/createDb.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql("/dataSql/deleteDb.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
abstract class DBDockerInit {

        companion object {
            val container: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:14.4")
            init {
                container.start()
            }
            @DynamicPropertySource
            @JvmStatic
            @Suppress("unused")
            fun properties(registry: DynamicPropertyRegistry) {
                registry.add("spring.datasource.url") { container.jdbcUrl }
                registry.add("spring.datasource.username") { container.username }
                registry.add("spring.datasource.password") { container.password }
            }
        }
}