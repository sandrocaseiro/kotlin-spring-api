package dev.sandrocaseiro.template.models.io

import dev.sandrocaseiro.template.utils.serializePrettyPrint
import java.time.LocalDate
import java.time.LocalTime

data class IArquivoExtratoHeader(
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
    val nomeEmpresa: String,
    val nomeBanco: String,
    val cnab2: String,
    val codRemessaRetorno: Int,
    val dataGeracao: LocalDate,
    val horaGeracao: LocalTime,
    val sequenciaNSA: Int,
    val nroVersaoLayout: Int,
    val densidade: Int,
    val reservadoBanco: String,
    val reservadoEmpresa: String,
    val cnab3: String?
) {
    override fun toString() = this.serializePrettyPrint() ?: ""
}
