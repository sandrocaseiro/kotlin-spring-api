package com.sandrocaseiro.template.exceptions

import org.springframework.http.HttpStatus

enum class CustomErrors(
    val httpStatus: HttpStatus,
    val code: Int,
    val messageRes: String
) {
    SUCCESS(HttpStatus.OK, 1, "error.success"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,500, "error.server"),
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, 400, "error.badrequest"),
    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED,401, "error.unauthorized"),
    FORBIDDEN_ERROR(HttpStatus.FORBIDDEN,403, "error.forbidden"),
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,404, "error.notfound"),
    METHOD_NOT_ALLOWED_ERROR(HttpStatus.METHOD_NOT_ALLOWED, 405, "error.methodnotallowed"),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, 415, "error.unsupportedmediatype"),
    TOKEN_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED,900, "error.tokenexpired"),
    INVALID_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, 901, "error.invalidtoken"),
    API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,902, "error.api"),
    ITEM_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, 903, "error.itemnotfound"),
    USERNAME_ALREADY_EXISTS(HttpStatus.UNPROCESSABLE_ENTITY,904, "error.usernamealreadyexists"),
    BINDING_VALIDATION_ERROR(HttpStatus.UNPROCESSABLE_ENTITY, 905, "error.bindvalidation"),
    PAGEABLE_REQUEST_ERROR(HttpStatus.BAD_REQUEST, 906, "error.pageablerequest"),
    FILTER_REQUEST_ERROR(HttpStatus.BAD_REQUEST, 907, "error.filterrequest")
}
