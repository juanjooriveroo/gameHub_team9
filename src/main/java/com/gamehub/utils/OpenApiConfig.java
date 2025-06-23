package com.gamehub.utils;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GameHub")
                        .version("1.0")
                        .description("""
                    Proyecto desarrollado para el evento: Bytes Colaborativos
                    
                    Nombre del proyecto: GameHub

                    Integrantes del proyecto:
                    - Juan Jose Rivero (https://github.com/juanjooriveroo)
                    - Javier Sánchez (https://github.com/jsbrb)
                    - Manuel Rocha (https://github.com/ManuRoch516)
                    - Gabriela (https://github.com/Gabjn798)
                    """)
                        .contact(new Contact()
                                .name("Repositorio del Proyecto")
                                .url("https://github.com/juanjooriveroo/gameHub_team9")
                        )
                ).components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}