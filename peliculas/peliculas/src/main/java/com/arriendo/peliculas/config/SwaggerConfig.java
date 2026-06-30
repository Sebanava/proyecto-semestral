package com.arriendo.peliculas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI peliculasOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Películas")
                        .description("Gestión del catálogo de películas disponibles para arriendo. " +
                                "Permite crear, actualizar, eliminar y consultar películas. " +
                                "Las operaciones de escritura requieren rol ADMIN.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipo Proyecto Semestral")
                                .email("e.nissispa@gmail.com")
                        )
                );
    }
}
