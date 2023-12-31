# Proyecto Laboratorio 4 - Con Docker

### Tecnologias: 
- JavaScript / VueJS 
- Java / Spring Boot

### Requisitos: 
- Docker

## Ejecución con Docker Compose

### Puertos:
- Backend (API): 8080
- Frontedn (FRONT):  5173
- PostgreSQL (DB): 5432


1. **Clona el Repositorio:**
   ```bash
   git clone https://github.com/erdini1/ProyectoFinal
   
   cd proyectoFinal

2. **Ejecutar docker-compose:**
    ```bash
   docker-compose up --build
Este comando iniciará el frontend VueJs, el backend Spring Boot y la base de datos PostgreSQL. 


3. **Acceder a la Aplicación:**
    #### Backend (API): 
    - http://localhost:8080/api/usuarios 
    - http://localhost:8080/api/libros
    - http://localhost:8080/api/prestamos
    #### Frontedn (FRONT): 
    - http://localhost:5173/login
    - http://localhost:5173/registro

4. **Detener y Limpiar Contenedores (Opcional):**
    ```bash
    docker-compose down
Este comando detendrá y eliminará los contenedores de Docker. Podes usar la opción -v para eliminar también los volúmenes de Docker.