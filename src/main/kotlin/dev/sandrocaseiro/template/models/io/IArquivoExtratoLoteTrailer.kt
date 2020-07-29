package dev.sandrocaseiro.template.models.io

import dev.sandrocaseiro.template.models.io.enums.IPosicaoSaldo
import dev.sandrocaseiro.template.models.io.enums.ISituacaoSaldo
import dev.sandrocaseiro.template.utils.serializePrettyPrint
import java.math.BigDecimal
import java.time.LocalDate

data class IArquivoExtratoLoteTrailer(
    val codBanco: Int,
    val lote: Int,
    val tipoRegistro: Int,
    val cnab: String,
    val tipoInscricao: Int,
    val nroInscricao: Long,
    val codConvenio: String,
    val nroAgencia: Int,
    val digitoAgencia: String,
    val nroConta: Long,
    val digitoConta: String,
    val digitoAgenciaConta: String,
    val cnab2: String,
    val vlrBloqueadoAcima24h: BigDecimal,
    val vlrLimite: BigDecimal,
    val vlrBloqueado24h: BigDecimal,
    val dtaSaldo: LocalDate,
    val vlrSaldo: BigDecimal,
    val situacaoSaldo: ISituacaoSaldo,
    val posicaoSaldo: IPosicaoSaldo,
    val qtdRegistros: Int,
    val vlrDebitos: BigDecimal,
    val vlrCreditos: BigDecimal,
    val cnab3: String?
) {
    override fun toString() = this.serializePrettyPrint() ?: ""
}
