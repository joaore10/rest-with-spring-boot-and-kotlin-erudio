package br.com.erudio.integrationtests.controller.withyaml

import br.com.erudio.integrationtests.ConfigTest
import br.com.erudio.integrationtests.controller.withyaml.mapper.YAMLMapper
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest
import br.com.erudio.integrationtests.vo.AccountCredentialsVO
import br.com.erudio.integrationtests.vo.TokenVO
import io.restassured.RestAssured.given
import io.restassured.config.EncoderConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.http.ContentType
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerYamlTest() : AbstractIntegrationTest() {

    private lateinit var tokenVO:TokenVO
    private lateinit var objectMapper:YAMLMapper

    @BeforeAll
    fun setupTest(){
        tokenVO = TokenVO()
        objectMapper = YAMLMapper()
    }

    @Test
    @Order(0)
    fun testLogin(){
        val user = AccountCredentialsVO(
            username = "leandro",
            password = "admin123"
        )

        tokenVO = given()
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
                .accept(ConfigTest.CONTENT_TYPE_YML)
                .contentType(ConfigTest.CONTENT_TYPE_YML)
                .body(user, objectMapper)
            .`when`()
                .post()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .`as`(TokenVO::class.java, objectMapper)

        assertNotNull(tokenVO.accessToken)
        assertNotNull(tokenVO.refreshToken)

    }

    @Test
    @Order(1)
    fun testRefresh(){

        tokenVO = given()
            .config(
                RestAssuredConfig
                    .config()
                    .encoderConfig(
                        EncoderConfig.encoderConfig()
                            .encodeContentTypeAs(ConfigTest.CONTENT_TYPE_YML, ContentType.TEXT)
                    )
            )
            .basePath("/auth/refresh")
                .port(ConfigTest.SERVER_PORT)
                .accept(ConfigTest.CONTENT_TYPE_YML)
                .contentType(ConfigTest.CONTENT_TYPE_YML)
                .pathParams("username", tokenVO.username)
                .header(ConfigTest.HEADER_PARAM_AUTHORIZATION, "Bearer ${tokenVO.refreshToken}")
                .`when`()
                    .put("{username}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .`as`(TokenVO::class.java, objectMapper)

        assertNotNull(tokenVO.accessToken)
        assertNotNull(tokenVO.refreshToken)

    }

}