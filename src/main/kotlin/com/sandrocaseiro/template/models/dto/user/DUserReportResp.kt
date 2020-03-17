package com.sandrocaseiro.template.models.dto.user

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.LocalDateTime

data class DUserReportResp (
    val id: Int,
    val name: String,
    val email: String,
    val groupId: Int,
    @JsonProperty("balanceAmount")
    val balance: String,
    val rolesCount: Int,
    @JsonProperty("isActive")
    val active: Boolean,
    @JsonProperty("creationDateTime")
    val creationDateTime: LocalDateTime
) {
    val creationDate: LocalDate
        get() = creationDateTime.toLocalDate()

    val creationTime: LocalDate
        get() = creationDateTime.toLocalDate()
}
