package mx.aluracursos.omdbapi_springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.aluracursos.omdbapi_springboot.dto.EpisodeDTO;
import mx.aluracursos.omdbapi_springboot.dto.FraseDTO;
import mx.aluracursos.omdbapi_springboot.dto.SerieDTO;
import mx.aluracursos.omdbapi_springboot.service.SerieService;

@RestController
@RequestMapping("/series")
public class SerieController {
    @Autowired
    private SerieService servicio;

    @GetMapping()
    public List<SerieDTO> seriesArchivadas() {
        return servicio.seriesArchivadas();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obtenerTop5() {
        return servicio.obtenerTop5();
    }

    @GetMapping("/lanzamientos")
    public List<SerieDTO> obtenerLanzamientosMasRecientes() {
        return servicio.obtenerLanzamientosMasReecientes();
    }

    @GetMapping("/{id}")
    public SerieDTO ObtenerPorId(@PathVariable Long id) {
        return servicio.obtenerPorID(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodeDTO> obtenerTodasLasTemps(@PathVariable Long id) {
        return servicio.obtenerTodasLasTemps(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodeDTO> obtenerTempPorNumero(@PathVariable Long id, @PathVariable Long numeroTemporada) {
        return servicio.obtenerTempsPorNumero(id, numeroTemporada);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodeDTO> obterTopEpisodios(@PathVariable Long id) {
        return servicio.obtenerTopEpisodios(id);
    }

    @GetMapping("/categoria/{nombreGenero}")
    public List<SerieDTO> obtenerSeriesPorGenero(@PathVariable String nombreGenero) {
        return servicio.obtenerSeriesPorGenero(nombreGenero);
    }

    @GetMapping("/frases")
    public FraseDTO obtenerFrases() {
        return servicio.obtenerFraseAleatoria();
    }
}