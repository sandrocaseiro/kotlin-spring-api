package dev.sandrocaseiro.template.models.dto.file

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile

@Schema(description = "Model for file upload")
data class DSaveFileReq (
    @Schema(description = "File id", required = true, example = "1")
    val id: Int,
    @Schema(description = "File name", required = true, example = "file")
    val name: String,
    @Schema(description = "File", required = true)
    val attachment: MultipartFile
)
