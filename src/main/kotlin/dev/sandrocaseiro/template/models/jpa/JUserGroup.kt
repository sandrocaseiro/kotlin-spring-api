package dev.sandrocaseiro.template.models.jpa

import org.springframework.beans.factory.annotation.Value

interface JUserGroup {
    @Value("#{target.id}")
    fun getId(): Int

    @Value("#{target.name}")
    fun getName(): String

    @Value("#{target.email}")
    fun getEmail(): String

    @Value("#{target.group}")
    fun getGroup(): String
}
