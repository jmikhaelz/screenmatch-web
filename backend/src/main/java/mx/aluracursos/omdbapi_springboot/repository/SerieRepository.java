package mx.aluracursos.omdbapi_springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.aluracursos.omdbapi_springboot.models.Categoria;
import mx.aluracursos.omdbapi_springboot.models.EpisodeClass;
import mx.aluracursos.omdbapi_springboot.models.SerieClass;

public interface SerieRepository extends JpaRepository<SerieClass, Long> {
    /*
     * QUERY POR JPA
     */
    Optional<SerieClass> findByTituloContainsIgnoreCase(String nombreSerie);

    List<SerieClass> findTop5ByOrderByEvaluacionDesc();

    List<SerieClass> findByGenero(Categoria categoria);

    /*
     * List<SerieClass>
     * findByTotalTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(int
     * totalTemporadas,
     * Double evaluacion);
     * QUERY POR JPQL POR PARAMETRO
     */
    @Query("SELECT s FROM SerieClass s WHERE s.totalTemporadas >= :totalTemporadas AND s.evaluacion >= :eval")
    List<SerieClass> seriesPorTempYEvalPromedioUser(@Param("totalTemporadas") int tt,
            @Param("eval") Double no);

    /* NAVITA Y FIJA */
    @Query(value = "SELECT * FROM series WHERE series.total_temporadas>=6 AND series.evaluacion>=7.5", nativeQuery = true)
    List<SerieClass> seriesPorTempYEvalPromedio();

    @Query("SELECT e FROM SerieClass s JOIN s.episodios e WHERE e.titulo ILIKE %:nombre%")
    List<EpisodeClass> episodiosPorNombre(String nombre);

    @Query("SELECT e FROM SerieClass s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<EpisodeClass> top5Episodios(SerieClass serie);

}