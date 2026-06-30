package com.arriendo.pago.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI pagoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Pagos")
                        .description("Procesamiento de pagos de arriendos. " +
                                "Valida número de tarjeta (16 dígitos) y CVV (3 dígitos). " +
                                "Verifica que el arriendo no haya sido pagado anteriormente. " +
                                "Al aprobar el pago, actualiza el estado del arriendo a 'pagado'.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipo Proyecto Semestral")
                                .email("e.nissispa@gmail.com")
                        )
                );
    }
}
