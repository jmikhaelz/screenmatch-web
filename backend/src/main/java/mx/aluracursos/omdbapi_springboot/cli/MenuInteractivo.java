package mx.aluracursos.omdbapi_springboot.cli;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import mx.aluracursos.omdbapi_springboot.client.OmdbApiClient;
import mx.aluracursos.omdbapi_springboot.client.OmdbQueryParams;
import mx.aluracursos.omdbapi_springboot.config.GeminiApiProperties;
import mx.aluracursos.omdbapi_springboot.config.OmdbApiProperties;
import mx.aluracursos.omdbapi_springboot.models.Categoria;
import mx.aluracursos.omdbapi_springboot.models.EpisodeClass;
import mx.aluracursos.omdbapi_springboot.models.Season;
import mx.aluracursos.omdbapi_springboot.models.Serie;
import mx.aluracursos.omdbapi_springboot.models.SerieClass;
import mx.aluracursos.omdbapi_springboot.repository.SerieRepository;
import mx.aluracursos.omdbapi_springboot.service.OmdbApiService;

@Component
public class MenuInteractivo implements CommandLineRunner {
    // VARIABLE LOCALES
    private final static Scanner scanner = new Scanner(System.in);
    private List<SerieClass> listHistorial;
    private Optional<SerieClass> serieBuscada;
    // VARIABLES DE PROPIEDADES
    @Autowired
    private OmdbApiProperties omdbApiProperties = new OmdbApiProperties();
    @Autowired
    private GeminiApiProperties geminiApiProperties = new GeminiApiProperties();
    @Autowired
    private SerieRepository repository;

