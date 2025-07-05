package mx.aluracursos.omdbapi_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.aluracursos.omdbapi_springboot.models.Frase;

public interface FraseRepository extends JpaRepository<Frase, Long> {

    @Query(value = "SELECT * FROM frases ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Frase obtenerFraseAleatoria();

}
