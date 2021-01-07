package dev.sandrocaseiro.template.models.dto.role

import io.swagger.v3.oas.annotations.media.Schema

data class DRoleResp(
    @Schema(description = "Role's id", example = "1")
    val id: Int,
    @Schema(description = "Role's name", example = "role1")
    val name: String
)
