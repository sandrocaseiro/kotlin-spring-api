package dev.sandrocaseiro.template.controllers

import dev.sandrocaseiro.template.mappers.toListDRoleResp
import dev.sandrocaseiro.template.models.DResponse
import dev.sandrocaseiro.template.models.domain.ERole
import dev.sandrocaseiro.template.models.dto.role.DRoleResp
import dev.sandrocaseiro.template.services.RoleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Role", description = "Role Operations")
class RoleController(
    private val roleService: RoleService
) {
    @GetMapping("/v1/roles")
    @Operation(summary = "Get all roles", description = "Get all roles", responses = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun findAll(): List<DRoleResp> {
        val roles: List<ERole> = roleService.findAll()

        return roles.toListDRoleResp()
    }
}
