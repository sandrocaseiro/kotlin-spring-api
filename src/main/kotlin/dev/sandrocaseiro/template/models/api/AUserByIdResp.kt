package dev.sandrocaseiro.template.models.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class AUserByIdResp (
    val id: Int,
    val name: String,
    val email: String
)
