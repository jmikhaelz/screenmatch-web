package mx.aluracursos.omdbapi_springboot.client;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

import mx.aluracursos.omdbapi_springboot.config.OmdbApiProperties;

@Service
public class OmdbApiClient {
    private final HttpClient client = HttpClient.newHttpClient();
    
    public String getJSOString(OmdbQueryParams query, OmdbApiProperties omfOmdbApiProperties) throws IOException {
        try {
            URI uri = buildOmdUri(query, omfOmdbApiProperties);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return null;
            }
            return response.body();
        } catch (Exception e) {
            throw new IOException("Error al realizar la consulta a OMDBAPI", e);
        }
    }

    private URI buildOmdUri(OmdbQueryParams params, OmdbApiProperties omdbApiProperties) {
        StringBuilder urlApi = new StringBuilder(omdbApiProperties.getUrl());

        if (params.getTitulo() != null)
            urlApi.append("?t=").append(URLEncoder.encode(params.getTitulo().toLowerCase(), StandardCharsets.UTF_8));
        if (params.getTemporada() != 0)
            urlApi.append("&Season=").append(params.getTemporada());
        if (params.getEpisodio() != 0)
            urlApi.append("&Episode=").append(params.getEpisodio());

        urlApi.append("&type=series");
        urlApi.append("&apikey=").append(omdbApiProperties.getKey());
        return URI.create(urlApi.toString());
    }
}
