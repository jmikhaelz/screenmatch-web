package mx.aluracursos.omdbapi_springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.aluracursos.omdbapi_springboot.dto.SerieDTO;
import mx.aluracursos.omdbapi_springboot.service.SerieService;

@RestController
public class SerieController {
    @Autowired
    private SerieService servicio;

    @GetMapping("/series")
    public List<SerieDTO> seriesArchivadas() {
        return servicio.seriesArchivadas();
    }

}