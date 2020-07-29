package dev.sandrocaseiro.template.mappers

import dev.sandrocaseiro.template.models.domain.ERole
import dev.sandrocaseiro.template.models.domain.EUser
import dev.sandrocaseiro.template.models.dto.user.*
import dev.sandrocaseiro.template.models.jpa.JUserGroup
import dev.sandrocaseiro.template.models.service.SUser

fun DUserCreateReq.toEUser() = EUser().also {
    it.name = this.name!!
    it.email = this.email!!
    it.cpf = this.cpf!!
    it.password = this.password!!
    it.groupId = this.groupId!!
    it.roles = this.roles!!.map { r -> ERole().apply { id = r } }
}

fun DUserUpdateReq.toEUser(id: Int) = EUser().also {
    it.id = id
    it.name = this.name!!
    it.cpf = this.cpf!!
    it.password = this.password!!
    it.groupId = this.groupId!!
    it.roles = this.roles!!.map { r -> ERole().apply { this.id = r } }
}

fun EUser.toCreateResp() = DUserCreateResp(
    id = this.id,
    name = this.name,
    email = this.email,
    groupId = this.groupId,
    roles = this.roles.map { it.id }
)

fun EUser.toGroupDto() = DUserGroupResp(
    id = this.id,
    name = this.name,
    email = this.email,
    group = this.group?.name ?: ""
)

fun List<EUser>.toListGroupDto() = this.map { it.toGroupDto() }

fun SUser.toUserResp() = DUserResp(
    id = this.id,
    name = this.name,
    email = this.email
)

fun EUser.toUserDto() = DUserResp (
    id = this.id,
    name = this.name,
    email = this.email
)

fun List<EUser>.toListUserDto() = this.map { it.toUserDto() }

fun JUserGroup.toGroupDto() = DUserGroupResp(
    id = this.getId(),
    name = this.getName(),
    email = this.getEmail(),
    group = this.getGroup()
)

fun EUser.toReportResp(currency: String) = DUserReportResp(
    id = this.id,
    name = this.name,
    email = this.email,
    groupId = this.groupId,
    balance = "$currency ${this.balance}",
    rolesCount = this.roles.size,
    active = this.active,
    creationDateTime = this.creationDate
)

fun List<EUser>.toListReport(currency: String) = this.map { it.toReportResp(currency) }
