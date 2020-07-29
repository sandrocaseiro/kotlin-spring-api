package dev.sandrocaseiro.template.models.domain

import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "User")
@Table(name = "\"USER\"")
class EUser: ETrace() {
    @Id
    @GeneratedValue(generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    var id: Int = 0

    @Column(name = "name", nullable = false, length = 50)
    var name: String = ""

    @Column(name = "cpf", nullable = false, length = 11)
    var cpf: String = ""

    @Column(name = "email", nullable = false, length = 100)
    var email: String = ""

    @Column(name = "password", nullable = false, length = 150)
    var password: String = ""

    @Column(name = "balance")
    var balance: BigDecimal? = null

    @Column(name = "group_id", nullable = false)
    var groupId: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false, insertable = false, updatable = false)
    var group: EGroup? = null

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
    var roles: List<ERole> = mutableListOf()
}