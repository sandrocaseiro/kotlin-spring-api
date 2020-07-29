package dev.sandrocaseiro.template.exceptions

class FilterBadRequestException(
    val fieldName: String,
    val e: Throwable
): BaseException(e) {
    override val error: AppErrors = AppErrors.FILTER_REQUEST_ERROR
}
