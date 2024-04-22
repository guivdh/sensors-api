package app.micronaut.domain


import io.micronaut.serde.annotation.Serdeable
import java.time.Instant

@Serdeable
data class MeasurementWithSensorName(
    val id: Int?,
    val sensor_id: Int,
    val value: String,
    val measured_at: Instant,
    val sensor_name: String,
)
