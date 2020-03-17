package com.sandrocaseiro.template.models

import com.fasterxml.jackson.annotation.JsonValue

class DResponse<T>(
    val errors: List<Error>,
    val data: T
) {
    companion object {
        fun <T> ok(data: T, code: Int, message: String): DResponse<T> = DResponse(listOf(Error.success(code, message)), data)

        fun notOk(code: Int, message: String) = DResponse(listOf(Error.error(code, message)), null)

        fun notOk(errors: List<Error>) = DResponse(errors, null)
    }

    val isSuccess
        get() = errors.isEmpty() || errors.none { it.type != ErrorType.SUCCESS }

    class Error(
        val code: Int,
        val type: ErrorType,
        val description: String
    ) {
        companion object {
            fun error(code: Int, description: String): Error {
                return Error(code, ErrorType.ERROR, description)
            }

            fun warning(code: Int, description: String): Error {
                return Error(code, ErrorType.WARNING, description)
            }

            fun information(code: Int, description: String): Error {
                return Error(code, ErrorType.INFORMATION, description)
            }

            fun success(code: Int, description: String): Error {
                return Error(code, ErrorType.SUCCESS, description)
            }
        }
    }

    enum class ErrorType(@JsonValue val value: String) {
        ERROR("E"),
        WARNING("W"),
        INFORMATION("I"),
        SUCCESS("S")
    }
}
