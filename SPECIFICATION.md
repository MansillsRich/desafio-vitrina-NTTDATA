# Especificación del Sistema - Vitrina de Productos

## 1. Propósito
Aplicación web orientada a la exploración fluida de un catálogo masivo de productos de supermercado, garantizando integridad de datos y respuestas rápidas.

## 2. Requerimientos Funcionales
* **Visualización en Grilla**: Tarjetas con imagen, categoría, nombre, precio formateado en pesos chilenos ($CLP) y formato.
* **Búsqueda Reactiva**: Filtrado por texto en tiempo real conectado al backend mediante un temporizador (debouncer).
* **Filtros Dinámicos**: Selección por categorías y formatos específicos obtenidos directamente del servidor.
* **Paginación del Servidor**: Navegación controlada por páginas (12 elementos por vista) para optimizar el rendimiento.
* **Manejo de Estados**: Retroalimentación visual para estados de carga, ausencia de resultados y errores de red.

## 3. Contrato de Endpoints API REST
* `GET /api/products`: Lista paginada de productos con filtros opcionales (`page`, `size`, `search`, `category`, `format`).
* `GET /api/products/{id}`: Detalle de un producto individual.
* `GET /api/products/filters`: Listados de categorías y formatos disponibles para los selectores.