package dev.sandrocaseiro.template.models.dto.user

import dev.sandrocaseiro.template.validations.Cpf
import dev.sandrocaseiro.template.validations.NotEmpty
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Min
import javax.validation.constraints.Size

@Schema(description = "Model for user update")
data class DUserUpdateReq (
    @NotEmpty
    @get:Size(max = 50)
    @Schema(description = "User's name", required = true, example = "user1")
    val name: String?,

    @Cpf
    @NotEmpty
    @get:Size(max = 11)
    @Schema(description = "User's CPF", required = true, example = "29035196090")
    val cpf: String?,

    @NotEmpty
    @get:Size(max = 20)
    @Schema(description = "User's password", required = true, example = "1234")
    val password: String?,

    @NotEmpty
    @get:Min(1)
    @Schema(description = "User's Group Id", required = true, example = "1")
    val groupId: Int?,

    @NotEmpty
    @Schema(description = "User's Role id's", required = true, example = "[1,2]")
    val roles: List<Int>?
)
