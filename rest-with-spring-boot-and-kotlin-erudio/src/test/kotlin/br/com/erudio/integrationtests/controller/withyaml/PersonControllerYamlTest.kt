package br.com.erudio.integrationtests.controller.withyaml

import br.com.erudio.integrationtests.ConfigTest
import br.com.erudio.integrationtests.controller.withyaml.mapper.YAMLMapper
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest
import br.com.erudio.integrationtests.vo.AccountCredentialsVO
import br.com.erudio.integrationtests.vo.PersonVO
import br.com.erudio.integrationtests.vo.TokenVO
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.config.EncoderConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerYamlTest: AbstractIntegrationTest() {

    private lateinit var specification: RequestSpecification
    private lateinit var objectMapper: YAMLMapper
    private lateinit var person: PersonVO

    @BeforeAll
    fun setupTest(){
        objectMapper = YAMLMapper()
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
            .config(
                RestAssuredConfig
                    .config()
                    .encoderConfig(
                        EncoderConfig.encoderConfig()
                            .encodeContentTypeAs(ConfigTest.CONTENT_TYPE_YML, ContentType.TEXT)
                    )
            )
            .basePath("/auth/signin")
                .port(ConfigTest.SERVER_PORT)
                .contentType(ConfigTest.CONTENT_TYPE_YML)
                .body(user, objectMapper)
            .`when`()
                .post()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                    .`as`(TokenVO::class.java, objectMapper)
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

        val item = given()
            .config(
                RestAssuredConfig
                    .config()
                    .encoderConfig(
                        EncoderConfig.encoderConfig()
                            .encodeContentTypeAs(ConfigTest.CONTENT_TYPE_YML, ContentType.TEXT)
                    )
            )
            .spec(specification)
            .contentType(ConfigTest.CONTENT_TYPE_YML)
            .body(person, objectMapper)
            .`when`()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(PersonVO::class.java, objectMapper)

        person = item

        assertNotNull(item.id)
        assertTrue(item.id > 0)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.genero)

        assertEquals("Richard",item.firstName)
        assertEquals("Stallman",item.lastName)
        assertEquals("New York City, New York - USA",item.address)
        assertEquals("Macho",item.genero)
    }

    @Test
    @Order(2)
    fun testUpdate(){
        person.lastName = "Matthew Stallman"

        val item = given()
            .config(
                RestAssuredConfig
                    .config()
                    .encoderConfig(
                        EncoderConfig.encoderConfig()
                            .encodeContentTypeAs(ConfigTest.CONTENT_TYPE_YML, ContentType.TEXT)
                    )
            )
            .spec(specification)
            .contentType(ConfigTest.CONTENT_TYPE_YML)
            .body(person, objectMapper)
            .`when`()
            .put()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(PersonVO::class.java, objectMapper)

        person = item

        assertNotNull(item.id)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.genero)

        assertEquals(person.id, item.id)
        assertEquals("Richard",item.firstName)
        assertEquals("Matthew Stallman",item.lastName)
        assertEquals("New York City, New York - USA",item.address)
        assertEquals("Macho",item.genero)
    }

    @Test
    @Order(3)
    fun testFindById(){

        val item = given()
            .config(
                RestAssuredConfig
                    .config()
                    .encoderConfig(
                        EncoderConfig.encoderConfig()
                            .encodeContentTypeAs(ConfigTest.CONTENT_TYPE_YML, ContentType.TEXT)
                    )
            )
            .spec(specification)
            .contentType(ConfigTest.CONTENT_TYPE_YML)
            .pathParams("id", person.id)
            .`when`()
            .get("{id}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(PersonVO::class.java, objectMapper)

        person = item

        assertNotNull(item.id)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.genero)

        assertEquals(person.id, item.id)
        assertEquals("Richard",item.firstName)
        assertEquals("Matthew Stallman",item.lastName)
        assertEquals("New York City, New York - USA",item.address)
        assertEquals("Macho",item.genero)
    }

    @Test
    @Order(4)
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
    @Order(5)
    fun testFindAll(){

        val people = given()
            .config(
                RestAssuredConfig
                    .config()
                    .encoderConfig(
                        EncoderConfig.encoderConfig()
                            .encodeContentTypeAs(ConfigTest.CONTENT_TYPE_YML, ContentType.TEXT)
                    )
            )
            .spec(specification)
            .contentType(ConfigTest.CONTENT_TYPE_YML)
            .`when`()
            .get()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(Array<PersonVO>::class.java, objectMapper)


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
    }

    @Test
    @Order(6)
    fun testFindAllWithoutToken(){

        val specificationWithoutToken: RequestSpecification = RequestSpecBuilder()
            .setBasePath("/api/person/v1")
            .setPort(ConfigTest.SERVER_PORT)
                .addFilter(RequestLoggingFilter(LogDetail.ALL))
                .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        given()
            .config(
                RestAssuredConfig
                    .config()
                    .encoderConfig(
                        EncoderConfig.encoderConfig()
                            .encodeContentTypeAs(ConfigTest.CONTENT_TYPE_YML, ContentType.TEXT)
                    )
            )
            .spec(specificationWithoutToken)
            .contentType(ConfigTest.CONTENT_TYPE_YML)
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
        person.lastName = "Stallman"
        person.address = "New York City, New York - USA"
        person.genero = "Macho"
    }


}