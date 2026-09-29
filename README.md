# Vitrina de Productos - Desafío Full Stack

Aplicación web desarrollada para explorar de forma fluida un catálogo masivo de productos de supermercado, con precios en pesos chilenos ($CLP).

## Stack Tecnológico

* **Backend**: Java 17, Spring Boot 3, Spring Data JPA
* **Base de Datos**: H2 Database (en memoria, cargada automáticamente al iniciar)
* **Frontend**: HTML5, CSS3 y JavaScript Vanilla (servido de forma estática por el mismo backend)
* **Gestor de Dependencias**: Maven (`./mvnw`)
* **Procesamiento de Datos**: Carga inicial desde archivo CSV con soporte estricto de codificación UTF-8.

---

## Arquitectura

El sistema está diseñado bajo una arquitectura modular tipo monolito, donde el servidor Spring Boot expone tanto la interfaz de usuario como la API REST en un único puerto (`8080`).


```text
Usuario
  |
  v
Spring Boot (Puerto 8080)
  |-- Frontend estático (HTML/CSS/JS) en /
  |-- API RESTful en /api/products
  |-- Carga de catalog.csv con UTF-8 a H2 Database en memoria
```

* La búsqueda por texto, los filtros dinámicos y la paginación se procesan directamente en el backend mediante consultas optimizadas con Spring Data JPA.
* El frontend no descarga el catálogo completo, garantizando un rendimiento óptimo en el cliente.

---

## Estructura del Repositorio

```text
.
├── AGENTS.md
├── ARCHITECTURE.md
├── README.md
├── SPECIFICATION.md
├── backend/
│   ├── .mvn/
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/vitrina/api/
│       │   │   ├── ApiApplication.java
│       │   │   ├── config/DataLoader.java
│       │   │   ├── controller/ProductController.java
│       │   │   ├── model/Product.java
│       │   │   └── repository/ProductRepository.java
│       │   └── resources/
│       │       ├── application.properties
│       │       ├── catalog.csv
│       │       └── static/
│       │           ├── index.html
│       │           ├── app.js
│       │           └── css/
│       │               └── styles.css
│       └── test/
```

---

## Configuración y Requisitos Locales

* **Java Development Kit (JDK)**: Versión 17 o superior.
* **Maven**: Incluido mediante Maven Wrapper (`./mvnw`), no requiere instalación manual previa.

### Ejecución Local

1. Abre tu terminal y posicionate en la carpeta del backend:
   ```bash
   cd backend
   ```

2. Ejecuta la aplicación usando Maven Wrapper:
   * En macOS / Linux:
     ```bash
     ./mvnw spring-boot:run
     ```
   * En Windows:
     ```cmd
     mvnw.cmd spring-boot:run
     ```

3. Abrir el navegador en:
   * **Interfaz Web**: http://localhost:8080
   * **Consola H2 (Base de datos)**: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:catalogdb`, sin contraseña).

---

## Pruebas Locales (Endpoints y cURL)

Puedes validar el funcionamiento de la API ejecutando los siguientes comandos en otra terminal:

* Listar productos paginados:
  ```bash
  curl "http://localhost:8080/api/products?page=0&size=12"
  ```

* Búsqueda por texto (ej. "queso"):
  ```bash
  curl "http://localhost:8080/api/products?search=queso&page=0&size=12"
  ```

* Obtener filtros disponibles (categorías y formatos):
  ```bash
  curl http://localhost:8080/api/products/filters
  ```

* Detalle de un producto por ID:
  ```bash
  curl http://localhost:8080/api/products/1
  ```

---

## Detalles de Implementación y Codificación (UTF-8)

Para garantizar la correcta visualización de tildes, eñes y caracteres especiales característicos del idioma español presentes en el archivo `catalog.csv`, se configuró explícitamente la codificación de caracteres en el archivo `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:catalogdb;DB_CLOSE_DELAY=-1;CHARACTER_ENCODING=UTF-8
server.servlet.encoding.charset=UTF-8
server.servlet.encoding.enabled=true
server.servlet.encoding.force=true
```

Asimismo, el componente `DataLoader.java` lee el archivo CSV especificando la codificación UTF-8 al mapear los registros hacia la entidad JPA `Product`.

---

## Documentación del Proyecto

* [SPECIFICATION.md](./SPECIFICATION.md): Requerimientos funcionales y contratos detallados de la API.
* [ARCHITECTURE.md](./ARCHITECTURE.md): Decisiones de diseño y distribución de componentes.
* [AGENTS.md](./AGENTS.md): Arnés de gobernanza e instrucciones operativas para asistentes de IA.
