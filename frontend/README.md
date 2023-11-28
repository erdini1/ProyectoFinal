# Proyecto Frontend (JavaScript/VueJS) - Con Docker

Fronted del proyecto final de Laboratorio 4. Interfaz de usuario construida en Vue.js. Se encarga de consumir la api y ofrecer una interfaz interactiva.



## Ejecución con Docker Compose

### **Requisitos:** Docker


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

## Ejecución Individual sin Docker

Si se prefiere ejecutar el frontend de forma independiente sin Docker, seguir estos pasos:

### 1. **Instalar Dependencias:**
```bash
cd frontend
npm install
```

### 2. **Configurar archivo env.js:**

Crear un archivo env.js en la raíz de la carpeta frontend con el siguiente contenido.

```bash
const env = {
    API_ENDPOINT: 'http://localhost:8080/api',
};

export default env;
```

Reemplzar la URL con tu backend:

### 3. **Iniciar la Aplicación:**

```bash
npm run dev
```
La aplicación estará disponible en http://localhost:5173.

### 4. Detener la Aplicación:
Puedes detener la aplicación con Ctrl + C en la terminal.