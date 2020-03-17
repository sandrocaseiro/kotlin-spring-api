package com.sandrocaseiro.template.handlers.io

import com.sandrocaseiro.template.utils.toTime
import com.sandrocaseiro.template.utils.toTimeString
import org.beanio.types.ConfigurableTypeHandler
import org.beanio.types.TypeHandler
import java.time.LocalTime
import java.util.*

class LocalTimeTypeHandler : ConfigurableTypeHandler, Cloneable {
    var format = "ddMMyyyy"

    override fun parse(text: String?): Any? {
        return if (text.isNullOrBlank())
            null
        else
            text.toTime()
    }

    override fun format(value: Any?): String? {
        return if (value == null)
            null
        else
            (value as LocalTime).toTimeString(format)
    }

    override fun getType(): Class<*> = LocalTime::class.java

    override fun newInstance(properties: Properties): TypeHandler {
        val customFormat = properties.getProperty("format")
        if (customFormat.isNullOrBlank()) return this

        try {
            return (clone() as LocalTimeTypeHandler).apply {
                format = customFormat
            }
        } catch (e: CloneNotSupportedException) {
            throw IllegalArgumentException("Invalid time format pattern '$customFormat': ${e.message}", e)
        }
    }
}
