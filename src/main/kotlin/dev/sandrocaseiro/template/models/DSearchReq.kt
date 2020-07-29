package dev.sandrocaseiro.template.models

import dev.sandrocaseiro.template.validations.NotEmpty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Model to search using sensitive information")
data class DSearchReq (
    @NotEmpty
    @Schema(description = "Search string", required = true, example = "29035196090")
    val searchContent: String?
)
