package mx.aluracursos.omdbapi_springboot.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Season(
        @JsonAlias("Title") String titulo,
        @JsonAlias("Season") int numero,
        @JsonAlias("Episodes") List<Episode> episodios) {
}
