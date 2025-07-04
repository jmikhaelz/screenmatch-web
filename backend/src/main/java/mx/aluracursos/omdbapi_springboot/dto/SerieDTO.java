package mx.aluracursos.omdbapi_springboot.dto;

import mx.aluracursos.omdbapi_springboot.models.Categoria;

public record SerieDTO(
        Long id,
        String titulo,
        Integer totalTemporadas,
        Double evaluacion,
        String lanzamiento,
        String poster,
        Categoria genero,
        String actores,
        String sinopsis) {
}