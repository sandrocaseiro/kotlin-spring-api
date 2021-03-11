package dev.sandrocaseiro.template.controllers

import dev.sandrocaseiro.template.mappers.toDCepResp
import dev.sandrocaseiro.template.models.DResponse
import dev.sandrocaseiro.template.models.api.ACep
import dev.sandrocaseiro.template.models.dto.cep.DCepResp
import dev.sandrocaseiro.template.models.dto.cep.DResponseDCepResp
import dev.sandrocaseiro.template.services.CepService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Cep", description = "Cep Operations")
class CepController(
    private val cepService: CepService
) {
    @GetMapping("/v1/cep/{cep}")
    @Operation(summary = "Update User", description = "Search an CEP address", responses = [
        ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = DResponseDCepResp::class))]),
        ApiResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun getCep(@Parameter(description = "Cep value", `in` = ParameterIn.PATH, required = true, example = "99999999") @PathVariable cep: String): DCepResp {
        val cepApi: ACep = cepService.searchCep(cep)

        return cepApi.toDCepResp()
    }
}