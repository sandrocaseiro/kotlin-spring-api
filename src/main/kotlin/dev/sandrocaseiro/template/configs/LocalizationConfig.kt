package dev.sandrocaseiro.template.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.Locale

@Configuration
class LocalizationConfig {
    @Value("\${locale.default}")
    var defaultLocale: String? = null

    @Bean
    fun messageSource(): MessageSource {
        return ReloadableResourceBundleMessageSource().apply {
            setBasename("classpath:messages")
            setDefaultEncoding("UTF-8")
        }
    }

    @Bean
    fun localeResolver(): LocaleResolver {
        val locale = if (defaultLocale.isNullOrEmpty()) Locale.US else Locale.forLanguageTag(defaultLocale)
        Locale.setDefault(locale)

        return AcceptHeaderLocaleResolver().apply {
            defaultLocale = locale
        }
    }

    @Bean
    fun validator(): LocalValidatorFactoryBean {
        return LocalValidatorFactoryBean().apply {
            setValidationMessageSource(messageSource())
        }
    }
}
