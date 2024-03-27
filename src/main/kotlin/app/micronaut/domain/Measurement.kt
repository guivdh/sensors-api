package app.micronaut.domain

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation
import io.micronaut.serde.annotation.Serdeable
import java.time.Instant

@Serdeable
@MappedEntity("measurements")
data class Measurement(
    @field:Id
    @field:GeneratedValue
    var id: Long? = null,
    var sensorId: Long,
    var value: String,
    var measuredAt: Instant,

    @field:Relation(value = Relation.Kind.MANY_TO_ONE, cascade = [Relation.Cascade.PERSIST])
    var sensor: Sensor?
)
