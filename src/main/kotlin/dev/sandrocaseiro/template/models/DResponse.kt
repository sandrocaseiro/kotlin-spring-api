package dev.sandrocaseiro.template.models

import com.fasterxml.jackson.annotation.JsonValue
import io.swagger.v3.oas.annotations.media.Schema

open class DResponse<T>(
    @Schema(description = "List of the operations results")
    val errors: List<Error>,
    @Schema(description = "Endpoint response data", nullable = true)
    val data: T
) {
    companion object {
        fun <T> ok(data: T, code: Int, message: String): DResponse<T> = DResponse(listOf(Error.success(code, message)), data)

        fun notOk(code: Int, message: String) = DResponse(listOf(Error.error(code, message)), null)

        fun notOk(errors: List<Error>) = DResponse(errors, null)
    }

    @Schema(description = "Operation was successful")
    var isSuccess = errors.isEmpty() || errors.none { it.type != ErrorType.SUCCESS }
        private set

    class Error(
        @Schema(description = "Code of the operation result", example = "500")
        val code: Int,
        @Schema(description = "Type of the operation result")
        val type: ErrorType,
        @Schema(description = "Description of the operation result", example = "Server error")
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
