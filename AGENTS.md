# Instrucciones de Gobierno y Arnés Agéntico (AGENTS.md)

Directrices operativas para la asistencia de agentes de Inteligencia Artificial en el desarrollo y mantenimiento del proyecto.

## 1. Principios de Intervención
* **Separación de Responsabilidades**: El backend gestiona la lógica de negocio y persistencia; el frontend se limita estrictamente a la presentación y consumo asíncrono.
* **Principio KISS (Keep It Simple, Stupid)**: Evitar frameworks de frontend innecesarios (como React o Vue) cuando JavaScript Vanilla resuelve los requerimientos eficientemente.
* **Preservación de Estándares**: Respetar la estructura de carpetas estándar de Spring Boot y mantener la codificación UTF-8 en todos los archivos de texto y datos.

## 2. Lista de Verificación (Checklist) para Cambios
1. **Verificación de Compilación**: Comprobar que `./mvnw clean compile` se ejecute sin errores tras modificar entidades o controladores.
2. **Prueba de Conectividad**: Validar respuestas HTTP `200 OK` en los endpoints de productos y filtros.
3. **Validación de Consola**: Asegurarse de que el navegador no arroje errores de red o excepciones en JavaScript al interactuar con la interfaz.