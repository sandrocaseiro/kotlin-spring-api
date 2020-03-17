package com.sandrocaseiro.template.exceptions

class MethodNotAllowedException : BaseException() {
    override val error = CustomErrors.METHOD_NOT_ALLOWED_ERROR
}
