package com.sandrocaseiro.template.models

import javax.validation.constraints.NotNull

data class DSearchReq (
    @NotNull
    val searchContent: String?
)
