package mx.aluracursos.omdbapi_springboot.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Episode(
                @JsonAlias("Title") String titulo,
                @JsonAlias("Episode") String numero,
                @JsonAlias("Season") String temporada,
                @JsonAlias("imdbRating") String evaluacion,
                @JsonAlias("Released") String lanzamiento) {

        @Override
        public String toString() {
                return "Episodio #" + numero + " : " + titulo +
                                "\n Lanzamiento : " + lanzamiento + " Evaluacion : "
                                + evaluacion;
        }

}
