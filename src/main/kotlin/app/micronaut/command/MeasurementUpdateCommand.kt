package app.micronaut.command

import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal
import java.time.Instant

@Serdeable
data class MeasurementUpdateCommand(
    val id: Long,
    val sensorId: Long,
    val value: String,
    val measuredAt: Instant
)
