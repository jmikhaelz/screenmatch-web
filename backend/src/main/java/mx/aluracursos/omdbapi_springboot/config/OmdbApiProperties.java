package mx.aluracursos.omdbapi_springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "omdbapi")
public class OmdbApiProperties {
    private String url;
    private String key;

    public String getUrl() {
        return url;
    }

    public String getKey() {
        return key;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setKey(String key) {
        this.key = key;
    }
}