package dev.sandrocaseiro.template.repositories

import dev.sandrocaseiro.template.models.domain.EUser
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.ParameterMode
import javax.persistence.Query

@Repository
class UserRepositoryImpl(private val entityManager: EntityManager) : UserRepositoryCustom {
    override fun findByUsernameActive(username: String): EUser? {
        val query: Query = entityManager.createNativeQuery("select * from \"USER\" where email = :username and active = 1", EUser::class.java)
            .setParameter("username", username)

        return try {
            query.singleResult as EUser
        } catch (e: NoResultException) {
            null
        }
    }

    override fun findByGroup(groupId: Int): List<EUser> {
        val query = entityManager.createStoredProcedureQuery("sp_findByGroup", EUser::class.java)
            .registerStoredProcedureParameter("p_group_id", Int::class.java, ParameterMode.IN)
            .registerStoredProcedureParameter("p_result", Void::class.java, ParameterMode.REF_CURSOR)
            .setParameter("p_group_id", groupId)

        return query.resultList as List<EUser>
    }
}
