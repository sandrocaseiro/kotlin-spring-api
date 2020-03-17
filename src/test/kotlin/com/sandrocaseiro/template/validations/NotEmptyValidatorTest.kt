package com.sandrocaseiro.template.validations

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NotEmptyValidatorTest {
    private val validator = NotEmptyValidator()

    @Test
    fun testNullIsNotValid() {
        assertThat(validator.isValid(null, null)).isFalse()
    }

    @Test
    fun testEmptyIsNotValid() {
        assertThat(validator.isValid(emptyList<String>(), null)).isFalse()
    }

    @Test
    fun testListIsValid() {
        assertThat(validator.isValid(listOf(1), null)).isTrue()
        assertThat(validator.isValid(listOf(1, 2, 3), null)).isTrue()
    }
}
