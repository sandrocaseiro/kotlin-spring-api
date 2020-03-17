package com.sandrocaseiro.template.security.permissions

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention
@PreAuthorize("hasRoles()")
annotation class HasRoles
