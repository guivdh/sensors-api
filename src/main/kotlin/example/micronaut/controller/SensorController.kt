package app.micronaut.controller

import app.micronaut.command.SensorUpdateCommand
import app.micronaut.domain.Sensor
import app.micronaut.repository.SensorRepository
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.Status
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import java.net.URI
import java.util.Optional
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank

@ExecuteOn(TaskExecutors.BLOCKING)
@Controller("/sensors")
open class SensorController(private val sensorRepository: SensorRepository) {

    @Get("/{id}")
    fun show(id: Long): Optional<Sensor> =
        sensorRepository.findById(id)

    @Put
    open fun update(@Body @Valid command: SensorUpdateCommand): HttpResponse<Sensor> {
        val id = sensorRepository.update(command.id, command.sensorName)

        return HttpResponse
            .noContent<Sensor>()
            .header(HttpHeaders.LOCATION, id.location.path)
    }

    @Get("/list")
    open fun list(@Valid pageable: Pageable): List<Sensor> =
        sensorRepository.findAll(pageable).content

    @Post
    open fun save(@Body("sensorName") @NotBlank sensorName: String): HttpResponse<Sensor> {
        val sensor = sensorRepository.save(sensorName)

        return HttpResponse
            .created(sensor)
            .headers { headers -> headers.location(sensor.location) }
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    fun delete(id: Long) = sensorRepository.deleteById(id)

    private val Long?.location: URI
        get() = URI.create("/sensors/$this")

    private val Sensor.location: URI
        get() = id.location
}
