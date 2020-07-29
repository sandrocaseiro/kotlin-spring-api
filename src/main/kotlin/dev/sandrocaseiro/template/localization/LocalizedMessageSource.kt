package dev.sandrocaseiro.template.localization

import dev.sandrocaseiro.template.exceptions.AppErrors
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class LocalizedMessageSource(
    private val messageSource: MessageSource
): IMessageSource {
    override fun getMessage(resource: String): String = getMessage(resource, null)

    override fun getMessage(resource: String, params: Array<*>?): String = messageSource.getMessage(resource, params, currentLocale)

    override fun getMessage(error: AppErrors): String = getMessage(error, null)

    override fun getMessage(error: AppErrors, params: Array<*>?): String = getMessage(error.messageRes, params)

    override fun getMessage(messages: Messages): String = getMessage(messages.value, null)

    override val currentLocale: Locale
        get() = LocaleContextHolder.getLocale()

    override val currentLocaleTag: String
        get() = LocaleContextHolder.getLocale().toLanguageTag()
}
