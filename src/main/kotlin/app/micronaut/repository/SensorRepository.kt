package app.micronaut.repository

import app.micronaut.domain.Sensor
import io.micronaut.data.annotation.Id
import io.micronaut.data.exceptions.DataAccessException
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository
import jakarta.transaction.Transactional
import jakarta.validation.constraints.NotBlank

@JdbcRepository(dialect = Dialect.MYSQL)
abstract class SensorRepository : PageableRepository<Sensor, Long> {

    abstract fun save(@NotBlank sensorName: String): Sensor

    @Transactional
    open fun saveWithException(@NotBlank sensorName: String): Sensor {
        save(sensorName)
        throw DataAccessException("test exception")
    }

    abstract fun update(@Id id: Long, @NotBlank sensorName: String): Long

    @Transactional
    open fun addSensor(sensorName: String): Sensor {
        return save(sensorName)
    }
}
