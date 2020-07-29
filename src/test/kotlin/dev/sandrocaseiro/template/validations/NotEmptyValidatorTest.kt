package dev.sandrocaseiro.template.validations

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.validation.ClockProvider
import javax.validation.ConstraintValidatorContext

class NotEmptyValidatorTest {
    private val validator = NotEmptyValidator()
    private val context = object: ConstraintValidatorContext {
        override fun buildConstraintViolationWithTemplate(messageTemplate: String?): ConstraintValidatorContext.ConstraintViolationBuilder {
            TODO("Not yet implemented")
        }
        override fun disableDefaultConstraintViolation() {
            TODO("Not yet implemented")
        }

        override fun getDefaultConstraintMessageTemplate(): String {
            TODO("Not yet implemented")
        }

        override fun getClockProvider(): ClockProvider {
            TODO("Not yet implemented")
        }

        override fun <T : Any?> unwrap(type: Class<T>?): T {
            TODO("Not yet implemented")
        }
    }

    @Test
    fun testNullIsNotValid() {
        assertThat(validator.isValid(null, context)).isFalse()
    }

    @Test
    fun testEmptyIsNotValid() {
        assertThat(validator.isValid(emptyList<String>(), context)).isFalse()
    }

    @Test
    fun testListIsValid() {
        assertThat(validator.isValid(listOf(1), context)).isTrue()
        assertThat(validator.isValid(listOf(1, 2, 3), context)).isTrue()
    }
}
