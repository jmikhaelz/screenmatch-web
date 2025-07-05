package mx.aluracursos.omdbapi_springboot.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.aluracursos.omdbapi_springboot.dto.EpisodeDTO;
import mx.aluracursos.omdbapi_springboot.dto.FraseDTO;
import mx.aluracursos.omdbapi_springboot.dto.SerieDTO;
import mx.aluracursos.omdbapi_springboot.models.Categoria;
import mx.aluracursos.omdbapi_springboot.models.Frase;
import mx.aluracursos.omdbapi_springboot.models.SerieClass;
import mx.aluracursos.omdbapi_springboot.repository.FraseRepository;
import mx.aluracursos.omdbapi_springboot.repository.SerieRepository;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    @Autowired
    private FraseRepository repositoryF;

    public List<SerieDTO> seriesArchivadas() {
        return convtDatos(repository.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return convtDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> obtenerLanzamientosMasReecientes() {
        return convtDatos(repository.lanzamientosMasRecientes());
    }

    public SerieDTO obtenerPorID(Long id) {
        Optional<SerieClass> serie = repository.findById(id);
        if (serie.isPresent()) {
            SerieClass s = serie.get();
            return new SerieDTO(
                    s.getId(),
                    s.getTitulo(),
                    s.getTotalTemporadas(),
                    s.getEvaluacion(),
                    s.getLanzamiento(),
                    s.getPoster(),
                    s.getGenero(),
                    s.getActores(),
                    s.getSinopsis());
        }
        return null;
    }

    public List<EpisodeDTO> obtenerTodasLasTemps(Long id) {
        Optional<SerieClass> serie = repository.findById(id);
        if (serie.isPresent()) {
            SerieClass s = serie.get();
            return s.getEpisodios().stream().map(e -> new EpisodeDTO(
                    e.getTemporada(),
                    e.getTitulo(),
                    e.getNumero()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodeDTO> obtenerTempsPorNumero(Long id, Long numeroTemporada) {
        return repository.obtenerTempsPorNumero(id, numeroTemporada).stream()
                .map(e -> new EpisodeDTO(
                        e.getTemporada(),
                        e.getTitulo(),
                        e.getNumero()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerSeriesPorGenero(String nombreGenero) {
        Categoria cat = Categoria.fromSpanish(nombreGenero);
        return convtDatos(repository.findByGenero(cat));
    }

    public List<EpisodeDTO> obtenerTopEpisodios(Long id) {
        Optional<SerieClass> serie = repository.findById(id);
        if (serie.isPresent()) {
            SerieClass s = serie.get();
            return repository.top5Episodios(s).stream()
                    .map(e -> new EpisodeDTO(
                            e.getTemporada(),
                            e.getTitulo(),
                            e.getNumero()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public FraseDTO obtenerFraseAleatoria() {
        Frase frase = repositoryF.obtenerFraseAleatoria();
        return new FraseDTO(
                frase.getTitulo(),
                frase.getFrase(),
                frase.getPersonaje(),
                frase.getPoster());
    }

    private List<SerieDTO> convtDatos(List<SerieClass> data) {
        return data.stream()
                .map(s -> new SerieDTO(
                        s.getId(),
                        s.getTitulo(),
                        s.getTotalTemporadas(),
                        s.getEvaluacion(),
                        s.getLanzamiento(),
                        s.getPoster(),
                        s.getGenero(),
                        s.getActores(),
                        s.getSinopsis()))
                .collect(Collectors.toList());
    }

}