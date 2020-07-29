package dev.sandrocaseiro.template.repositories

import dev.sandrocaseiro.template.models.domain.EUser

interface UserRepositoryCustom {
    fun findByUsernameActive(username: String): EUser?

    fun findByGroup(groupId: Int): List<EUser>
}
