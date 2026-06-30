package com.arriendo.login.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI loginOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Autenticación")
                        .description("Gestión de usuarios y autenticación del sistema. " +
                                "Maneja roles ADMIN y CLIENTE. " +
                                "El login retorna nombre, email y rol del usuario autenticado.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipo Proyecto Semestral")
                                .email("e.nissispa@gmail.com")
                        )
                );
    }
}
