package com.sandrocaseiro.template.clients

import com.sandrocaseiro.template.models.AResponse
import com.sandrocaseiro.template.models.api.AUserByIdResp
import com.sandrocaseiro.template.models.api.AUserUpdateReq
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user", url = "\${endpoints.api1.base-url}")
interface UserClient {
    @PatchMapping(value = ["/v1/users/{id}/balance"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateBalance(@PathVariable("id") id: Int, user: AUserUpdateReq): AResponse<*>

    @GetMapping(value = ["/v1/users/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getById(@PathVariable("id") id: Int): AResponse<AUserByIdResp>
}
