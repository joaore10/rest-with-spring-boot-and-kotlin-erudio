package br.com.erudio.integrationtests.controller.withjson

import br.com.erudio.integrationtests.ConfigTest
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest
import br.com.erudio.integrationtests.vo.AccountCredentialsVO
import br.com.erudio.integrationtests.vo.PersonVO
import br.com.erudio.integrationtests.vo.TokenVO
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerJsonTest: AbstractIntegrationTest() {

    private lateinit var specification: RequestSpecification
    private lateinit var objectMapper: ObjectMapper
    private lateinit var person: PersonVO

    @BeforeAll
    fun setupTest(){
        objectMapper = ObjectMapper()
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        person = PersonVO()
    }

    @Test
    @Order(0)
    fun testLogin(){
        val user = AccountCredentialsVO(
            username = "leandro",
            password = "admin123"
        )

        val token = given()
            .basePath("/auth/signin")
                .port(ConfigTest.SERVER_PORT)
                .contentType(ConfigTest.CONTENT_TYPE_JSON)
                .body(user)
            .`when`()
                .post()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                    .`as`(TokenVO::class.java)
                    .accessToken

        specification = RequestSpecBuilder()
            .addHeader(ConfigTest.HEADER_PARAM_AUTHORIZATION, "Bearer $token")
                .setBasePath("/api/person/v1")
            .setPort(ConfigTest.SERVER_PORT)
                .addFilter(RequestLoggingFilter(LogDetail.ALL))
                .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()
    }

    @Test
    @Order(1)
    fun testCreate(){
        mockPerson()

        val content = given()
            .spec(specification)
            .contentType(ConfigTest.CONTENT_TYPE_JSON)
            .body(person)
            .`when`()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val createdPerson = objectMapper.readValue(content, PersonVO::class.java)

        assertNotNull(createdPerson.id)
        assertTrue(createdPerson.id > 0)
        assertNotNull(createdPerson.firstName)
        assertNotNull(createdPerson.lastName)
        assertNotNull(createdPerson.address)
        assertNotNull(createdPerson.genero)

        assertEquals("Richard",createdPerson.firstName)
        assertEquals("Stallman",createdPerson.lastName)
        assertEquals("New York City, New York - USA",createdPerson.address)
        assertEquals("Macho",createdPerson.genero)
    }

    private fun mockPerson() {
        person.firstName = "Richard"
        person.lastName = "Stallman"
        person.address = "New York City, New York - USA"
        person.genero = "Macho"
    }


}