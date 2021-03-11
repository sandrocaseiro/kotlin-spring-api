package dev.sandrocaseiro.template.clients.configs

import dev.sandrocaseiro.template.clients.interceptors.ApiKeyRequestInterceptor
import dev.sandrocaseiro.template.properties.EndpointProperties
import org.springframework.context.annotation.Bean

class CepClientConfiguration (
    private val endpointProperties: EndpointProperties
) {
    @Bean
    fun tokenInterceptor(): ApiKeyRequestInterceptor = ApiKeyRequestInterceptor(endpointProperties)
}
