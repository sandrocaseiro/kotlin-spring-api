package dev.sandrocaseiro.template.models.io

import dev.sandrocaseiro.template.utils.serializePrettyPrint

data class IArquivoExtrato(
    val header: IArquivoExtratoHeader,
    val lotes: List<IArquivoExtratoLote>,
    val trailer: IArquivoExtratoTrailer
) {
    override fun toString() = this.serializePrettyPrint() ?: ""
}
