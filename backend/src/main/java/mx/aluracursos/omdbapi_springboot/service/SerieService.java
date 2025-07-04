package mx.aluracursos.omdbapi_springboot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.aluracursos.omdbapi_springboot.dto.SerieDTO;
import mx.aluracursos.omdbapi_springboot.models.SerieClass;
import mx.aluracursos.omdbapi_springboot.repository.SerieRepository;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> seriesArchivadas() {
        return convtDatos(repository.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return convtDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> obtenerLanzamientosMasReecientes() {
        return convtDatos(repository.lanzamientosMasRecientes());
    }

    private List<SerieDTO> convtDatos(List<SerieClass> data) {
        return data.stream()
                .map(s -> new SerieDTO(
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