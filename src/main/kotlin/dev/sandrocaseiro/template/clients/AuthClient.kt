package dev.sandrocaseiro.template.clients

import dev.sandrocaseiro.template.models.api.ATokenReq
import dev.sandrocaseiro.template.models.api.ATokenResp
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(name = "auth", url = "\${endpoints.api1.base-url}")
interface AuthClient {
    @PostMapping(value = ["/v1/token"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun token(req: ATokenReq): ATokenResp
}
