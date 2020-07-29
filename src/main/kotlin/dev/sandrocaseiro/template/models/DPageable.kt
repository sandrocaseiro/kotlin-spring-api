package dev.sandrocaseiro.template.models

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort.Direction

@Schema(hidden = true)
class DPageable (
    private var currentPage: Int = CURRENT_PAGE,
    private var pageSize: Int = PAGE_SIZE,
    private var sort: List<Sort>? = null
) {
    companion object {
        val PAGE_SIZE = 10
        val CURRENT_PAGE = 1
    }

    fun asPageable(): Pageable {
        var pageableSort: org.springframework.data.domain.Sort? = null
        for (fieldSort in sort.orEmpty()) {
            val direction = if (fieldSort.isDescending) Direction.DESC else Direction.ASC

            if (pageableSort == null)
                pageableSort = org.springframework.data.domain.Sort.by(direction, fieldSort.field)
            else
                pageableSort.and(org.springframework.data.domain.Sort.by(direction, fieldSort.field))
        }

        return if (pageableSort == null)
            PageRequest.of(currentPage - 1, pageSize)
        else
            PageRequest.of(currentPage - 1, pageSize, pageableSort)
    }

    data class Sort(
        val field: String,
        val isDescending: Boolean
    )
}
