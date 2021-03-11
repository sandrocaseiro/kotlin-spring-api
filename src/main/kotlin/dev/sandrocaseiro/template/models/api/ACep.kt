package dev.sandrocaseiro.template.models.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ACep(
    val cep: String,
    val logradouro: String,
    val bairro: String,
    @JsonProperty("localidade")
    val cidade: String,
    val uf: String
)
