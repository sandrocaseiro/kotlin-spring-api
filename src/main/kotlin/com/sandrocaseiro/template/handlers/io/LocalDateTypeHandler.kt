package com.sandrocaseiro.template.handlers.io

import com.sandrocaseiro.template.utils.toDate
import com.sandrocaseiro.template.utils.toDateString
import org.beanio.types.ConfigurableTypeHandler
import org.beanio.types.TypeHandler
import java.time.LocalDate
import java.util.*

class LocalDateTypeHandler : ConfigurableTypeHandler, Cloneable {
    var format = "ddMMyyyy"

    override fun parse(text: String?): Any? {
        return if (text.isNullOrBlank())
            null
        else
            text.toDate(format)
    }

    override fun format(value: Any?): String? {
        return if (value != null)
            (value as LocalDate).toDateString(format)
        else
            null
    }

    override fun getType(): Class<*> = LocalDate::class.java

    override fun newInstance(properties: Properties): TypeHandler {
        val customFormat = properties.getProperty("format")
        if (customFormat.isNullOrBlank())
            return this
        try {
            return (clone() as LocalDateTypeHandler).apply {
                format = customFormat
            }
        } catch (e: CloneNotSupportedException) {
            throw IllegalArgumentException("Invalid date format pattern '$customFormat': ${e.message}", e)
        }
    }
}
