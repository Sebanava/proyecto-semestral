package com.arriendo.clientes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI clientesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Clientes")
                        .description("Gestión de clientes del sistema de arriendo. " +
                                "Permite registrar y administrar la información de los clientes. " +
                                "Valida unicidad de RUT, email y número de teléfono.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipo Proyecto Semestral")
                                .email("e.nissispa@gmail.com")
                        )
                );
    }
}
