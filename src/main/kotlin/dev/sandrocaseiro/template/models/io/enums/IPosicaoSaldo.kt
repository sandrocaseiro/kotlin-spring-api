package dev.sandrocaseiro.template.models.io.enums

enum class IPosicaoSaldo(
    val nome: String
) {
    F("Final"),
    P("Parcial"),
    I("Intra-Dia"),
    ;

    val value = this.name
}
