package dev.sandrocaseiro.template.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("endpoints")
data class EndpointProperties(
    val cep: CepApi
) {
    data class CepApi(
        val baseUrl: String,
        val apiKey: String
    )
}
