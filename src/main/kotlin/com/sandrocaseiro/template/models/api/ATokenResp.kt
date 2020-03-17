package com.sandrocaseiro.template.models.api

data class ATokenResp (
    val tokenType: String,
    val expiresIn: Long,
    val refreshExpiresIn: Long,
    val accessToken: String,
    val refreshToken: String
)
