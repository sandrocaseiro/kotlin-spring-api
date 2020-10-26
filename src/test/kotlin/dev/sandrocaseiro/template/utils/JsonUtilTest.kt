package dev.sandrocaseiro.template.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class JsonUtilTest {
    @Test
    fun testSerializeNullModelShouldReturnNull() {
        val model: TestModel? = null
        assertThat(model.serialize()).isNull()
    }

    @Test
    fun testPrettySerializeNullModelShouldReturnNull() {
        val model: TestModel? = null
        assertThat(model.serializePrettyPrint()).isNull()
    }

    @Test
    fun testSerializeModelShouldSerialize() {
        val model = TestModel("User", "user@mail.com")

        assertThat(model.serialize()).isEqualTo("{\"name\":\"User\",\"email\":\"user@mail.com\"}")
    }

//    @Test
//    fun testPrettySerializeModelShouldSerialize() {
//        val model = TestModel("User", "user@mail.com")
//
//        assertThat(model.serializePrettyPrint()).isEqualTo("{\r\n  \"name\" : \"User\",\r\n  \"email\" : \"user@mail.com\"\r\n}")
//    }

    private data class TestModel (
        val name: String,
        val email: String
    )
}
