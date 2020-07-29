package dev.sandrocaseiro.template.exceptions

abstract class BaseException: RuntimeException {
    constructor(): super()

    constructor(e: Throwable): super(e)

    abstract val error: AppErrors
}
