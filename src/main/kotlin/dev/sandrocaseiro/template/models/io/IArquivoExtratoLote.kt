package dev.sandrocaseiro.template.models.io

import dev.sandrocaseiro.template.utils.serializePrettyPrint

data class IArquivoExtratoLote(
    val headerLote: IArquivoExtratoLoteHeader,
    val detalhes: List<IArquivoExtratoLoteDetalhe>,
    val trailerLote: IArquivoExtratoLoteTrailer
) {
    override fun toString() = this.serializePrettyPrint() ?: ""
}
