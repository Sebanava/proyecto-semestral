package com.arriendos.resenas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI resenasOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Reseñas")
                        .description("Gestión de reseñas y calificaciones de películas. " +
                                "Valida que la película exista antes de guardar la reseña. " +
                                "Calificación entre 0 y 10. Comentario máximo 200 caracteres. " +
                                "Solo CLIENTE puede crear reseñas. Solo ADMIN puede eliminarlas.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipo Proyecto Semestral")
                                .email("e.nissispa@gmail.com")
                        )
                );
    }
}
