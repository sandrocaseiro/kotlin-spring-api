package dev.sandrocaseiro.template.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("info")
class InfoProperties(
    val app: App
) {
    data class App(
        val id: String
    )
}
