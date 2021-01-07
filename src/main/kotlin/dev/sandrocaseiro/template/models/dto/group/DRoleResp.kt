package dev.sandrocaseiro.template.models.dto.group

import io.swagger.v3.oas.annotations.media.Schema

data class DGroupResp(
    @Schema(description = "Group's id", example = "1")
    val id: Int,
    @Schema(description = "Group's name", example = "role1")
    val name: String
)
