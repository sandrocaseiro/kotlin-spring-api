package dev.sandrocaseiro.template.clients.interceptors

import dev.sandrocaseiro.template.clients.AuthClient
import dev.sandrocaseiro.template.models.api.ATokenReq
import dev.sandrocaseiro.template.models.api.ATokenResp
import dev.sandrocaseiro.template.properties.EndpointProperties
import feign.RequestInterceptor
import feign.RequestTemplate

class TokenRequestInterceptor (
    private val authClient: AuthClient,
    private val endpointProperties: EndpointProperties
) : RequestInterceptor {
    override fun apply(template: RequestTemplate) {
        val tokenResp: ATokenResp = authClient.token(ATokenReq(endpointProperties.api1.username, endpointProperties.api1.password))
        template.header("Authorization", "Bearer ${tokenResp.accessToken}")
    }
}
