package dev.sandrocaseiro.template.steps

import com.fasterxml.jackson.databind.JsonNode
import dev.sandrocaseiro.template.models.domain.EUser
import dev.sandrocaseiro.template.repositories.UserRepository
import io.cucumber.java8.En
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Then
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal
import java.util.*

class UserSteps: BaseSteps(), En {
    @Autowired
    lateinit var userRepository: UserRepository

    init {
        Given("I inactivate the user {int}") { userId: Int ->
            val user: EUser = userRepository.findById(userId).get()
            user.active = false
            userRepository.save(user)
        }

        Then("The response should have the created user info") {
            val reqUser = state.requestPayload!!
            val roles: MutableList<Int> = ArrayList()
            reqUser.withArray<JsonNode>("roles").elements().forEachRemaining { i: JsonNode -> roles.add(i.asInt()) }

            state.response!!.Then {
                body("data.name", equalTo(reqUser["name"].asText()))
                body("data.email", equalTo(reqUser["email"].asText()))
                body("data.groupId", equalTo(reqUser["groupId"].asInt()))
                body("data.roles.size()", `is`(2))
                body("data.roles", equalTo(roles))
            }
        }

        Then("The created user should exist in the database as active") {
            val id = state.response!!.Extract {
                path<Int>("data.id")
            }

            val user: EUser? = userRepository.findById(id).orElse(null)
            val payload = state.requestPayload!!
            val roles: MutableList<Int> = ArrayList()
            payload.withArray<JsonNode>("roles").elements().forEachRemaining { i: JsonNode -> roles.add(i.asInt()) }

            assertThat(user).isNotNull
            assertThat(user!!.active).isTrue()
            assertThat(user.roles).hasSize(2)
            assertThat(payload["name"].asText()).isEqualTo(user.name)
            assertThat(payload["email"].asText()).isEqualTo(user.email)
            assertThat(payload["cpf"].asText()).isEqualTo(user.cpf)
            assertThat(payload["password"].asText()).isEqualTo(user.password)
            assertThat(payload["groupId"].asInt()).isEqualTo(user.groupId)
            assertThat(roles).containsExactlyInAnyOrderElementsOf(user.roles.map { it.id })
        }

        Then("The user {int} should have his data updated in the database") { userId: Int ->
            val user: EUser? = userRepository.findById(userId).orElse(null)
            val payload = state.requestPayload!!
            val roles: MutableList<Int> = ArrayList()
            payload.withArray<JsonNode>("roles").elements().forEachRemaining { i: JsonNode -> roles.add(i.asInt()) }

            assertThat(user).isNotNull
            assertThat(user!!.roles).hasSize(2)
            assertThat(payload["name"].asText()).isEqualTo(user.name)
            assertThat(payload["cpf"].asText()).isEqualTo(user.cpf)
            assertThat(payload["password"].asText()).isEqualTo(user.password)
            assertThat(payload["groupId"].asInt()).isEqualTo(user.groupId)
            assertThat(roles).containsExactlyInAnyOrderElementsOf(user.roles.map { it.id })
        }

        Then("The user {int} should have his balance updated in the database") { userId: Int ->
            val user: EUser? = userRepository.findById(userId).orElse(null)
            val payload = state.requestPayload!!
            assertThat(user).isNotNull
            assertThat(BigDecimal(payload["balance"].asText())).isEqualTo(user!!.balance)
        }

        Then("The user {int} should be inactive") { userId: Int ->
            val user: EUser? = userRepository.findById(userId).orElse(null)
            assertThat(user).isNotNull
            assertThat(user!!.active).isFalse()
        }
    }
}
