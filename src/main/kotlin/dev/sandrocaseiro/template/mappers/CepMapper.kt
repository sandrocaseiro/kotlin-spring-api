package dev.sandrocaseiro.template.mappers

import dev.sandrocaseiro.template.models.api.ACep
import dev.sandrocaseiro.template.models.dto.cep.DCepResp

fun ACep.toDCepResp() = DCepResp(
    cep = this.cep,
    logradouro = this.logradouro,
    bairro = this.bairro,
    cidade = this.cidade,
    uf = this.uf
)
