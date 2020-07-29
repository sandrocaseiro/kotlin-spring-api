package dev.sandrocaseiro.template.models.dto.user

import dev.sandrocaseiro.template.models.DResponse
import io.swagger.v3.oas.annotations.media.Schema

data class DUserGroupResp (
    @Schema(description = "User's id", required = true, example = "1")
    val id: Int,
    @Schema(description = "User's name", required = true, example = "user1")
    val name: String,
    @Schema(description = "User's e-mail", required = true, example = "user1@mail.com")
    val email: String,
    @Schema(description = "User's group name", required = true, example = "Group 1")
    val group: String
)

@Schema(name = "DResponse<DUserGroupResp>", description = "Model to return user with group name")
class DResponseDUserGroupResp(errors: List<Error>, data: DUserGroupResp) : DResponse<DUserGroupResp>(errors, data)
