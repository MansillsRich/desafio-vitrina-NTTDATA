# Arquitectura y Decisiones de Diseño

## 1. Arquitectura del Sistema
El proyecto implementa una arquitectura desacoplada a nivel de código pero integrada en ejecución mediante un monolito modular:

* **Capa Backend (`/backend`)**: Desarrollada en **Java 17** y **Spring Boot 3**. Utiliza Spring Data JPA para interactuar con una base de datos **H2 en memoria**, cargada mediante un script inicialador con codificación UTF-8.
* **Capa Frontend (`/frontend` o estáticos en resources)**: Desarrollada con **HTML5, CSS3 y JavaScript Vanilla** puro, consumiendo la API REST de forma asíncrona mediante la API `fetch`.

## 2. Decisiones Técnicas Clave
* **Procesamiento en Base de Datos**: La paginación, filtros y búsquedas se resuelven a nivel de repositorio JPA para evitar sobrecargar la memoria del cliente.
* **Codificación UTF-8**: Forzada en las propiedades de la aplicación para preservar tildes y caracteres especiales en español.
* **Fallback de Imágenes**: Gestión de errores en el cliente para mostrar elementos visuales alternativos ante rutas de imágenes vacías o rotas.