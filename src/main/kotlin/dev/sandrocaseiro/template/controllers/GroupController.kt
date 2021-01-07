package dev.sandrocaseiro.template.controllers

import dev.sandrocaseiro.template.mappers.toListDGroupResp
import dev.sandrocaseiro.template.models.DResponse
import dev.sandrocaseiro.template.models.domain.EGroup
import dev.sandrocaseiro.template.models.dto.role.DRoleResp
import dev.sandrocaseiro.template.services.GroupService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Group", description = "Group Operations")
class GroupController(
    private val groupService: GroupService
) {
    @GetMapping("/v1/groups")
    @Operation(summary = "Get all groups", description = "Get all groups", responses = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun findAll(): List<DRoleResp> {
        val groups: List<EGroup> = groupService.findAll()

        return groups.toListDGroupResp()
    }
}
