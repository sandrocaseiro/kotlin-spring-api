package dev.sandrocaseiro.template.repositories

import dev.sandrocaseiro.template.models.domain.EGroup
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : BaseRepository<EGroup, Int> {

}
