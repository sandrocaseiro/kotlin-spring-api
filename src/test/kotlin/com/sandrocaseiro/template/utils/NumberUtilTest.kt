package com.sandrocaseiro.template.utils

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test

class NumberUtilTest {
    @Test
    fun testIntParseNullOrEmptyShouldReturnDefaultValue() {
        assertThat(null.toInt(0)).isEqualTo(0)
        assertThat("".toInt(1)).isEqualTo(1)
    }

    @Test
    fun testIntParseInvalidShouldThrow() {
        assertThatExceptionOfType(NumberFormatException::class.java).isThrownBy { "aaa".toInt(0) }
        assertThatExceptionOfType(NumberFormatException::class.java).isThrownBy { "11,5".toInt(0) }
        assertThatExceptionOfType(NumberFormatException::class.java).isThrownBy { "11.5".toInt(0) }
    }

    @Test
    fun testIntParseShouldParseToInt() {
        assertThat("10".toInt(0)).isEqualTo(10)
        assertThat("22".toInt(0)).isEqualTo(22)
        assertThat("01".toInt(0)).isEqualTo(1)
    }
}
