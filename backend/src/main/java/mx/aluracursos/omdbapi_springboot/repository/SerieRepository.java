package mx.aluracursos.omdbapi_springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.aluracursos.omdbapi_springboot.models.Categoria;
import mx.aluracursos.omdbapi_springboot.models.EpisodeClass;
import mx.aluracursos.omdbapi_springboot.models.SerieClass;

public interface SerieRepository extends JpaRepository<SerieClass, Long> {

    List<SerieClass> findTop5ByOrderByEvaluacionDesc();

    List<SerieClass> findByGenero(Categoria categoria);

    @Query("SELECT e FROM SerieClass s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<EpisodeClass> top5Episodios(SerieClass serie);

    @Query("SELECT s FROM SerieClass s JOIN s.episodios e GROUP BY s ORDER BY MAX(e.lanzamiento) DESC LIMIT 5")
    List<SerieClass> lanzamientosMasRecientes();

    @Query("SELECT e FROM SerieClass s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numeroTemporada")
    List<EpisodeClass> obtenerTempsPorNumero(Long id, Long numeroTemporada);
}