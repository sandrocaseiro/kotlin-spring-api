package dev.sandrocaseiro.template.clients.configs

import dev.sandrocaseiro.template.clients.AuthClient
import dev.sandrocaseiro.template.clients.interceptors.TokenRequestInterceptor
import dev.sandrocaseiro.template.properties.EndpointProperties
import org.springframework.context.annotation.Bean

class UserClientConfiguration (
    private val authClient: AuthClient,
    private val endpointProperties: EndpointProperties
) {
    @Bean
    fun tokenInterceptor(): TokenRequestInterceptor = TokenRequestInterceptor(authClient, endpointProperties)
}
