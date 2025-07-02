package mx.aluracursos.omdbapi_springboot.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OmdbApiService {
    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> T getAbout(String json, Class<T> aboutClass) throws Exception {
        try {
            if (json != null)
                return objectMapper.readValue(json, aboutClass);
            else
                throw new Exception("[getAbout] : Consulta erronia!.");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
