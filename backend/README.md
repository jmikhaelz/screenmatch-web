# OMDB API Spring Boot

Este servicio es una aplicación en Java usando Spring Boot que te permite buscar información sobre series y películas utilizando la [API de OMDB](https://www.omdbapi.com/). Además, almacena los resultados en una base de datos PostgreSQL y te permite consultarlos desde una interfaz interactiva en la línea de comandos.

## 📜 Características

- Interfaz interactiva en consola ([`MenuInteractivo.java`](src/main/java/mx/aluracursos/omdbapi_springboot/cli/MenuInteractivo.java))
- Cliente para consumir la API de OMDB ([`OmdbApiClient.java`](src/main/java/mx/aluracursos/omdbapi_springboot/client/OmdbApiClient.java))
- Modelos de datos para series, temporadas y episodios
- Traducción automática de sinopsis usando Gemini API
- Persistencia de datos con PostgreSQL y Spring Data JPA
- Configuración sencilla mediante archivos de propiedades
- Uso de Docker para facilitar la configuración de la base de datos y la herramienta de administración pgAdmin

## 🚀 Requisitos

- **Java 17 o superior** (por ejemplo, `openjdk 17.0.15`)
- **Maven** (gestor de dependencias y construcción, ya incluido el wrapper en el proyecto)
- **Docker** (opcional, pero recomendado para la base de datos)
- **Spring Boot** (ya incluido en las dependencias)

## ⚙️ Instalación y Ejecución

### 1. Clona el repositorio

```bash
git clone https://github.com/TU-USUARIO/omdbapi-springboot.git
cd omdbapi-springboot
```

### 2. Configura las variables de entorno y propiedades

- Copia los archivos de ejemplo y edítalos con tus datos:
  ```bash
  cp src/main/resources/application.properties.example src/main/resources/application.properties
  cp docker/.env.example docker/.env
  ```
- En `src/main/resources/application.properties`, coloca tus claves de OMDB y Gemini.
- En `docker/.env`, pon los datos de usuario, contraseña y base de datos para PostgreSQL y pgAdmin.

### 3. Levanta la base de datos y pgAdmin con Docker

Si nunca has usado Docker, solo necesitas instalarlo y luego ejecutar:

```bash
cd docker
docker-compose up -d
cd ..
```

Esto creará dos servicios:
- **db**: Un contenedor de PostgreSQL donde se guardarán tus series y episodios.
- **pgadmin**: Una herramienta web para administrar la base de datos, accesible en [http://localhost:8080](http://localhost:8080).

Puedes detener los servicios con:
```bash
docker-compose down
```

### 4. Ejecuta del servicio

- Para manejo del servidor/API sobre la informacion de la DB
```bash
./mvnw spring-boot:run
```
- Para manejo de recaudar informacion de la API OMDB/GEMINI
```bash
./mvnw spring-boot:run  -Dspring-boot.run.arguments=--cli
```

Esto compilará y ejecutará la aplicación. Verás un menú interactivo en la consola para buscar series, consultar temporadas, ver rankings y más.

## 🐳 ¿Qué hace el archivo `docker-compose.yml`?

El archivo [`docker/docker-compose.yml`](docker/docker-compose.yml) automatiza la creación de dos servicios:

- **db**:  
  - Usa la imagen oficial de PostgreSQL.
  - Crea una base de datos con el usuario, contraseña y nombre que definas en el archivo `.env`.
  - Expone el puerto 5432 para que la aplicación pueda conectarse.
  - Guarda los datos en un volumen persistente llamado `postgres_data`.

- **pgadmin**:  
  - Usa la imagen oficial de pgAdmin4.
  - Permite administrar la base de datos desde el navegador.
  - Accesible en [http://localhost:8080](http://localhost:8080) usando el correo y contraseña definidos en `.env`.
  - Sus configuraciones se guardan en el volumen `pgadmin_config`.

Ambos servicios están conectados en una red interna llamada `demo_network` para que puedan comunicarse entre sí.

## 📂 Estructura del Proyecto

```sh
.
├── src
│   ├── main/java/mx/aluracursos/omdbapi_springboot
│   │   ├── cli/           # Interfaz interactiva
│   │   ├── client/        # Cliente OMDB y Gemini
│   │   ├── config/        # Configuración de APIs
│   │   ├── models/        # Modelos de datos
│   │   ├── service/       # Lógica de negocio
│   └── test/java/mx/aluracursos/omdbapi_springboot
│       └── OmdbapiSpringbootApplicationTests.java
├── docker/                # Archivos para Docker y base de datos
├── pom.xml                # Dependencias y configuración de Maven
└── README.md
```

## 🤖 Traducción automática con Gemini API

Además de buscar información en OMDB, este proyecto utiliza la **API de Gemini** (de Google) para traducir automáticamente la sinopsis de las series y películas al español. Cuando realizas una búsqueda, la aplicación consulta Gemini para obtener la traducción y la guarda junto con el resto de la información.

Para que esto funcione, necesitas una clave de Gemini API, que debes colocar en el archivo `src/main/resources/application.properties`.

## 🗂️ ¿Para qué sirve cada archivo principal?

- **src/main/java/mx/aluracursos/omdbapi_springboot/cli/MenuInteractivo.java**  
  Es la interfaz de texto que ves al ejecutar la aplicación. Aquí puedes buscar series, ver temporadas, rankings, etc.

- **src/main/java/mx/aluracursos/omdbapi_springboot/client/OmdbApiClient.java**  
  Se encarga de conectarse a la API de OMDB, buscar series y películas, y traer la información.

- **src/main/java/mx/aluracursos/omdbapi_springboot/client/GeminiApiClient.java**  
  (o similar, según tu estructura) Se conecta a la API de Gemini para traducir la sinopsis automáticamente.

- **src/main/java/mx/aluracursos/omdbapi_springboot/models/**  
  Contiene las clases que representan Series, Temporadas y Episodios, es decir, cómo se guarda la información en la base de datos.

- **src/main/java/mx/aluracursos/omdbapi_springboot/service/**  
  Aquí está la lógica principal de la aplicación: cómo se procesan las búsquedas, traducciones y almacenamiento.

- **src/main/resources/application.properties**  
  Archivo de configuración donde pones tus claves de OMDB y Gemini, y los datos de conexión a la base de datos.

- **docker/docker-compose.yml**  
  Automatiza la creación de la base de datos PostgreSQL y la herramienta pgAdmin para que no tengas que instalarlas manualmente.

- **docker/.env**  
  Aquí defines los usuarios y contraseñas para la base de datos y pgAdmin.


---

## 💡 Créditos

- Proyecto original por [Alura Latam](https://www.aluracursos.com/) y [Monica Hillman](https://www.linkedin.com/in/monicamhillman/)
- Modificaciones y personalización por: *@jmikhaelz* ✨

---