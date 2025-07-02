package mx.aluracursos.omdbapi_springboot.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "episodios")
public class EpisodeClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Integer temporada;
    private String titulo;
    private Integer numero;
    private Double evaluacion;
    private LocalDate lanzamiento;
    @ManyToOne
    private SerieClass serie;

    public EpisodeClass() {
    }

    public EpisodeClass(Integer temporada, Episode episodio) {
        this.temporada = temporada;
        this.titulo = episodio.titulo();
        this.numero = Integer.valueOf(episodio.numero());
        try {
            this.evaluacion = Double.valueOf(episodio.evaluacion());
        } catch (NumberFormatException e) {
            this.evaluacion = 0.0;
        }
        try {
            this.lanzamiento = LocalDate.parse(episodio.lanzamiento());
        } catch (DateTimeParseException e) {
            this.lanzamiento = null;
        }
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public void setLanzamiento(LocalDate lanzamiento) {
        this.lanzamiento = lanzamiento;
    }

    public SerieClass getSerie() {
        return serie;
    }

    public void setSerie(SerieClass serie) {
        this.serie = serie;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getNumero() {
        return numero;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public LocalDate getLanzamiento() {
        return lanzamiento;
    }

}