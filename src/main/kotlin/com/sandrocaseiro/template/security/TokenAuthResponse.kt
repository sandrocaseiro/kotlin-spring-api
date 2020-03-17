package com.sandrocaseiro.template.security

data class TokenAuthResponse (
    val tokenType: String,
    val expiresIn: Long,
    val refreshExpiresIn: Long,
    val accessToken: String,
    val refreshToken: String
)
