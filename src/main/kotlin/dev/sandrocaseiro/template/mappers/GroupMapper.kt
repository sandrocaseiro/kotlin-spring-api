package dev.sandrocaseiro.template.mappers

import dev.sandrocaseiro.template.models.domain.EGroup
import dev.sandrocaseiro.template.models.dto.role.DRoleResp


fun EGroup.toDGroupResp() = DRoleResp(
    id = this.id,
    name = this.name
)

fun List<EGroup>.toListDGroupResp() = this.map { it.toDGroupResp() }
