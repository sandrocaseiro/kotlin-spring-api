package dev.sandrocaseiro.template.models.dto.user

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Schema(description = "Model to show how API must enforce value formats and property names")
data class DUserReportResp (
    @Schema(description = "User's id", example = "1")
    val id: Int,
    @Schema(description = "User's name", example = "user1")
    val name: String,
    @Schema(description = "User's email", example = "user1@mail.com")
    val email: String,
    @Schema(description = "User's group id", example = "1")
    val groupId: Int,
    @Schema(description = "User's balance. Should be preceded by the monetary code", example = "USD 155.75")
    val balance: String,
    @Schema(description = "User's roles count. Counters should have the sulfix 'Count'", example = "1")
    val rolesCount: Int,
    @JsonProperty("isActive")
    @Schema(description = "User's status", example = "true")
    val active: Boolean,
    @JsonProperty("creationDateTime")
    @Schema(description = "User's creation datetime. DateTime should have the sulfix 'DateTime' and the format 'uuuu-MM-ddTHH:mm:ssZ'", example = "2020-12-01T20:53:15Z")
    val creationDateTime: LocalDateTime
) {
    @Schema(description = "User's creation date. Date should have the sulfix 'Date' and the format 'uuuu-MM-dd'", example = "2020-12-01")
    var creationDate: LocalDate = creationDateTime.toLocalDate()
        private set

    @Schema(description = "User's creation time. Time should have the sulfix 'Time' and the format 'HH:mm:ss'", example = "20:53:15")
    var creationTime: LocalTime = creationDateTime.toLocalTime()
        private set
}
