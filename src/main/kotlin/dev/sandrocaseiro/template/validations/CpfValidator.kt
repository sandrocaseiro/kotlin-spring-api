package dev.sandrocaseiro.template.validations

import java.util.*
import java.util.regex.Pattern
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class CpfValidator : ConstraintValidator<Cpf, String> {
    private val CPF_PATTERN = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})")

    private val CPF_LENGTH = 11
    private val CHAR_OFFSET = 48
    private val DIGIT_1 = 9
    private val DIGIT_2 = 10

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null || value.length != CPF_LENGTH || !CPF_PATTERN.matcher(value).matches())
            return false

        return try {
            calculateDigit(value, DIGIT_1, CPF_LENGTH - 1) == value[DIGIT_1]
                && calculateDigit(value, DIGIT_2, CPF_LENGTH) == value[DIGIT_2]
        } catch (ex: InputMismatchException) {
            false
        }
    }

    private fun calculateDigit(cpf: String, pos: Int, weight: Int): Char {
        var newWeight = weight
        var sm = 0
        for (i in 0 until pos) {
            val num: Int = cpf[i].toInt() - CHAR_OFFSET
            sm += num * newWeight
            newWeight -= 1
        }
        val r: Int = CPF_LENGTH - sm % CPF_LENGTH
        return if (r == CPF_LENGTH - 1 || r == CPF_LENGTH) '0' else (r + CHAR_OFFSET).toChar()
    }
}