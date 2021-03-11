package dev.sandrocaseiro.template.clients.interceptors

import dev.sandrocaseiro.template.properties.EndpointProperties
import feign.RequestInterceptor
import feign.RequestTemplate

class ApiKeyRequestInterceptor (
    private val endpointProperties: EndpointProperties
) : RequestInterceptor {
    override fun apply(template: RequestTemplate) {
        template.header("X-API-KEY", endpointProperties.cep.apiKey)
    }
}
