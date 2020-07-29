package dev.sandrocaseiro.template.utils

fun String?.toInt(defaultValue: Int): Int = if (this.isNullOrBlank()) defaultValue else this.toInt()
