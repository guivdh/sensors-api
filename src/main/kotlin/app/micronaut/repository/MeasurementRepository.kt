package app.micronaut.repository

import app.micronaut.domain.Measurement
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.Query
import io.micronaut.data.exceptions.DataAccessException
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository
import jakarta.transaction.Transactional
import java.math.BigDecimal
import java.time.Instant

@JdbcRepository(dialect = Dialect.MYSQL)
abstract class MeasurementRepository : PageableRepository<Measurement, Long> {

    abstract fun save(sensorId: Long, value: String, measuredAt: Instant): Measurement

    @Transactional
    open fun saveWithException(sensorId: Long, value: String, measuredAt: Instant): Measurement {
        save(sensorId, value, measuredAt)
        throw DataAccessException("test exception")
    }

    abstract fun update(@Id id: Long, sensorId: Long, value: String, measuredAt: Instant): Long

    @Query("SELECT * FROM measurements m LEFT JOIN sensors s ON s.id = m.sensor_id WHERE m.sensor_id = 1;")
    abstract fun customList(): List<Measurement>
}
