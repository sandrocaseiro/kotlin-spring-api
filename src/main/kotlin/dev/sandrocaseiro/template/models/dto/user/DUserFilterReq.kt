package dev.sandrocaseiro.template.models.dto.user

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Model for user filter")
data class DUserFilterReq (
    @Schema(description = "User's name filter", example = "user1")
    val name: String
)
