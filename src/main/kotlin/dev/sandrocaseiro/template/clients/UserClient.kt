package dev.sandrocaseiro.template.clients

import dev.sandrocaseiro.template.clients.configs.UserClientConfiguration
import dev.sandrocaseiro.template.models.api.AUserUpdateReq
import feign.Response
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user", url = "\${endpoints.api1.base-url}", configuration = [UserClientConfiguration::class])
interface UserClient {
    @PatchMapping(value = ["/v1/users/{id}/balance"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateBalance(@PathVariable("id") id: Int, user: AUserUpdateReq): Response

    @GetMapping(value = ["/v1/users/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getById(@PathVariable("id") id: Int): Response
}
