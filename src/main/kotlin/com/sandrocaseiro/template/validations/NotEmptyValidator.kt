package com.sandrocaseiro.template.validations

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NotEmptyValidator : ConstraintValidator<NotEmpty, List<*>> {
    override fun isValid(value: List<*>?, context: ConstraintValidatorContext?): Boolean
        = value != null&& value.isNotEmpty()
}
