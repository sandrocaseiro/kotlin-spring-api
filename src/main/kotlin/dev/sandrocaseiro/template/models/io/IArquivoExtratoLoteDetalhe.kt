package dev.sandrocaseiro.template.models.io

import dev.sandrocaseiro.template.utils.serializePrettyPrint
import java.math.BigDecimal
import java.time.LocalDate

data class IArquivoExtratoLoteDetalhe(
    val codBanco: Int,
    val lote: Int,
    val tipoRegistro: Int,
    val sequencia: Int,
    val segmento: String,
    val cnab: String,
    val tipoInscricao: Int,
    val nroInscricao: Long,
    val codConvenio: String,
    val nroAgencia: Int,
    val digitoAgencia: String,
    val nroConta: Long,
    val digitoConta: String,
    val digitoAgenciaConta: String,
    val nomeEmpresa: String,
    val cnab2: String,
    val natureza: String,
    val tipoComplemento: Int,
    val complemento: String,
    val cpmf: String,
    val dtaContabil: LocalDate,
    val dtaLancamento: LocalDate,
    val vlrLancamento: BigDecimal,
    val tipoLancamento: String,
    val categoriaLancamento: Int,
    val codHistorico: String,
    val descricaoHistorico: String,
    val nroDocumento: String?
) {
    override fun toString() = this.serializePrettyPrint() ?: ""
}
