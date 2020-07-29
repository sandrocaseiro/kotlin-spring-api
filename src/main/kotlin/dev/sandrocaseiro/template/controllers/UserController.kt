package dev.sandrocaseiro.template.controllers

import dev.sandrocaseiro.template.exceptions.BindValidationException
import dev.sandrocaseiro.template.mappers.*
import dev.sandrocaseiro.template.models.DPage
import dev.sandrocaseiro.template.models.DPageable
import dev.sandrocaseiro.template.models.DResponse
import dev.sandrocaseiro.template.models.DSearchReq
import dev.sandrocaseiro.template.models.domain.EUser
import dev.sandrocaseiro.template.models.dto.user.*
import dev.sandrocaseiro.template.models.jpa.JUserGroup
import dev.sandrocaseiro.template.models.service.SUser
import dev.sandrocaseiro.template.security.UserPrincipal
import dev.sandrocaseiro.template.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Tag(name = "User", description = "User Operations")
class UserController (
    private val userService: UserService
) {
    @PostMapping("/v1/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create User", description = "Create a new user", responses = [
        ApiResponse(responseCode = "201", description = "Created", content = [Content(schema = Schema(implementation = DResponseDUserCreateResp::class))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = DResponse::class))]),
        ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = [Content(schema = Schema(implementation = DResponse::class))]),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun createUser(@RequestBody @Valid dto: DUserCreateReq,
                   bindingErrors: Errors): DUserCreateResp {
        if (bindingErrors.hasErrors())
            throw BindValidationException(bindingErrors)

        val user: EUser = userService.create(dto.toEUser())
        return user.toCreateResp()
    }

    @PutMapping("/v1/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update User", description = "Update an user", responses = [
        ApiResponse(responseCode = "204", description = "Updated"),
        ApiResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
        ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = [Content(schema = Schema(implementation = DResponse::class))]),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun updateUser(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") @PathVariable id: Int,
                   @RequestBody @Valid dto: DUserUpdateReq,
                   bindingErrors: Errors) {
        if (bindingErrors.hasErrors())
            throw BindValidationException(bindingErrors)

        userService.update(dto.toEUser(id))
    }

    @PatchMapping("/v1/users/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isGroup(1)")
    @Operation(summary = "Update User's balance", description = "Update an user's balance", responses = [
        ApiResponse(responseCode = "204", description = "Updated"),
        ApiResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
        ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = [Content(schema = Schema(implementation = DResponse::class))]),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun updateUserBalance(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") @PathVariable id: Int,
                          @RequestBody @Valid dto: DUserBalanceUpdateReq,
                          bindingErrors: Errors) {
        if (bindingErrors.hasErrors())
            throw BindValidationException(bindingErrors)

        userService.updateBalance(id, dto.balance!!)
    }

    @PatchMapping("/v2/users/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isGroup(1)")
    @Operation(summary = "Update User's balance", description = "Update an user's balance", responses = [
        ApiResponse(responseCode = "204", description = "Updated"),
        ApiResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
        ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = [Content(schema = Schema(implementation = DResponse::class))]),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun updateUserBalanceApi(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") @PathVariable id: Int,
                             @RequestBody @Valid dto: DUserBalanceUpdateReq,
                             bindingErrors: Errors) {
        if (bindingErrors.hasErrors())
            throw BindValidationException(bindingErrors)

        userService.updateBalanceApi(id, dto.balance!!)
    }

    @DeleteMapping("/v1/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user", description = "Delete an user", responses = [
        ApiResponse(responseCode = "204", description = "Deleted"),
        ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema(implementation = DResponse::class))]),
        ApiResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun deleteUser(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") @PathVariable id: Int) {
        userService.delete(id)
    }

    @GetMapping("/v1/users")
    @Operation(summary = "Search users", description = "Search users by name", responses = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun searchUsers(@Parameter(description = "Search string", `in` = ParameterIn.QUERY, example = "user1")
                    @RequestParam("\$search") search: String?): List<DUserGroupResp> {
        val users: List<EUser> = userService.searchByName(search ?: "")
        return users.toListGroupDto()
    }

    //Sensitive search
    @PostMapping("/v1/users/search")
    @Operation(summary = "Search users", description = "Search users using sensitive information", responses = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = [Content(schema = Schema(implementation = DResponse::class))]),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun searchUsersSensitive(@RequestBody @Valid search: DSearchReq,
                             bindingErrors: Errors): List<DUserGroupResp> {
        if (bindingErrors.hasErrors())
            throw BindValidationException(bindingErrors)

        val users: List<EUser> = userService.searchByCpf(search.searchContent!!)
        return users.toListGroupDto()
    }

    //Get token user
    @GetMapping("/v1/users/current")
    @Operation(summary = "Get token's user", description = "Get current token's user information", responses = [
        ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = DResponseDUserGroupResp::class))]),
        ApiResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun findUser(@AuthenticationPrincipal principal: UserPrincipal): DUserGroupResp {
        val user: JUserGroup = userService.findById(principal.id)

        return user.toGroupDto()
    }

    @GetMapping("/v1/users/{id}")
    @PreAuthorize("canAccessUser(#id)")
    @Operation(summary = "Get user", description = "Get user by Id", responses = [
        ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = DResponseDUserGroupResp::class))]),
        ApiResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun findUser(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") @PathVariable id: Int): DUserGroupResp {
        val user: JUserGroup = userService.findById(id)

        return user.toGroupDto()
    }

    @GetMapping("/v2/users/{id}")
    @PreAuthorize("canAccessUser(#id)")
    @Operation(summary = "Get user", description = "Get user by Id", responses = [
        ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = DResponseDUserGroupResp::class))]),
        ApiResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun findUserApi(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") @PathVariable id: Int): DUserResp {
        val user: SUser = userService.findByIdApi(id)

        return user.toUserResp()
    }

    @GetMapping("/v1/users/group/{id}")
    @Operation(summary = "Get users by group", description = "Get all users by group id", responses = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun findAllByGroup(@Parameter(description = "Group Id", `in` = ParameterIn.PATH, required = true, example = "1") @PathVariable id: Int): List<DUserResp> {
        val users: List<EUser> = userService.findByGroup(id)

        return users.toListUserDto()
    }

    @GetMapping("/v1/users/active")
    @Operation(summary = "Get all active users", description = "Get all active users with paging", responses = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun findAll(pageable: DPageable): DPage<DUserGroupResp> {
        val users: Page<EUser> = userService.findAllActive(pageable.asPageable())

        return users.toDto { it.toGroupDto() }
    }

    @GetMapping("/v1/users/report")
    @Operation(summary = "Get all users", description = "Get a report for all users", responses = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun usersReport(): List<DUserReportResp> {
        val users: List<EUser> = userService.findAll()

        return users.toListReport("USD")
    }
}
