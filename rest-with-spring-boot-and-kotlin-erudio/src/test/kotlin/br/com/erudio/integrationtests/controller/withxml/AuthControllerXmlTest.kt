package br.com.erudio.integrationtests.controller.withxml

import br.com.erudio.integrationtests.ConfigTest
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest
import br.com.erudio.integrationtests.vo.AccountCredentialsVO
import br.com.erudio.integrationtests.vo.TokenVO
import io.restassured.RestAssured.given
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerXmlTest() : AbstractIntegrationTest() {

    private lateinit var tokenVO:TokenVO

    @BeforeAll
    fun setupTest(){
        tokenVO = TokenVO()
    }

    @Test
    @Order(0)
    fun testLogin(){
        val user = AccountCredentialsVO(
            username = "leandro",
            password = "admin123"
        )

        tokenVO = given()
            .basePath("/auth/signin")
                .port(ConfigTest.SERVER_PORT)
                .contentType(ConfigTest.CONTENT_TYPE_XML)
                .body(user)
            .`when`()
                .post()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .`as`(TokenVO::class.java)

        assertNotNull(tokenVO.accessToken)
        assertNotNull(tokenVO.refreshToken)

    }

    @Test
    @Order(1)
    fun testRefresh(){

        tokenVO = given()
            .basePath("/auth/refresh")
                .port(ConfigTest.SERVER_PORT)
                .contentType(ConfigTest.CONTENT_TYPE_XML)
                .pathParams("username", tokenVO.username)
                .header(ConfigTest.HEADER_PARAM_AUTHORIZATION, "Bearer ${tokenVO.refreshToken}")
                .`when`()
                    .put("{username}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .`as`(TokenVO::class.java)

        assertNotNull(tokenVO.accessToken)
        assertNotNull(tokenVO.refreshToken)

    }

}