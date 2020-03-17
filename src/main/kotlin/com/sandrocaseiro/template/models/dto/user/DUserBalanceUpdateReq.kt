package com.sandrocaseiro.template.models.dto.user

import java.math.BigDecimal
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class DUserBalanceUpdateReq (
    @NotNull @Min(0)
    val balance: BigDecimal? = null
)
