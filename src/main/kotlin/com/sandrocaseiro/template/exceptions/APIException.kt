package com.sandrocaseiro.template.exceptions

class APIException : RuntimeException {
    constructor(message: String) : super(message)

    constructor(ex: Throwable) : super(ex)
}