    @Override
    public void run(String... args) {
        if (Arrays.asList(args).contains("--cli")) {
            mostrarMenu();
            System.out.println("¡Finalizado exitosamente el programa!");
        }
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n|\t\t SERIE PLANET \t\t|");
            System.out.println("0. Salir");
            System.out.println("1. Buscar serie por OmdbApi");
            System.out.println("2. Consultar temporadas y episodios");
            System.out.println("3. Consultar serie de DB");
            System.out.println("4. Ranking de series");
            System.out.println("5. Buscar series por categoria");
            System.out.println("6. Historial de Busquedas");
            System.out.println("7. Filtrar por temporada y evaluacion");
            System.out.println("8. Ver series recomendadas");
            System.out.println("9. Buscar episodio por nombre");
            System.out.println("10. Top Episodios por Serie");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    solicitarNombreSerie();
                    break;
                case 2:
                    mostrarTemporadasYEpisodios();
                    break;
                case 3:
                    consultarSerie();
                    break;
                case 4:
                    topSeries();
                    break;
                case 5:
                    buscarPorCategoria();
                    break;
                case 6:
                    historialBusqueda();
                    break;
                case 7:
                    filtrarSeriesPorTemporadaYEvaluacion();
                    break;
                case 8:
                    verSeriesPromedios();
                    break;
                case 9:
                    buscarEpisodioPorTitulo();
                    break;
                case 10:
                    topEpisodios();
                    break;
                case 0:
                    System.out.print("¿Seguro que quieres salir? [Ingrese Y/ Si no de ENTER]: ");
                    String respuesta = scanner.nextLine().trim().toLowerCase();
                    if (!(respuesta.equals("y")))
                        opcion = -1;
                    break;
                default:
            }
        } while (opcion != 0);
    }

    private void topEpisodios() {
        consultarSerie();
        if (serieBuscada.isPresent()) {
            SerieClass resp = serieBuscada.get();
            List<EpisodeClass> tops = repository.top5Episodios(resp);
            System.out.println("\n>>Top 5 Episodios de la Serie " + resp.getTitulo() + "<<");
            tops.forEach(e -> System.out.println("\n\t Temp: " + e.getTemporada() + " - Ep: " + e.getNumero()
                    + " - Titulo: " + e.getTitulo() + " -  Evaluacion: " + e.getEvaluacion()));
        }
    }

    private void buscarEpisodioPorTitulo() {
        System.out.println(" Ingrese el nombre del episodio: ");
        var nombreS = scanner.nextLine();
        List<EpisodeClass> resp = repository.episodiosPorNombre(nombreS);

        resp.forEach(
                e -> System.out.printf("\n\tSerie: %s - Temp: %s - Ep: %s - Evaluacion: %s", e.getSerie().getTitulo(),
                        e.getTemporada(), e.getNumero(), e.getEvaluacion()));
    }

    private void verSeriesPromedios() {
        System.out.println("\n\t>>SERIES RECOMENDADAS<<");
        List<SerieClass> resp = repository.seriesPorTempYEvalPromedio();
        resp.forEach(s -> System.out
                .println(s.getTitulo() + " - Temp: " + s.getTotalTemporadas() + " - evaluacion: " + s.getEvaluacion()));
    }

    public void filtrarSeriesPorTemporadaYEvaluacion() {
        System.out.println("¿Filtrar séries con cuántas temporadas? ");
        var totalTemporadas = scanner.nextInt();
        scanner.nextLine();
        System.out.println("¿Com evaluación apartir de cuál valor? ");
        var evaluacion = scanner.nextDouble();
        scanner.nextLine();
        List<SerieClass> filtroSeries = repository.seriesPorTempYEvalPromedioUser(totalTemporadas, evaluacion);

        System.out.println("*** Series filtradas ***");
        filtroSeries.forEach(s -> System.out.println(s.getTitulo() + "  - evaluacion: " + s.getEvaluacion()));
    }

    private void buscarPorCategoria() {
        System.out.println("Ingrese el nombre de la categoria/genero de la serie que quieres buscar: ");
        var entrada = scanner.nextLine();
        var categoria = Categoria.fromSpanish(entrada);
        List<SerieClass> seriesPorCategoria = repository.findByGenero(categoria);
        System.out.println("Series que son " + entrada);
        seriesPorCategoria.forEach(s -> System.out.println("* " + s.getTitulo() + " - " + s.getLanzamiento()));
    }

    private void topSeries() {
        System.out.println("\n\t|<>| Ranking de Series");
        List<SerieClass> tops = repository.findTop5ByOrderByEvaluacionDesc();
        tops.forEach(s -> System.out.println("* " + s.getTitulo() + " Eval." + s.getEvaluacion()));
    }

    private void consultarSerie() {
        System.out.println("Ingrese el nombre de la serie que quieres buscar: ");
        var nombreSerie = scanner.nextLine();
        serieBuscada = repository.findByTituloContainsIgnoreCase(nombreSerie);
        if (serieBuscada.isPresent())
            System.out.println("Serie Encontrada : \n\t" + serieBuscada.get());
        else
            System.out.println("La serie " + nombreSerie + " no existente en DB");
    }

    private void mostrarTemporadasYEpisodios() {
        System.out.println("\n\t| SERIES DEISPONIBLES \t\t|");
        serieDisponible();
        System.out.println("Ingrese el nombre de la serie que quieres ver sus capitulos: ");
        var nombreSerie = scanner.nextLine();

        Optional<SerieClass> serie = listHistorial.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<Season> temporadas = new ArrayList<>();
            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                OmdbQueryParams params = new OmdbQueryParams.Builder()
                        .titulo(serieEncontrada.getTitulo()).temporada(i).build();
                String resquestEp;
                try {
                    resquestEp = new OmdbApiClient().getJSOString(params, omdbApiProperties);
                    var queryEp = new OmdbApiService().getAbout(resquestEp, Season.class);
                    temporadas.add(queryEp);
                } catch (Exception e) {
                    System.out.println("[Season " + i + "] Error de conseguir temporada : " + e.getMessage());
                }
            }
            System.out.println("\n🎬 Lista de episodios:");
            temporadas.forEach(season -> season.episodios().forEach(ep -> System.out.println(
                    "Temporada " + season.numero() + " - Episodio " + ep.numero() + ": " + ep.titulo())));
            List<EpisodeClass> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(e -> new EpisodeClass(t.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repository.save(serieEncontrada);
        } else

        {
            System.out.println("[X] Error no existe la serie " + nombreSerie);
        }
    }

    private void solicitarNombreSerie() {
        while (true) {
            System.out.print("\n🔎 Ingrese el nombre de la serie: ");
            OmdbQueryParams params = new OmdbQueryParams.Builder()
                    .titulo(scanner.nextLine().toLowerCase()).build();
            System.out.println("\n⏳ Cargando información...\n");
            if (obtenerDatosSerie(params)) {
                System.out.println("✅ Datos Obtenidos de la Serie: " + params.getTitulo().toUpperCase());
                break;
            } else {
                System.out.println("❌ Datos Obtenidos de la Serie: " + params.getTitulo());
                System.out.print("¿Quieres seguir con otra búsqueda? [Ingrese Y/ Si no de ENTER]: ");
                var respuesta = scanner.nextLine().trim().toLowerCase();
                if (!(respuesta.equals("y")))
                    break;

            }
        }

    }

    private boolean obtenerDatosSerie(OmdbQueryParams uri) {
        try {
            var resquest = new OmdbApiClient().getJSOString(uri, omdbApiProperties);
            Serie serieCache = new OmdbApiService().getAbout(resquest, Serie.class);
            if (serieCache.titulo() != null) {
                SerieClass consultaSerie = new SerieClass(serieCache, geminiApiProperties);
                repository.save(consultaSerie);
                BarraCargaUtil.mostrarBarraCarga(30, 100);
                System.out.println(consultaSerie);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void serieDisponible() {
        listHistorial = repository.findAll();

        listHistorial.stream()
                .sorted(Comparator.comparing(SerieClass::getTitulo))
                .forEach(s -> System.out.println("> " + s.getTitulo() + " - " + s.getLanzamiento()));
    }

    private void historialBusqueda() {
        System.out.println("\n\t| HISTORIAL DE BUSQUEDAS \t\t|");
        listHistorial = repository.findAll();

        listHistorial.stream()
                .sorted(Comparator.comparing(SerieClass::getGenero))
                .forEach(System.out::println);
    }
}