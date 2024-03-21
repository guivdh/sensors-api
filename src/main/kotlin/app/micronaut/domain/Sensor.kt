package app.micronaut.domain

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@MappedEntity("sensors")
data class Sensor(
    @field:Id
    @field:GeneratedValue
    var id: Long? = null,
    var sensorName: String,
    var sensorDescription: String? = null
)
