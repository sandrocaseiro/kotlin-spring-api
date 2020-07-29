package dev.sandrocaseiro.template.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("cors")
data class CorsProperties(
    val enabled: Boolean = false,
    val allowedMethods: List<String> = listOf(),
    val allowedHeaders: List<String> = listOf(),
    val exposedHeaders: List<String> = listOf(),
    val allowedOrigins: List<String> = listOf()
)
