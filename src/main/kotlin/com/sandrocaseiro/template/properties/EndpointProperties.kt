package com.sandrocaseiro.template.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("endpoints")
data class EndpointProperties(
    val api1: API
) {
    data class API(
        val baseUrl: String,
        val username: String,
        val password: String
    )
}
