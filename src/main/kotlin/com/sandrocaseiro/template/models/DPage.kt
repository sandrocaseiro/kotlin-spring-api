package com.sandrocaseiro.template.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.util.Streamable

data class DPage<T>(
    val data: MutableList<T> = mutableListOf(),
    val lastPage: Boolean = false,
    val totalPages: Int = 0,
    val totalItems: Long = 0,
    val currentPage: Int = 0
): Streamable<T> {
    override fun iterator(): MutableIterator<T> = data.listIterator()

    fun add(item: T) {
        data.add(item)
    }

    @JsonIgnore
    override fun isEmpty(): Boolean = data.isEmpty()
}
