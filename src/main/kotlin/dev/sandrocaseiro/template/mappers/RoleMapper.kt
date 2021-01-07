package dev.sandrocaseiro.template.mappers

import dev.sandrocaseiro.template.models.domain.ERole
import dev.sandrocaseiro.template.models.dto.role.DRoleResp


fun ERole.toDRoleResp() = DRoleResp(
    id = this.id,
    name = this.name
)

fun List<ERole>.toListDRoleResp() = this.map { it.toDRoleResp() }
