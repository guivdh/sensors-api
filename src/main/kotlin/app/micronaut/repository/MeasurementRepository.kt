package app.micronaut.repository

import app.micronaut.domain.Measurement
import app.micronaut.domain.MeasurementWithSensorName
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.Join
import io.micronaut.data.annotation.Query
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository
import java.time.Instant

@JdbcRepository(dialect = Dialect.MYSQL)
abstract class MeasurementRepository : PageableRepository<Measurement, Int> {

    abstract fun save(sensorId: Int, value: String, measuredAt: Instant): Measurement

    abstract fun update(@Id id: Int, sensorId: Int, value: String, measuredAt: Instant): Int

    @Join("sensor")
    @Query(
        value = "SELECT m.id, m.sensor_id, m.value, m.measured_at, m.value, s.sensor_name\n" +
                "FROM measurements m\n" +
                "JOIN sensors s ON m.sensor_id = s.id;",
        nativeQuery = true
    )
    abstract fun findAllWithSensorName(): List<MeasurementWithSensorName>

}
