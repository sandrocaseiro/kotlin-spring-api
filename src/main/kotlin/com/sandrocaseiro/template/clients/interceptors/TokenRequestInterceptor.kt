package com.sandrocaseiro.template.clients.interceptors

import com.sandrocaseiro.template.clients.AuthClient
import com.sandrocaseiro.template.models.api.ATokenReq
import com.sandrocaseiro.template.models.api.ATokenResp
import com.sandrocaseiro.template.properties.EndpointProperties
import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.stereotype.Component

@Component
class TokenRequestInterceptor (
    private val authClient: AuthClient,
    private val endpointProperties: EndpointProperties
) : RequestInterceptor {
    override fun apply(template: RequestTemplate) {
        val tokenResp: ATokenResp = authClient.token(ATokenReq(endpointProperties.api1.username, endpointProperties.api1.password));
        template.header("Authorization", "Bearer ${tokenResp.accessToken}");
    }
}
