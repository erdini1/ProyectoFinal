# Proyecto Frontend (JavaScript/VueJS) - Con Docker

Fronted del proyecto final de Laboratorio 4. Interfaz de usuario construida en Vue.js. Se encarga de consumir la api y ofrecer una interfaz interactiva.

### Requisitos: 
- Docker

## Ejecución con Docker Compose
1. **Clona el Repositorio:**
   ```bash
   git clone https://github.com/erdini1/ProyectoFinal
   
   cd proyectoFinal

2. **Ejecutar docker-compose:**
    ```bash
   docker-compose up --build
Este comando iniciará el frontend VueJs, el backend Spring Boot y la base de datos PostgreSQL. 

La aplicación estará disponible en http://localhost:5173/api/login o http://localhost:5173/api/registro.

3. **Detener y Limpiar Contenedores (Opcional):**
    ```bash
    docker-compose down
Este comando detendrá y eliminará los contenedores de Docker. Podes usar la opción -v para eliminar también los volúmenes de Docker.