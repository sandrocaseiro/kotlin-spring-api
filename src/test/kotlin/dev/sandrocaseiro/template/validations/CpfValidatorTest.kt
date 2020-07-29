package dev.sandrocaseiro.template.validations

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CpfValidatorTest {
    private val validator = CpfValidator()

    @Test
    fun testNullValueIsNotValid() {
        assertThat(validator.isValid(null, null)).isFalse()
    }

    @Test
    fun testInvalidCpfShouldNotValidate() {
        assertThat(validator.isValid("", null)).isFalse()
        assertThat(validator.isValid("12345678912", null)).isFalse()
        assertThat(validator.isValid("1234", null)).isFalse()
        assertThat(validator.isValid("16352660097", null)).isFalse()
        assertThat(validator.isValid("999.233.790-78", null)).isFalse()
    }

    @Test
    fun testShouldValidateCorrectCpf() {
        assertThat(validator.isValid("01846941083", null)).isTrue()
        assertThat(validator.isValid("48231831002", null)).isTrue()
        assertThat(validator.isValid("44045178074", null)).isTrue()
    }
}
