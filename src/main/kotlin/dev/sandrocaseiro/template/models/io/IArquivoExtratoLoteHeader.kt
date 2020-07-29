package dev.sandrocaseiro.template.models.io

import dev.sandrocaseiro.template.models.io.enums.IPosicaoSaldo
import dev.sandrocaseiro.template.models.io.enums.ISituacaoSaldo
import dev.sandrocaseiro.template.utils.serializePrettyPrint
import java.math.BigDecimal
import java.time.LocalDate

data class IArquivoExtratoLoteHeader(
    val codBanco: Int,
    val lote: Int,
    val tipoRegistro: Int,
    val tipoOperacao: String,
    val tipoServico: Int,
    val formaLancamento: Int,
    val layoutLote: Int,
    val cnab: String,
    val tipoInscricao: Int,
    val nroInscricao: Long,
    val codConvenio: String,
    val nroAgencia: Int,
    val digitoAgencia: String,
    val nroConta: String,
    val digitoConta: String,
    val digitoAgenciaConta: String,
    val nomeEmpresa: String,
    val cnab2: String,
    val dtaSaldo: LocalDate,
    val vlrSaldo: BigDecimal,
    val situacaoSaldo: ISituacaoSaldo,
    val posicaoSaldo: IPosicaoSaldo,
    val moeda: String,
    val sequencia: Int,
    val cnab3: String?
) {
    override fun toString() = this.serializePrettyPrint() ?: ""
}
