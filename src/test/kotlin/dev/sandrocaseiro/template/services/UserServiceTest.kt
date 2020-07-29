package dev.sandrocaseiro.template.services

import dev.sandrocaseiro.template.clients.UserClient
import dev.sandrocaseiro.template.exceptions.AppException
import dev.sandrocaseiro.template.models.domain.EGroup
import dev.sandrocaseiro.template.models.domain.ERole
import dev.sandrocaseiro.template.models.domain.EUser
import dev.sandrocaseiro.template.models.jpa.JUserGroup
import dev.sandrocaseiro.template.repositories.UserRepository
import dev.sandrocaseiro.template.security.IAuthenticationInfo
import dev.sandrocaseiro.template.utils.anyNonNull
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.lenient
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.util.*

@RunWith(MockitoJUnitRunner::class)
@ExtendWith(MockitoExtension::class)
class UserServiceTest {
    @Mock private lateinit var userRepository: UserRepository
    @Mock private lateinit var authInfo: IAuthenticationInfo
    @Mock private lateinit var userApiClient: UserClient
    @InjectMocks private lateinit var userService: UserService

    companion object {
        private var groups: List<EGroup> = emptyList()
        private var roles: List<ERole> = emptyList()
        private var users: List<EUser> = emptyList()
    }

    @Before
    fun init() {
        prepareData()
        prepateMock()
    }

    private fun prepareData() {
        val group1 = EGroup().apply {
            id = 1
            name = "Group 1"
        }
        val group2 = EGroup().apply {
            id = 2
            name = "Group 2"
        }

        val role1 = ERole().apply {
            id = 1
            name = "Role 1"
        }
        val role2 = ERole().apply {
            id = 2
            name = "Role 2"
        }

        val user1 = EUser().apply {
            id = 1
            name = "User 1"
            cpf = "66619705022"
            email = "user1@mail.com"
            password = "1234"
            balance = BigDecimal.valueOf(100)
            groupId = group1.id
            group = group1
            roles = listOf(role1)
            active = true
        }
        val user2 = EUser().apply {
            id = 2
            name = "User 2"
            cpf = "19037443001"
            email = "user2@mail.com"
            password = "1234"
            balance = BigDecimal.valueOf(10)
            groupId = group2.id
            group = group2
            roles = listOf(role2)
            active = true
        }
        val user3 = EUser().apply {
            id = 3
            name = "User 3"
            cpf = "87004531023"
            email = "user3@mail.com"
            password = "1234"
            balance = BigDecimal.valueOf(55.5)
            groupId = group1.id
            group = group1
            roles = listOf(role1, role2)
            active = true
        }
        val user4 = EUser().apply {
            id = 4
            name = "User 4"
            cpf = "10260842028"
            email = "user4@mail.com"
            password = "1234"
            balance = BigDecimal.valueOf(15)
            groupId = group2.id
            group = group2
            roles = listOf(role1, role2)
            active = true
        }
        val user5 = EUser().apply {
            id = 5
            name = "User 5"
            cpf = "29464594039"
            email = "user5@mail.com"
            password = "1234"
            balance = BigDecimal.valueOf(10.4)
            groupId = group2.id
            group = group2
            roles = listOf(role1, role2)
            active = true
        }
        val user6 = EUser().apply {
            id = 6
            name = "User 6"
            cpf = "55776072050"
            email = "user6@mail.com"
            password = "1234"
            balance = BigDecimal.valueOf(0)
            groupId = group2.id
            group = group2
            roles = listOf(role1, role2)
            active = true
        }

        groups = listOf(group1, group2)
        roles = listOf(role1, role2)
        users = listOf(user1, user2, user3, user4, user5, user6)
    }

