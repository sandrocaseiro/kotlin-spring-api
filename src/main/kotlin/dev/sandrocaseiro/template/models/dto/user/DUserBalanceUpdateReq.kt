package dev.sandrocaseiro.template.models.dto.user

import dev.sandrocaseiro.template.validations.NotEmpty
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import javax.validation.constraints.Min

@Schema(description = "Model for updating user's balance")
data class DUserBalanceUpdateReq (
    @NotEmpty @get:Min(0)
    @Schema(description = "User's new balance", required = true, example = "55.79")
    val balance: BigDecimal? = null
)
