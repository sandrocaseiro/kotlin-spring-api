package dev.sandrocaseiro.template.exceptions

import org.springframework.validation.Errors

class BindValidationException(
    val bindErrors: Errors
): BaseException() {
    override val error: AppErrors = AppErrors.BINDING_VALIDATION_ERROR
}
