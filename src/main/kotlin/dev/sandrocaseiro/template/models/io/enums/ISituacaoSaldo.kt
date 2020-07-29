package dev.sandrocaseiro.template.models.io.enums

enum class ISituacaoSaldo(
    val nome: String
) {
    C("Credor"),
    D("Devedor"),
    ;

    val value = this.name
}
