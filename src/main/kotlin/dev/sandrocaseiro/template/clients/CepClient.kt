package dev.sandrocaseiro.template.clients

import dev.sandrocaseiro.template.clients.configs.CepClientConfiguration
import feign.Response
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable

import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "cep", url = "\${endpoints.cep.base-url}", configuration = [CepClientConfiguration::class])
interface CepClient {
    @GetMapping(value = ["/{cep}/json/"])
    fun buscarEnderecoPorCep(@PathVariable("cep") cep: String): Response
}
