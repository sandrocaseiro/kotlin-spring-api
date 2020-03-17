package com.sandrocaseiro.template.exceptions

class UsernameAlreadyExistsException : BaseException() {
    override val error = CustomErrors.USERNAME_ALREADY_EXISTS
}