    private fun prepateMock() {
        lenient().`when`(authInfo.getId()).thenReturn(1)

        lenient().`when`(userRepository.getOne(anyNonNull())).thenAnswer {
            val id: Int = it.getArgument(0)

            users.firstOrNull { u -> u.id == id }
        }

        lenient().`when`(userRepository.findByUsername(anyNonNull())).thenAnswer {
            val username: String = it.getArgument(0)

            users.firstOrNull { u -> u.email == username }
        }

        lenient().`when`(userRepository.findById(anyNonNull())).thenAnswer {
            val id: Int = it.getArgument(0)

            Optional.ofNullable(users.firstOrNull { u -> u.id == id })
        }

        lenient().`when`(userRepository.findOneById(anyNonNull())).thenAnswer {
            val id: Int = it.getArgument(0)

            users.firstOrNull { u -> u.id == id }?.let { u ->
                object: JUserGroup {
                    override fun getId(): Int = u.id
                    override fun getName(): String = u.name
                    override fun getEmail(): String = u.email
                    override fun getGroup(): String = u.group?.name ?: ""
                }
            }
        }

        lenient().`when`(userRepository.save(any(EUser::class.java))).then(returnsFirstArg<EUser>())

        lenient().`when`(userRepository.updateBalance(anyNonNull(), anyNonNull())).thenAnswer {
            val id: Int = it.getArgument(0)
            val balance: BigDecimal = it.getArgument(1)

            val user: EUser? = users.firstOrNull { u -> u.id == id }
            if (user == null)
                0
            else {
                user.balance = balance
                1
            }
        }
    }

    @Test
    fun testCannotCreateUserWithSameEmail() {
        val user = EUser().apply {
            id = 4
            name = "User 4"
            cpf = "66619705022"
            email = "user1@mail.com"
            password = "1234"
            balance = BigDecimal.valueOf(100)
            groupId = 1
        }

        assertThatExceptionOfType(AppException::class.java).isThrownBy { userService.create(user) }
    }

    @Test
    fun testCanCreateUser() {
        val user = EUser().apply {
            id = 4
            name = "User 4"
            cpf = "66619705022"
            email = "user999@mail.com"
            password = "1234"
            balance = BigDecimal.valueOf(100)
            groupId = 1
        }
        assertThat(userService.create(user)).isEqualTo(user)
    }

    @Test
    fun testCannotUpdateNotExistingUser() {
        val user = EUser().apply {
            id = 999
            name = "User 1"
            cpf = "66619705022"
            email = "user1@mail.com"
            password = "1234"
            balance = BigDecimal.valueOf(100)
            groupId = 1
        }
        assertThatExceptionOfType(AppException::class.java).isThrownBy { userService.update(user) }
    }

    @Test
    fun testCanUpdateExistingUser() {
        val user = EUser().apply {
            id = 1
            name = "User 2"
            cpf = "432423432"
            email = "user1@mail.com"
            password = "1234"
            balance = BigDecimal.valueOf(100)
            groupId = 2
            roles = emptyList()
        }

        userService.update(user)
        val dbUser: EUser = users[0]
        assertThat(dbUser.name).isEqualTo(user.name)
        assertThat(dbUser.cpf).isEqualTo(user.cpf)
        assertThat(dbUser.password).isEqualTo(user.password)
        assertThat(dbUser.groupId).isEqualTo(user.groupId)
        assertThat(dbUser.roles).isEmpty()
    }

    @Test
    fun testExistingCanUpdateUserBalance() {
        val balance = BigDecimal.valueOf(50)
        userService.updateBalance(1, balance)

        val user: EUser = userRepository.getOne(1)
        assertThat(user.balance).isEqualByComparingTo(balance)
    }

    @Test
    fun testUpdateBalanceWithNotExistingUserShouldThrowException() {
        val balance = BigDecimal.valueOf(50)

        assertThatExceptionOfType(AppException::class.java).isThrownBy { userService.updateBalance(999, balance) }
    }

    @Test
    fun testUserCannotDeleteHimself() {
        assertThatExceptionOfType(AppException::class.java).isThrownBy { userService.delete(1) }
    }

    @Test
    fun testUserCannotDeleteNotExistingUser() {
        assertThatExceptionOfType(AppException::class.java).isThrownBy { userService.delete(999) }
    }

    @Test
    fun testUserCanDeleteAnotherUser() {
        userService.delete(2)

        assertThat(users[1].active).isFalse()
    }

    @Test
    fun testFindNotExistingUserShouldThrowException() {
        assertThatExceptionOfType(AppException::class.java).isThrownBy { userService.findById(999) }
    }

    @Test
    fun testCanFindUserById() {
        val user: JUserGroup = userService.findById(3)
        val dbUser: EUser = users[2]

        assertThat(user.getId()).isEqualTo(dbUser.id)
        assertThat(user.getName()).isEqualTo(dbUser.name)
        assertThat(user.getGroup()).isEqualTo(dbUser.group?.name)
    }
}
