package ru.stankin.graduation.config

import com.fasterxml.jackson.databind.MapperFeature
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Pageable
import ru.stankin.graduation.dto.SwaggerPageable
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.configuration.ObjectMapperConfigured
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig : ApplicationListener<ObjectMapperConfigured> {

    companion object {
        private val UNPROTECTED_URLS = listOf("/user/v1/login", "/user/v1/changePassword")
    }

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(getApiInfo())
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))
            .select()
            .apis(RequestHandlerSelectors.basePackage("ru.stankin.graduation.controller"))
            .paths(PathSelectors.any())
            .build()
            .directModelSubstitute(Pageable::class.java, SwaggerPageable::class.java)
    }

    private fun getApiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("Электронный чек-лист")
            .description("Модуль по работе с электронным чек-листом")
            .version("1.0")
            .build()
    }

    private fun securityContext(): SecurityContext? {
        return SecurityContext.builder()
            .operationSelector { operationContext -> !UNPROTECTED_URLS.contains(operationContext.requestMappingPattern())}
            .securityReferences(defaultAuth())
            .build()
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScopes = arrayOf(AuthorizationScope("global", "accessEverything"))
        return listOf(SecurityReference("JWT", authorizationScopes))
    }

    private fun apiKey(): ApiKey {
        return ApiKey("JWT", "Authorization", "header")
    }

    override fun onApplicationEvent(event: ObjectMapperConfigured) {
        event.objectMapper.disable(MapperFeature.AUTO_DETECT_SETTERS)
    }
}