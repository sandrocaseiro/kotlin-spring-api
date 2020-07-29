package dev.sandrocaseiro.template.models.io

import dev.sandrocaseiro.template.utils.serializePrettyPrint

data class IArquivoExtratoTrailer(
    val codBanco: Int,
    val lote: Int,
    val tipoRegistro: Int,
    val cnab: String,
    val qtdLotes: Int,
    val qtdRegistros: Int,
    val qtdContas: Int,
    val cnab2: String?
) {
    override fun toString() = this.serializePrettyPrint() ?: ""
}
