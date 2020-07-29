package dev.sandrocaseiro.template.repositories

import dev.sandrocaseiro.template.models.domain.ERole
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : BaseRepository<ERole, Int> {
    @Query("select u.roles from User u where u.id = :id")
    fun findAllByUserId(id: Int): List<ERole>
}