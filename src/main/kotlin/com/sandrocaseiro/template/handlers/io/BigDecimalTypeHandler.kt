package com.sandrocaseiro.template.handlers.io

import org.beanio.types.TypeHandler
import java.math.BigDecimal

class BigDecimalTypeHandler : TypeHandler {
    var decimals: Int = 2

    override fun parse(text: String?): Any {
        if (text.isNullOrBlank())
            return BigDecimal.ZERO

        val valor: BigDecimal = text.toBigDecimal()
        return if (decimals > 0) valor.movePointLeft(decimals) else valor
    }

    override fun format(value: Any?): String = value?.toString() ?: "0"

    override fun getType(): Class<*> = BigDecimal::class.java
}