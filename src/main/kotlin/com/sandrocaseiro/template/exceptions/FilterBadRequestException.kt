package com.sandrocaseiro.template.exceptions

class FilterBadRequestException : BaseException {
    private var _fieldName: String = ""
    val fieldName: String
        get() = _fieldName

    constructor(fieldName: String) : super() {
        _fieldName = fieldName
    }

    constructor(fieldName: String, ex: Throwable) : super(ex) {
        _fieldName = fieldName
    }

    override val error = CustomErrors.FILTER_REQUEST_ERROR
}
