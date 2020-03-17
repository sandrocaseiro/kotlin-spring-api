package com.sandrocaseiro.template.models.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "Role")
@Table(name = "role")
class ERole : ETrace() {
    @Id
    @Column(name = "id", nullable = false)
    var id: Int = 0

    @Column(name = "name", nullable = false, length = 50)
    var name: String = ""
}
