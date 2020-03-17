package com.sandrocaseiro.template.exceptions

class InvalidTokenException : BaseException() {
    override val error = CustomErrors.INVALID_TOKEN_ERROR
}
