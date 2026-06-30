package com.arriendo.alertas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI alertasOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Alertas")
                        .description("Monitoreo de stock de películas. " +
                                "Clasifica el stock en tres niveles: " +
                                "SIN STOCK (0 unidades), STOCK BAJO (1-3 unidades), STOCK SUFICIENTE (más de 3). " +
                                "Consulta en tiempo real los servicios de Películas e Inventario. " +
                                "Solo accesible con rol ADMIN.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipo Proyecto Semestral")
                                .email("e.nissispa@gmail.com")
                        )
                );
    }
}
