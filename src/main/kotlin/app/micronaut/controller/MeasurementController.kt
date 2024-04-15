package app.micronaut.controller

import app.micronaut.command.MeasurementSaveCommand
import app.micronaut.command.MeasurementUpdateCommand
import app.micronaut.domain.Measurement
import app.micronaut.domain.MeasurementWithSensorName
import app.micronaut.repository.MeasurementRepository
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import java.net.URI
import java.util.Optional
import jakarta.validation.Valid
import java.time.Instant

@ExecuteOn(TaskExecutors.BLOCKING)
@Controller("/measurements")
open class MeasurementController(private val measurementRepository: MeasurementRepository) {

    @Get("/{id}")
    fun show(id: Int): Optional<Measurement> =
        measurementRepository.findById(id)

    @Put
    open fun update(@Body @Valid command: MeasurementUpdateCommand): HttpResponse<Measurement> {
        val id = measurementRepository.update(command.id, command.sensorId, command.value, command.measuredAt)

        return HttpResponse
            .noContent<Measurement>()
            .header(HttpHeaders.LOCATION, id.location.path)
    }

    @Get("/list")
    open fun list(@Valid pageable: Pageable): List<MeasurementWithSensorName> {
        val res = measurementRepository.findAllWithSensorName()
        return res
    }

    @Post
    open fun save(@Body command: MeasurementSaveCommand): HttpResponse<Measurement> {
        val date: Instant = Instant.now();
        val measurement = measurementRepository.save(command.sensorId, command.value, date)

        return HttpResponse
            .created(measurement)
            .headers { headers -> headers.location(measurement.location) }
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    fun delete(id: Int) = measurementRepository.deleteById(id)

    private val Int?.location: URI
        get() = URI.create("/measurements/$this")

    private val Measurement.location: URI
        get() = id.location
}