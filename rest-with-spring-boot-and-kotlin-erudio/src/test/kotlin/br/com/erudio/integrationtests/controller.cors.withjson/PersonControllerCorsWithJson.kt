package br.com.erudio.integrationtests.controller.cors.withjson

import br.com.erudio.integrationtests.vo.PersonVO
import br.com.erudio.integrationtests.ConfigTest
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter

import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerCorsWithJson() : AbstractIntegrationTest() {

	private lateinit var specification: RequestSpecification
	private lateinit var objectMapper: ObjectMapper
	private lateinit var person: PersonVO

	@BeforeAll
	fun setupTests(){
		objectMapper = ObjectMapper()
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
		person = PersonVO()
	}

    @Test
	@Order(1)
	fun testCreate() {
		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(
				ConfigTest.HEADER_PARAM_ORIGIN,
				ConfigTest.ORIGIN_ERUDIO
			)
				.setBasePath("/api/person/v1")
				.setPort(ConfigTest.SERVER_PORT)
				.addFilter(RequestLoggingFilter(LogDetail.ALL))
				.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()



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

		val createdPerson = objectMapper.readValue(
			content,
			PersonVO::class.java
		)
		person = createdPerson

		assertNotNull(createdPerson.id)
		assertNotNull(createdPerson.firstName)
		assertNotNull(createdPerson.lastName)
		assertNotNull(createdPerson.address)
		assertNotNull(createdPerson.genero)

		assertTrue(createdPerson.id > 0)

		assertEquals("Nelson", createdPerson.firstName)
		assertEquals("Piquet", createdPerson.lastName)
		assertEquals("Brasilia, DF, Brasil", createdPerson.address)
		assertEquals("Macho", createdPerson.genero)
	}

	@Test
	@Order(2)
	fun testCreateWithWrongOrigin() {
		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(
				ConfigTest.HEADER_PARAM_ORIGIN,
				ConfigTest.ORIGIN_SEMERU
			)
			.setBasePath("/api/person/v1")
			.setPort(ConfigTest.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL))
			.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()



		val content = given()
			.spec(specification)
			.contentType(ConfigTest.CONTENT_TYPE_JSON)
			.body(person)
			.`when`()
			.post()
			.then()
			.statusCode(403)
			.extract()
			.body()
			.asString()

		assertEquals("Invalid CORS request", content)
	}

	@Test
	@Order(3)
	fun findById() {
		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(
				ConfigTest.HEADER_PARAM_ORIGIN,
				ConfigTest.ORIGIN_LOCALHOST
			)
			.setBasePath("/api/person/v1")
			.setPort(ConfigTest.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL))
			.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()



		val content = given()
			.spec(specification)
			.contentType(ConfigTest.CONTENT_TYPE_JSON)
			.pathParam("id", person.id)
			.`when`()["{id}"]
			.then()
			.statusCode(200)
			.extract()
			.body()
			.asString()

		val createdPerson = objectMapper.readValue(
			content,
			PersonVO::class.java
		)

		assertNotNull(createdPerson.id)
		assertNotNull(createdPerson.firstName)
		assertNotNull(createdPerson.lastName)
		assertNotNull(createdPerson.address)
		assertNotNull(createdPerson.genero)

		assertTrue(createdPerson.id > 0)

		assertEquals("Nelson", createdPerson.firstName)
		assertEquals("Piquet", createdPerson.lastName)
		assertEquals("Brasilia, DF, Brasil", createdPerson.address)
		assertEquals("Macho", createdPerson.genero)
	}

	@Test
	@Order(4)
	fun findByIdWithWrongOrigin() {
		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(
				ConfigTest.HEADER_PARAM_ORIGIN,
				ConfigTest.ORIGIN_SEMERU
			)
			.setBasePath("/api/person/v1")
			.setPort(ConfigTest.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL))
			.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()



		val content = given()
			.spec(specification)
			.contentType(ConfigTest.CONTENT_TYPE_JSON)
			.pathParam("id", person.id)
			.`when`()["{id}"]
			.then()
			.statusCode(403)
			.extract()
			.body()
			.asString()


		assertEquals("Invalid CORS request", content)
	}

	private fun mockPerson() {
		person.firstName = "Nelson"
		person.lastName = "Piquet"
		person.address = "Brasilia, DF, Brasil"
		person.genero = "Macho"
	}

}