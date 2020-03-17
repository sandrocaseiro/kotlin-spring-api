package com.sandrocaseiro.template.exceptions

class NotFoundException: BaseException() {
    override val error = CustomErrors.NOT_FOUND_ERROR
}
