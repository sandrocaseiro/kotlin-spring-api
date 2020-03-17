package com.sandrocaseiro.template.models.dto.user

import com.sandrocaseiro.template.validations.Cpf
import com.sandrocaseiro.template.validations.NotEmpty
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class DUserUpdateReq (
    @NotNull
    @Size(max = 50)
    val name: String?,

    @Cpf
    @NotNull
    @Size(max = 11)
    val cpf: String?,

    @NotNull
    @Size(max = 20)
    val password: String?,

    @NotNull
    @Min(1)
    val groupId: Int?,

    @NotEmpty
    val roles: List<Int>?
)
