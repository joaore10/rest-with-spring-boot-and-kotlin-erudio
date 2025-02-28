package br.com.erudio.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenApi(): OpenAPI{
        return OpenAPI()
            .info(
                Info()
                    .title("RESTful API com Kotlin 1.6.10 e SpringBoot 3.0.0")
                    .version("v1")
                    .description("Alguma descrição sobre API")
                    .termsOfService("https://github.com/joaore10/rest-with-spring-boot-and-kotlin-erudio/tree/main/rest-with-spring-boot-and-kotlin-erudio")
                    .license(
                        License().name("Apache 2.0")
                            .url("https://github.com/joaore10/rest-with-spring-boot-and-kotlin-erudio/tree/main/rest-with-spring-boot-and-kotlin-erudio")
                    )
            )
    }
}