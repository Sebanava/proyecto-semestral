package com.arriendo.rental.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI rentalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Arriendos")
                        .description("Gestión del ciclo completo de arriendos de películas. " +
                                "Al crear un arriendo se obtiene el precio desde el servicio de Películas " +
                                "y se reduce el stock en Inventario automáticamente. " +
                                "Incluye endpoints para marcar como pagado y devolver.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipo Proyecto Semestral")
                                .email("e.nissispa@gmail.com")
                        )
                );
    }
}
