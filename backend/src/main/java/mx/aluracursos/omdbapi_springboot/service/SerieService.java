package mx.aluracursos.omdbapi_springboot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import mx.aluracursos.omdbapi_springboot.dto.SerieDTO;
import mx.aluracursos.omdbapi_springboot.repository.SerieRepository;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    @GetMapping("/series")
    public List<SerieDTO> seriesArchivadas() {
        return repository.findAll().stream()
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
