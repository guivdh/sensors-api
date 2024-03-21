package app.micronaut.command

import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Serdeable
data class SensorUpdateCommand(
    val id: Long,
    @field:NotBlank val sensorName: String,
    val sensorDescription: String? = null
)
