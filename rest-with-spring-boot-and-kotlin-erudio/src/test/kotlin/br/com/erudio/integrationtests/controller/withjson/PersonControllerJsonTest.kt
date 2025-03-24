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

        val item = objectMapper.readValue(content, PersonVO::class.java)
        person = item

        assertNotNull(item.id)
        assertTrue(item.id > 0)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.genero)

        assertEquals("Richard",item.firstName)
        assertEquals("StallmanT",item.lastName)
        assertEquals("New York City, New York - USA",item.address)
        assertEquals("Macho",item.genero)
        assertEquals(true,item.enabled)
    }

    @Test
    @Order(2)
    fun testUpdate(){
        person.lastName = "Matthew StallmanT"

        val content = given()
            .spec(specification)
            .contentType(ConfigTest.CONTENT_TYPE_JSON)
            .body(person)
            .`when`()
            .put()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val item = objectMapper.readValue(content, PersonVO::class.java)
        person = item

        assertNotNull(item.id)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.genero)

        assertEquals(person.id, item.id)
        assertEquals("Richard",item.firstName)
        assertEquals("Matthew StallmanT",item.lastName)
        assertEquals("New York City, New York - USA",item.address)
        assertEquals("Macho",item.genero)
        assertEquals(true,item.enabled)
    }

    @Test
    @Order(3)
    fun testDisablePersonById(){

        val content = given()
            .spec(specification)
            .contentType(ConfigTest.CONTENT_TYPE_JSON)
            .pathParams("id", person.id)
            .`when`()
            .patch("{id}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val item = objectMapper.readValue(content, PersonVO::class.java)
        person = item

        assertNotNull(item.id)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.genero)

        assertEquals(person.id, item.id)
        assertEquals("Richard",item.firstName)
        assertEquals("Matthew StallmanT",item.lastName)
        assertEquals("New York City, New York - USA",item.address)
        assertEquals("Macho",item.genero)
        assertEquals(false,item.enabled)
    }

    @Test
    @Order(4)
    fun testFindById(){

        val content = given()
            .spec(specification)
            .contentType(ConfigTest.CONTENT_TYPE_JSON)
            .pathParams("id", person.id)
            .`when`()
            .get("{id}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val item = objectMapper.readValue(content, PersonVO::class.java)
        person = item

        assertNotNull(item.id)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.genero)

        assertEquals(person.id, item.id)
        assertEquals("Richard",item.firstName)
        assertEquals("Matthew StallmanT",item.lastName)
        assertEquals("New York City, New York - USA",item.address)
        assertEquals("Macho",item.genero)
        assertEquals(false,item.enabled)
    }

    @Test
    @Order(5)
    fun testDelete(){
        given()
            .spec(specification)
            .pathParams("id", person.id)
            .`when`()
            .delete("{id}")
            .then()
            .statusCode(204)
    }

    @Test
    @Order(6)
    fun testFindAll(){

        val content = given()
            .spec(specification)
            .contentType(ConfigTest.CONTENT_TYPE_JSON)
            .`when`()
            .get()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val people = objectMapper.readValue(content, Array<PersonVO>::class.java)

        val item1 = people[0]
        assertNotNull(item1.id)
        assertNotNull(item1.firstName)
        assertNotNull(item1.lastName)
        assertNotNull(item1.address)
        assertNotNull(item1.genero)

        assertEquals("Ariane",item1.firstName)
        assertEquals("Gachett",item1.lastName)
        assertEquals("Campinas 22",item1.address)
        assertEquals("Female",item1.genero)
        assertEquals(true,item1.enabled)

        val item2 = people[5]
        assertNotNull(item2.id)
        assertNotNull(item2.firstName)
        assertNotNull(item2.lastName)
        assertNotNull(item2.address)
        assertNotNull(item2.genero)

        assertEquals("Osmair Ré",item2.firstName)
        assertEquals("Junior",item2.lastName)
        assertEquals("Brasil - Campinas",item2.address)
        assertEquals("Macho",item2.genero)
        assertEquals(true,item2.enabled)
    }

    @Test
    @Order(7)
    fun testFindAllWithoutToken(){

        val specificationWithoutToken: RequestSpecification = RequestSpecBuilder()
            .setBasePath("/api/person/v1")
            .setPort(ConfigTest.SERVER_PORT)
                .addFilter(RequestLoggingFilter(LogDetail.ALL))
                .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        given()
            .spec(specificationWithoutToken)
            .contentType(ConfigTest.CONTENT_TYPE_JSON)
            .`when`()
            .get()
            .then()
            .statusCode(403)
            .extract()
            .body()
            .asString()

    }

    private fun mockPerson() {
        person.firstName = "Richard"
        person.lastName = "StallmanT"
        person.address = "New York City, New York - USA"
        person.genero = "Macho"
        person.enabled = true
    }


}