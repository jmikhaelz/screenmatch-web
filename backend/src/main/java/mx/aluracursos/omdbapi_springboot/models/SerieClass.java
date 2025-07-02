package mx.aluracursos.omdbapi_springboot.models;

import java.util.List;
import java.util.OptionalDouble;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import mx.aluracursos.omdbapi_springboot.client.GeminiApiClient;
import mx.aluracursos.omdbapi_springboot.config.GeminiApiProperties;

@Entity
@Table(name = "series")
public class SerieClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private Integer totalTemporadas;
    private Double evaluacion;
    private String lanzamiento;
    private String poster;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String actores;
    private String sinopsis;
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<EpisodeClass> episodios;

    public SerieClass() {
    }

    public SerieClass(Serie serieCache, GeminiApiProperties geminiApiProperties) {
        this.titulo = serieCache.titulo();
        this.totalTemporadas = serieCache.totalTemporadas();
        this.evaluacion = OptionalDouble.of(Double.valueOf(serieCache.evaluacion())).orElse(0);
        this.lanzamiento = serieCache.lanzamiento();
        this.poster = serieCache.poster();
        this.genero = Categoria.fromString(serieCache.genero().split(",")[0].trim());
        this.actores = serieCache.actores();
        this.sinopsis = GeminiApiClient.getTranslate(serieCache.sinopsis(), "spanish",
                geminiApiProperties.getKey());
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getLanzamiento() {
        return lanzamiento;
    }

    public void setLanzamiento(String lanzamiento) {
        this.lanzamiento = lanzamiento;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    @Override
    public String toString() {
        return "\n >" + titulo
                + ", Temporadas :" + totalTemporadas
                + " Evaluacion : " + evaluacion
                + "\n Lanzamiento : " + lanzamiento
                + ", Poster/URI : " + poster
                + "\n Genero :" + genero
                + ", Reparto : " + actores
                + "\n [<>] Sinopsis: \n\t" + sinopsis;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public List<EpisodeClass> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<EpisodeClass> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

}
