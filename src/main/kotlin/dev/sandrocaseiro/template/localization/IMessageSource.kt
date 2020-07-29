package dev.sandrocaseiro.template.localization

import dev.sandrocaseiro.template.exceptions.AppErrors
import java.util.*

interface IMessageSource {
    fun getMessage(resource: String): String

    fun getMessage(resource: String, params: Array<*>?): String

    fun getMessage(error: AppErrors): String

    fun getMessage(error: AppErrors, params: Array<*>?): String

    fun getMessage(messages: Messages): String

    val currentLocale: Locale

    val currentLocaleTag: String
}
