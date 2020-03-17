package com.sandrocaseiro.template.exceptions

abstract class BaseException : RuntimeException {
    constructor() : super()

    //constructor(message: String) : super(message)

    constructor(ex: Throwable) : super(ex)

    abstract val error: CustomErrors
}
