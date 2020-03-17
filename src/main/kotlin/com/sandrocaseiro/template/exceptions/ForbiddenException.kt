package com.sandrocaseiro.template.exceptions

class ForbiddenException : BaseException() {
    override val error = CustomErrors.FORBIDDEN_ERROR
}
