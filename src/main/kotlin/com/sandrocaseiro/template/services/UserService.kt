package com.sandrocaseiro.template.services

import com.sandrocaseiro.template.clients.UserClient
import com.sandrocaseiro.template.exceptions.ForbiddenException
import com.sandrocaseiro.template.exceptions.ItemNotFoundException
import com.sandrocaseiro.template.exceptions.UsernameAlreadyExistsException
import com.sandrocaseiro.template.models.api.AUserByIdResp
import com.sandrocaseiro.template.models.api.AUserUpdateReq
import com.sandrocaseiro.template.models.domain.EUser
import com.sandrocaseiro.template.models.jpa.JUserGroup
import com.sandrocaseiro.template.models.service.SUser
import com.sandrocaseiro.template.repositories.UserRepository
import com.sandrocaseiro.template.security.IAuthenticationInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class UserService(
    private val authInfo: IAuthenticationInfo,
    private val userRepository: UserRepository,
    private val userApiClient: UserClient
) {
    fun create(user: EUser): EUser {
        if (userRepository.findByUsername(user.email) != null)
            throw UsernameAlreadyExistsException()

        user.active = true
        return userRepository.save(user)
    }

    @Transactional
    fun update(user: EUser) {
        val dbUser: EUser = userRepository.findById(user.id).orElseThrow { ItemNotFoundException() }

        dbUser.name = user.name
        dbUser.cpf = user.cpf
        dbUser.password = user.password
        dbUser.groupId = user.groupId
        dbUser.roles = user.roles

        userRepository.save(dbUser)
    }

    @Transactional
    fun updateBalance(id: Int, balance: BigDecimal) {
        val updatedUsers: Int = userRepository.updateBalance(id, balance)
        if (updatedUsers == 0)
            throw ItemNotFoundException()
    }

    fun updateBalanceApi(id: Int, balance: BigDecimal) {
        userApiClient.updateBalance(id, AUserUpdateReq(balance))
    }

    @Transactional
    fun delete(id: Int) {
        if (authInfo.getId() == id)
            throw ForbiddenException()

        val user: EUser = userRepository.findById(id).orElseThrow { ItemNotFoundException() }
        user.active = false

        userRepository.save(user)
    }

    fun findById(id: Int): JUserGroup = userRepository.findOneById(id) ?: throw ItemNotFoundException()

    fun findByIdApi(id: Int): SUser {
        val user: AUserByIdResp = userApiClient.getById(id).data ?: throw ItemNotFoundException()

        return SUser(user.id, user.name, user.email)
    }

    fun searchByName(name: String): List<EUser> =  userRepository.searchByName(name)

    fun searchByCpf(cpf: String): List<EUser> = userRepository.searchByCpf(cpf)

    fun findByGroup(groupId: Int): List<EUser> = userRepository.findByGroup(groupId)

    fun findAllActive(pageable: Pageable): Page<EUser> = userRepository.findAllActive(pageable)

    fun findAll(): List<EUser> = userRepository.findAll()
}
