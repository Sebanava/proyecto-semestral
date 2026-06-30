package com.arriendos.resenas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para crear o actualizar una reseña de película")
public class ResenasModel {

    @Schema(description = "Título exacto de la película reseñada (debe existir en el catálogo)", example = "Inception")
    @NotBlank(message = "No puede estar vacio")
    private String titulo;

    @Schema(description = "Comentario o crítica de la película (máximo 200 caracteres)", example = "Película excelente con una trama muy elaborada")
    @NotBlank(message = "No puede estar vacio")
    private String comentario;

    @Schema(description = "Puntuación de 0 a 10", example = "9")
    @Min(value = 0, message = "La calificación mínima es 0")
    @Max(value = 10, message = "La calificación máxima es 10")
    private int calificaciones;
}
