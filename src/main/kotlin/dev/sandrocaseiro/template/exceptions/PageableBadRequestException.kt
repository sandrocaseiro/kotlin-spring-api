package dev.sandrocaseiro.template.exceptions

class PageableBadRequestException: BaseException {
    var fieldName: String = ""
        private set

    constructor(fieldName: String): super() {
        this.fieldName = fieldName
    }

    constructor(fieldName: String, e: Throwable): super(e) {
        this.fieldName = fieldName
    }

    override val error: AppErrors = AppErrors.PAGEABLE_REQUEST_ERROR
}
