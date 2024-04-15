package app.micronaut.command

import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal
import java.time.Instant

@Serdeable
data class MeasurementSaveCommand(
    val sensorId: Int,
    val value: String
)
