package com.sandrocaseiro.template.controllers

import com.sandrocaseiro.template.exceptions.BindValidationException
import com.sandrocaseiro.template.mappers.*
import com.sandrocaseiro.template.models.DPage
import com.sandrocaseiro.template.models.DPageable
import com.sandrocaseiro.template.models.DSearchReq
import com.sandrocaseiro.template.models.domain.EUser
import com.sandrocaseiro.template.models.dto.user.*
import com.sandrocaseiro.template.models.jpa.JUserGroup
import com.sandrocaseiro.template.models.service.SUser
import com.sandrocaseiro.template.security.UserPrincipal
import com.sandrocaseiro.template.services.UserService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.annotation.RequestScope
import javax.validation.Valid

@RestController
@RequestScope
class UserController (
    private val userService: UserService
) {
    @PostMapping("/v1/users")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody @Valid dto: DUserCreateReq,
                   bindingErrors: Errors): DUserCreateResp {
        if (bindingErrors.hasErrors())
            throw BindValidationException(bindingErrors)

        val user: EUser = userService.create(dto.toEUser())
        return user.toCreateResp()
    }

    @PutMapping("/v1/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateUser(@PathVariable id: Int,
                   @RequestBody @Valid dto: DUserUpdateReq,
                   bindingErrors: Errors) {
        if (bindingErrors.hasErrors())
            throw BindValidationException(bindingErrors)

        userService.update(dto.toEUser(id))
    }

    @PatchMapping("/v1/users/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isGroup(1)")
    fun updateUserBalance(@PathVariable id: Int,
                          @RequestBody @Valid dto: DUserBalanceUpdateReq,
                          bindingErrors: Errors) {
        if (bindingErrors.hasErrors())
            throw BindValidationException(bindingErrors)

        userService.updateBalance(id, dto.balance!!)
    }

    @PatchMapping("/v2/users/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isGroup(1)")
    fun updateUserBalanceApi(@PathVariable id: Int,
                             @RequestBody @Valid dto: DUserBalanceUpdateReq,
                             bindingErrors: Errors) {
        if (bindingErrors.hasErrors())
            throw BindValidationException(bindingErrors)

        userService.updateBalanceApi(id, dto.balance!!)
    }

    @DeleteMapping("/v1/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: Int) {
        userService.delete(id)
    }

    @GetMapping("/v1/users")
    fun searchUsers(@RequestParam("\$search") search: String?): List<DUserGroupResp> {
        val users: List<EUser> = userService.searchByName(search ?: "")
        return users.toListGroupDto()
    }

    //Sensitive search
    @PostMapping("/v1/users/search")
    fun searchUsersSensitive(@RequestBody @Valid search: DSearchReq,
                             bindingErrors: Errors): List<DUserGroupResp> {
        if (bindingErrors.hasErrors())
            throw BindValidationException(bindingErrors)

        val users: List<EUser> = userService.searchByCpf(search.searchContent!!)
        return users.toListGroupDto()
    }

    //Get token user
    @GetMapping("/v1/users/current")
    fun findUser(@AuthenticationPrincipal principal: UserPrincipal): DUserGroupResp {
        val user: JUserGroup = userService.findById(principal.id)

        return user.toGroupDto()
    }

    @GetMapping("/v1/users/{id}")
    @PreAuthorize("canAccessUser(#id)")
    fun findUser(@PathVariable id: Int): DUserGroupResp {
        val user: JUserGroup = userService.findById(id)

        return user.toGroupDto()
    }

    @GetMapping("/v2/users/{id}")
    @PreAuthorize("canAccessUser(#id)")
    fun findUserApi(@PathVariable id: Int): DUserResp {
        val user: SUser = userService.findByIdApi(id)

        return user.toUserResp()
    }

    @GetMapping("/v1/users/group/{id}")
    fun findAllByGroup(@PathVariable id: Int): List<DUserResp> {
        val users: List<EUser> = userService.findByGroup(id)

        return users.toListUserDto()
    }

    @GetMapping("/v1/users/active")
    fun findAll(pageable: DPageable): DPage<DUserGroupResp> {
        val users: Page<EUser> = userService.findAllActive(pageable.asPageable())

        return users.toDto { it.toGroupDto() }
    }

    @GetMapping("/v1/users/report")
    fun usersReport(): List<DUserReportResp> {
        val users: List<EUser> = userService.findAll()

        return users.toListReport("BRL")
    }
}
