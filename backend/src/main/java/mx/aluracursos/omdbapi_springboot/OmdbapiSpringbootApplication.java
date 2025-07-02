package mx.aluracursos.omdbapi_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class OmdbapiSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmdbapiSpringbootApplication.class, args);
	}

}