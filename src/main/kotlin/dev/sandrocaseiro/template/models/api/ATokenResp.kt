package dev.sandrocaseiro.template.models.api

import com.fasterxml.jackson.annotation.JsonProperty

data class ATokenResp (
    @JsonProperty("type")
    val tokenType: String,
    @JsonProperty("expires_in")
    val expiresIn: Long,
    @JsonProperty("refresh_expires_in")
    val refreshExpiresIn: Long,
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: String
)
