# Proyecto Backend (Java/Spring Boot) - Con Docker

Backend del proyecto final de Laboratorio 4. Api para la gestión de libros, prestamos y usuarios.

Construido utilizando Java y Spring Boot.

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
Este comando iniciará el backend Spring Boot, el frontend VueJs y la base de datos PostgreSQL. 

La aplicación estará disponible en: 
- http://localhost:8080/api/usuarios
- http://localhost:8080/api/libros
- http://localhost:8080/api/prestamos

3. **Detener y Limpiar Contenedores (Opcional):**
    ```bash
    docker-compose down
Este comando detendrá y eliminará los contenedores de Docker. Podes usar la opción -v para eliminar también los volúmenes de Docker.

## Ejecución Individual sin Docker

Si se prefiere ejecutar el frontend de forma independiente sin Docker, seguir estos pasos:

### 1. **Configurar el application.properties**
Para la configuración de la base de datos de debe crear un archivo application.properties en la carpeta src/main/resources del proyecto backend. Copia y pega el siguiente formato, reemplazando los valores con tus datos:

```
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
jwt.secret=${CLAVE_SECRETA_JWT}
jwt.expiration=86400000
```

### 2. **Ejecutar servidor**

A través del editor de código, ejecuta la clase main del proyecto.
