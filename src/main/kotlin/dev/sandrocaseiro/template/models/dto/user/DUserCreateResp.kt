package dev.sandrocaseiro.template.models.dto.user

import dev.sandrocaseiro.template.models.DResponse
import io.swagger.v3.oas.annotations.media.Schema

data class DUserCreateResp (
    @Schema(description = "User's id", example = "1")
    val id: Int,
    @Schema(description = "User's name", example = "user1")
    val name: String,
    @Schema(description = "User's e-mail", example = "user1@mail.com")
    val email: String,
    @Schema(description = "User's Group Id", example = "1")
    val groupId: Int,
    @Schema(description = "User's Role id's", example = "[1,2]")
    val roles: List<Int>
)

@Schema(name = "DResponse<DUserCreateResp>", description = "Response data for user created successfully")
class DResponseDUserCreateResp(errors: List<Error>, data: DUserCreateResp) : DResponse<DUserCreateResp>(errors, data)
