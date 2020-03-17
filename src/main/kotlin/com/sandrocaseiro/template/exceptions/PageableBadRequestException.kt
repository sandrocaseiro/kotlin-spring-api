package com.sandrocaseiro.template.exceptions

class PageableBadRequestException : BaseException {
    private var _fieldName: String = ""
    val fieldName: String
        get() = _fieldName

    constructor(fieldName: String) : super() {
        _fieldName = fieldName
    }

    constructor(fieldName: String, ex: Throwable) : super(ex) {
        _fieldName = fieldName
    }

    override val error = CustomErrors.PAGEABLE_REQUEST_ERROR
}
