# BassicBlog

BassicBlog es un sistema avanzado de gestión de blogs desarrollado con Java Spring Boot. Este proyecto incluye funcionalidades CRUD completas para publicaciones, categorías, etiquetas, usuarios, comentarios y roles. Además, permite la subida de imágenes mediante la integración con Imgbb y utiliza una base de datos PostgreSQL en un contenedor Docker.

## Funcionalidades principales

- **Gestión de publicaciones**: Crear, listar, actualizar y eliminar publicaciones, incluyendo la subida de imágenes.
- **Gestión de categorías**: Organizar las publicaciones en categorías, con opciones para crear, listar, actualizar y eliminar categorías.
- **Gestión de etiquetas**: Asignar etiquetas a las publicaciones para mejorar la búsqueda. Incluye funcionalidades para crear, listar, actualizar y eliminar etiquetas.
- **Gestión de usuarios**: Crear, listar, actualizar y eliminar usuarios.
- **Gestión de comentarios**: Añadir, listar, actualizar y eliminar comentarios en publicaciones.
- **Gestión de roles**: Crear, modificar, eliminar y listar roles.
- **Subida de imágenes**: Integración con Imgbb para el manejo de imágenes.

## Estructura de endpoints

### Publicaciones (/api/v1/post)

- `GET /api/v1/post`: Obtiene todas las publicaciones.
- `GET /api/v1/post/{id}`: Obtiene una publicación por su ID.
- `POST /api/v1/post`: Crea una publicación con imagen, título, contenido, autor, categoría y etiquetas.
- `PATCH /api/v1/post/{id}`: Actualiza una publicación existente.
- `DELETE /api/v1/post`: Elimina una publicación específica.

### Categorías (/api/v1/category)

- `GET /api/v1/category`: Obtiene todas las categorías.
- `GET /api/v1/category/{id}`: Obtiene una categoría por su ID.
- `POST /api/v1/category`: Crea una nueva categoría.
- `PATCH /api/v1/category/{id}`: Actualiza una categoría existente.
- `DELETE /api/v1/category/{id}`: Elimina una categoría específica.

### Etiquetas (/api/v1/tags)

- `GET /api/v1/tags`: Obtiene todas las etiquetas.
- `GET /api/v1/tags/{id}`: Obtiene una etiqueta por su ID.
- `POST /api/v1/tags`: Crea una nueva etiqueta.
- `PATCH /api/v1/tags/{id}`: Actualiza una etiqueta existente.
- `DELETE /api/v1/tags/{id}`: Elimina una etiqueta existente.

### Usuarios (/api/v1/user)

- `GET /api/v1/user`: Obtiene todos los usuarios.
- `GET /api/v1/user/{id}`: Obtiene un usuario por su ID.
- `POST /api/v1/user`: Crea un nuevo usuario.
- `PATCH /api/v1/user/{id}`: Actualiza un usuario existente.
- `DELETE /api/v1/user/{id}`: Elimina un usuario específico.

### Comentarios (/api/v1/comment)

- `GET /api/v1/comment`: Obtiene todos los comentarios.
- `GET /api/v1/comment/{id}`: Obtiene un comentario por su ID.
- `POST /api/v1/comment`: Crea un nuevo comentario.
- `PATCH /api/v1/comment/{id}`: Actualiza un comentario existente.
- `DELETE /api/v1/comment/{id}`: Elimina un comentario específico.

### Roles (/api/v1/role)

- `GET /api/v1/role`: Obtiene todos los roles.
- `GET /api/v1/role/{id}`: Obtiene un rol por su ID.
- `POST /api/v1/role`: Crea un nuevo rol.
- `PATCH /api/v1/role/{id}`: Actualiza un rol existente.
- `DELETE /api/v1/role/{id}`: Elimina un rol.

## Configuración

### Docker Compose

El proyecto utiliza una base de datos PostgreSQL configurada mediante Docker Compose. A continuación, se muestra el archivo `docker-compose.yaml`:

```yaml
services:
  postgres:
    image: 'postgres:latest'
    environment:
      - 'POSTGRES_DB=blogDB'
      - 'POSTGRES_PASSWORD=qwGa3PArSd435'
      - 'POSTGRES_USER=userblogDB'
    ports:
      - '5432:5432'
```

### Archivo `application.properties`

El archivo de configuración utiliza variables de entorno para gestionar los valores sensibles y la configuración dinámica:

```properties
spring.application.name=blog

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update

springdoc.swagger-ui.enabled=true
springdoc.api-docs.enabled=true

server.port=8088

# Configuración del token para Imgbb
imgbb.api.token=${IMGBB_API_TOKEN}
```

### Variables de entorno

Asegúrate de definir las siguientes variables de entorno antes de iniciar el proyecto:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/blogDB
export DB_USERNAME=userblogDB
export DB_PASSWORD=qwGa3PArSd435
export IMGBB_API_TOKEN=adbe48b6f1ccf0cfde757e0f2b70e15f
```

## Servicio de subida de imágenes

El proyecto utiliza **Imgbb** para gestionar la subida de imágenes. El token de la API se configura en el archivo `application.properties` como se muestra arriba. La clase `ImgbbService` se encarga de realizar las solicitudes a la API de Imgbb.

## Requisitos

- **Java** 17 o superior.
- **Maven** para la gestión de dependencias.
- **Docker** y **Docker Compose** para la base de datos.
- **Imgbb API Token** para subir imágenes.
- **Postman** (opcional) para probar los endpoints.

## Ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Yarigore/Bassic-blog.git
   cd BassicBlog
   ```

2. Levanta la base de datos con Docker:
   ```bash
   docker-compose up -d
   ```

3. Configura las variables de entorno necesarias.

4. Ejecuta el proyecto:
   ```bash
   mvn spring-boot:run
   ```

## Próximos pasos

- Implementar autenticación y autorización con **Spring Security**.
- Mejorar la validación y manejo de errores.
- Implementar clases DTO para estructurar los datos transferidos entre cliente y servidor.
- Añadir soporte para autenticación basada en JWT (JSON Web Tokens) para mejorar la seguridad.
  
---
