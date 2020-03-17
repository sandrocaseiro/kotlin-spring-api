package com.sandrocaseiro.template.repositories

import com.sandrocaseiro.template.models.domain.EUser
import com.sandrocaseiro.template.models.jpa.JUserGroup
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface UserRepository: BaseRepository<EUser, Int>, UserRepositoryCustom {
    @Modifying
    @Query("update User set balance = :balance, updateDate = SYSDATE where id = :id")
    fun updateBalance(id: Int, balance: BigDecimal): Int

    @Query("select u from User u where u.email = :username")
    fun findByUsername(username: String): EUser?

    @Query("select u.id as id, u.name as name, u.email as email, u.group.name as group from User u where u.id = :id")
    fun findOneById(id: Int): JUserGroup?

    @Query("select u from User u inner join fetch u.group g where lower(u.name) like lower(concat('%', :name, '%'))")
    fun searchByName(name: String): List<EUser>

    @Query("select u from User u inner join fetch u.group g where lower(u.cpf) like lower(concat('%', :cpf, '%'))")
    fun searchByCpf(cpf: String): List<EUser>

    @Query(
        value = "select u from User u inner join fetch u.group where u.active = true",
        countQuery = "select count(1) from User u where u.active = true"
    )
    fun findAllActive(pageable: Pageable): Page<EUser>

    @Query("select u from User u inner join fetch u.group")
    override fun findAll(): List<EUser>
}
