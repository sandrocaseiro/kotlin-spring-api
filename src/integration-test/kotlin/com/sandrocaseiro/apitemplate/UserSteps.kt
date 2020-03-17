package com.sandrocaseiro.apitemplate

import com.fasterxml.jackson.databind.JsonNode
import com.sandrocaseiro.template.models.domain.EUser
import com.sandrocaseiro.template.repositories.UserRepository
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.restassured.response.Response
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo

class UserSteps(
    private val state: TestState,
    private val userRepository: UserRepository
): En {
    init {
        After { _ ->
            val user: EUser? = state.user

            if (user != null)
                userRepository.deleteById(user.id)
        }

        When("The client calls get by id endpoint using id: {int}") { id: Int ->
            val response: Response = state.request!!
                .`when`()
                .get("/v1/users/{id}", id)

            state.response = response
        }

        When("The client calls user create endpoint") {
            val response: Response = state.request!!
                .`when`()
                .post("/v1/users")

            state.response = response
        }

        Then("The returned user has the following data:") { table: DataTable ->
            val map: Map<String, String> = table.asMaps()[0]
            state.response!!
                .then()
                .body("data.id", equalTo(map.getOrDefault("id", "0").toInt()))
                .body("data.name", equalTo(map["name"]))
                .body("data.email", equalTo(map["email"]))
        }

        Then("The response has the created user info") {
            val reqUser: JsonNode = state.requestPayload!!

            val roles = mutableListOf<Int>()
            reqUser.withArray<JsonNode>("roles").elements().forEachRemaining { roles.add(it.asInt()) }

            state.response!!
                .then()
                .body("data.name", equalTo(reqUser.get("name").asText()))
                .body("data.email", equalTo(reqUser.get("email").asText()))
                .body("data.groupId", equalTo(reqUser.get("groupId").asInt()))
                .body("data.roles.size()", `is`(2))
                .body("data.roles", equalTo(roles))
        }

        Then("The user is created in the database as active") {
            val id: Int = state.response!!.getBody().jsonPath().getInt("data.id")
            val user: EUser? = userRepository.findById(id).orElse(null)
            val payload: JsonNode = state.requestPayload!!
            val roles = mutableListOf<Int>()
            payload.withArray<JsonNode>("roles").elements().forEachRemaining { roles.add(it.asInt()) }

            state.user = user

            assertThat(user).isNotNull()
            assertThat(user?.active).isTrue()
            assertThat(user?.roles).hasSize(2)

            assertThat(payload.get("name").asText()).isEqualTo(user?.name)
            assertThat(payload.get("email").asText()).isEqualTo(user?.email)
            assertThat(payload.get("groupId").asInt()).isEqualTo(user?.groupId)
            assertThat(roles).containsExactlyInAnyOrderElementsOf(user?.roles?.map { it.id })
        }
    }
}
