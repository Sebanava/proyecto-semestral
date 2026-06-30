package com.arriendo.reportes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI reportesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Reportes")
                        .description("Generación de reportes y resumen del sistema. " +
                                "El endpoint /resumen consolida datos de Clientes, Arriendos y Pagos " +
                                "en una sola respuesta. Solo accesible con rol ADMIN.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipo Proyecto Semestral")
                                .email("e.nissispa@gmail.com")
                        )
                );
    }
}
