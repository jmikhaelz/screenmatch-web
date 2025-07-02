package mx.aluracursos.omdbapi_springboot.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Serie(
                @JsonAlias("Title") String titulo,
                @JsonAlias("totalSeasons") int totalTemporadas,
                @JsonAlias("imdbRating") String evaluacion,
                @JsonAlias("Released") String lanzamiento, @JsonAlias("Poster") String poster,
                @JsonAlias("Genre") String genero,
                @JsonAlias("Actors") String actores,
                @JsonAlias("Plot") String sinopsis) {

        @Override
        public String toString() {
                return "\tSerie : " + titulo + " Genero : " + genero +
                                " Lanzamiento : " + lanzamiento + " Evalucion : " + evaluacion;
        }

}
