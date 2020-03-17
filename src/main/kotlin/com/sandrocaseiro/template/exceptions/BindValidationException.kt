package com.sandrocaseiro.template.exceptions

import org.springframework.validation.Errors

class BindValidationException(val bindErrors: Errors) : BaseException() {
    override val error: CustomErrors = CustomErrors.BINDING_VALIDATION_ERROR
}
