package com.sandrocaseiro.template.models.dto.user

data class DUserCreateResp (
    val id: Int,
    val name: String,
    val email: String,
    val groupId: Int,
    val roles: List<Int>
)
