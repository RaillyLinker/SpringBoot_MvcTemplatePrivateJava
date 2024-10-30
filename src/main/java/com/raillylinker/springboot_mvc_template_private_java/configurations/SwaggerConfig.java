package com.raillylinker.springboot_mvc_template_private_java.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.function.Consumer;

@Configuration
public class SwaggerConfig {
    // <멤버 변수 공간>
    @Value("${custom-config.swagger.document-version}")
    private String documentVersion;

    @Value("${custom-config.swagger.document-title}")
    private String documentTitle;

    @Value("${custom-config.swagger.document-description}")
    private String documentDescription;


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @Bean
    public @Valid @NotNull OpenAPI openAPI() {
        @Valid @NotNull Components components = new Components().addSecuritySchemes(
                "JWT",
                new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name(HttpHeaders.AUTHORIZATION)
        );

        @Valid @NotNull SecurityRequirement securityRequirement = new SecurityRequirement().addList("JWT");

        @Valid @NotNull Info documentInfo = new Info()
                .title(documentTitle)
                .version(documentVersion)
                .description(documentDescription);

        return new OpenAPI()
                .components(components)
                .addSecurityItem(securityRequirement)
                .info(documentInfo);
    }

    @Bean
    public @Valid @NotNull OpenApiCustomizer openApiCustomizer() {
        @Valid @NotNull Consumer<Operation> pathItemConsumer = operation -> operation.getResponses()
                .addApiResponse("400", new ApiResponse()
                        .description("클라이언트에서 전달한 Request 변수의 형식이 잘못되었습니다.\n\n" +
                                "입력 데이터를 다시 한번 확인해주세요"))
                .addApiResponse("500", new ApiResponse()
                        .description("서버에서 런타임 에러가 발생하였습니다.\n\n" +
                                "서버 개발자에게 에러 상황, 에러 로그 등의 정보를 알려주세요."));

        return openApi -> openApi.getPaths()
                .values()
                .forEach(pathItem -> pathItem.readOperations().forEach(pathItemConsumer));
    }
}