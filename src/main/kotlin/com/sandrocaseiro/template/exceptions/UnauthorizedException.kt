package com.sandrocaseiro.template.exceptions

class UnauthorizedException : BaseException {
    constructor() : super()
    constructor(ex: Throwable) : super(ex)

    override val error = CustomErrors.UNAUTHORIZED_ERROR
}
