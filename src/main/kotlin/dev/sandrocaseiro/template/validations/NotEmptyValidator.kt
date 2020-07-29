package dev.sandrocaseiro.template.validations

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NotEmptyValidator : ConstraintValidator<NotEmpty, Any?> {
    override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
        if (value == null) return false
        else if (value is List<*>) return !value.isEmpty()
        else if (value is Map<*, *>) return !value.isEmpty()
        else if (value is String) return !(value as String?).isNullOrEmpty()

        return true
    }
}
