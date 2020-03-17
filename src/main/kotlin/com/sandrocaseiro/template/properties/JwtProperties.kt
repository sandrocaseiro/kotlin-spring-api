package com.sandrocaseiro.template.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("jwt")
data class JwtProperties (
    val expiration: Long,
    val refreshExpiration: Long,
    val secret: String,
    val tokenPrefix: String,
    val header: String
)
