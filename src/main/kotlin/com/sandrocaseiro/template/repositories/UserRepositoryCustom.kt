package com.sandrocaseiro.template.repositories

import com.sandrocaseiro.template.models.domain.EUser


interface UserRepositoryCustom {
    fun findByUsernameActive(username: String): EUser?

    fun findByGroup(groupId: Int): List<EUser>
}
