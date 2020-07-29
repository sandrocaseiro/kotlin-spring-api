package dev.sandrocaseiro.template.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonValue

@JsonIgnoreProperties(ignoreUnknown = true)
data class AResponse<T> (
    val isSuccess: Boolean = true,
    val errors: List<Error>,
    val data: T? = null
) {
    data class Error (
        val code: Int,
        val type: ErrorType,
        val description: String
    )

    enum class ErrorType (@JsonValue val value: String) {
        ERROR("E"),
        WARNING("W"),
        INFORMATION("I"),
        SUCCESS("S")
    }
}
