package dev.sandrocaseiro.template.exceptions

import org.springframework.http.HttpStatus

enum class AppErrors(
    val httpStatus: HttpStatus,
    val code: Int,
    val messageRes: String
) {

    SUCCESS(HttpStatus.OK, 200, "error.success") {
        override fun throws() = throw AppException(this)
    },
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,500, "error.server") {
        override fun throws() = throw AppException(this)
    },
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, 400, "error.badrequest") {
        override fun throws() = throw AppException(this)
    },
    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED,401, "error.unauthorized") {
        override fun throws() = throw AppException(this)
    },
    FORBIDDEN_ERROR(HttpStatus.FORBIDDEN,403, "error.forbidden") {
        override fun throws() = throw AppException(this)
    },
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,404, "error.notfound") {
        override fun throws() = throw AppException(this)
    },
    METHOD_NOT_ALLOWED_ERROR(HttpStatus.METHOD_NOT_ALLOWED, 405, "error.methodnotallowed") {
        override fun throws() = throw AppException(this)
    },
    NOT_ACCEPTABLE_MEDIA_TYPE(HttpStatus.NOT_ACCEPTABLE, 406, "error.notacceptable") {
        override fun throws() = throw AppException(this)
    },
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, 415, "error.unsupportedmediatype") {
        override fun throws() = throw AppException(this)
    },
    TOKEN_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED,900, "error.tokenexpired") {
        override fun throws() = throw AppException(this)
    },
    INVALID_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, 901, "error.invalidtoken") {
        override fun throws() = throw AppException(this)
    },
    API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,902, "error.api") {
        override fun throws() = throw AppException(this)
    },
    ITEM_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, 903, "error.itemnotfound") {
        override fun throws() = throw AppException(this)
    },
    USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,904, "error.usernamealreadyexists") {
        override fun throws() = throw AppException(this)
    },
    BINDING_VALIDATION_ERROR(HttpStatus.UNPROCESSABLE_ENTITY, 905, "error.bindvalidation") {
        override fun throws() = throw AppException(this)
    },
    PAGEABLE_REQUEST_ERROR(HttpStatus.BAD_REQUEST, 906, "error.pageablerequest") {
        override fun throws() = throw AppException(this)
    },
    FILTER_REQUEST_ERROR(HttpStatus.BAD_REQUEST, 907, "error.filterrequest") {
        override fun throws() = throw AppException(this)
    },
    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, 909, "error.invalidcredentials") {
        override fun throws() = throw AppException(this)
    },
    ;

    abstract fun throws(): Nothing
}
