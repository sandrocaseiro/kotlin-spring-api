package dev.sandrocaseiro.template.mappers

import dev.sandrocaseiro.template.models.DPage
import org.springframework.data.domain.Page

fun <S, T> Page<S>.toDto(map: (S) -> T) = DPage<T>(
    lastPage = this.isLast,
    totalPages = this.totalPages,
    totalItems = this.totalElements,
    currentPage = this.pageable.pageNumber,
    data = this.content.map(map).toMutableList()
)
