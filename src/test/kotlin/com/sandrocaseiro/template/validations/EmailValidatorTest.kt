package com.sandrocaseiro.template.validations

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EmailValidatorTest {
    private val validator = EmailValidator()

    @Test
    fun testNullValueIsNotValid() {
        assertThat(validator.isValid(null, null)).isFalse()
    }

    @Test
    fun testInvalidEmailShouldNotValidate() {
        assertThat(validator.isValid("", null)).isFalse()
        assertThat(validator.isValid("testmail.com", null)).isFalse()
        assertThat(validator.isValid("@mail.com", null)).isFalse()
        assertThat(validator.isValid(".test@mail.com", null)).isFalse()
    }

    @Test
    fun testShouldValidateCorrectEmail() {
        assertThat(validator.isValid("test@mail.com", null)).isTrue()
        assertThat(validator.isValid("test@mail.com.br", null)).isTrue()
    }
}
