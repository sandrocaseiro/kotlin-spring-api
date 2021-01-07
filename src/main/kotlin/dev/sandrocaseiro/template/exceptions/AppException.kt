package dev.sandrocaseiro.template.exceptions

class AppException: BaseException {
    private var errorType: AppErrors

    constructor(errorType: AppErrors, e: Throwable): super(e) {
        this.errorType = errorType
    }

    constructor(errorType: AppErrors): super() {
        this.errorType = errorType
    }

    override val error: AppErrors
        get() = errorType

    companion object {
        fun of(errorType: AppErrors) = AppException(errorType)

        fun of(errorType: AppErrors, e: Throwable) = AppException(errorType, e)
    }
}
