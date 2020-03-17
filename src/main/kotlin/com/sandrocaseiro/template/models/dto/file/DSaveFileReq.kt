package com.sandrocaseiro.template.models.dto.file

import org.springframework.web.multipart.MultipartFile

data class DSaveFileReq (
    val id: Int,
    val name: String,
    val attachment: MultipartFile
)
