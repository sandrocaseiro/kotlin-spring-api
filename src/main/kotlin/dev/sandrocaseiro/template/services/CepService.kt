package dev.sandrocaseiro.template.services

import com.fasterxml.jackson.databind.JsonNode
import dev.sandrocaseiro.template.clients.CepClient
import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.models.api.ACep
import dev.sandrocaseiro.template.utils.deserialize
import dev.sandrocaseiro.template.utils.deserializeTree
import feign.Response
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class CepService(
    private val cepClient: CepClient
) {
    fun searchCep(cep: String): ACep {
        val resp: Response = cepClient.buscarEnderecoPorCep(cep)
        if (HttpStatus.valueOf(resp.status()).isError)
            AppErrors.API_ERROR.throws()

        val node: JsonNode? = resp.body().deserializeTree()
        if (node == null || node.has("erro"))
            AppErrors.ITEM_NOT_FOUND_ERROR.throws()

        return node.deserialize()
    }
}
