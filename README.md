Ecommerce API
Este proyecto es una API de un sistema de ecommerce desarrollada con Spring Boot y Spring Security, donde los estudiantes pueden crear publicaciones (productos) con imágenes, gestionar su información personal, y realizar autenticación basada en JWT. La API gestiona usuarios, roles y publicaciones, y está protegida mediante autenticación basada en tokens JWT.

Funcionalidades Principales
Autenticación y autorización con JWT.
Estudiantes pueden gestionar su información personal y publicaciones.
Publicaciones (productos) con atributos como precio, stock, y múltiples imágenes.
Configuración de CORS para desarrollo y producción.
Roles para gestionar acceso a diferentes partes de la API.
Tecnologías Utilizadas
Java 17
Spring Boot 3.x
Spring Security con JWT
JPA/Hibernate para la persistencia de datos
H2 (Base de datos en memoria) o MySQL (configuración a elección)
Lombok para reducir la escritura de código repetitivo
Swagger para la documentación de la API
Estructura del Proyecto
El proyecto sigue una arquitectura básica de controladores, servicios y repositorios, con el siguiente enfoque:

Entidades Principales:
Student: Los estudiantes pueden registrarse, autenticar y gestionar sus publicaciones.
Publication: Las publicaciones son los productos del ecommerce, que contienen atributos como descripción, precio, stock, y están asociadas a imágenes.
Image: Almacena las imágenes asociadas a una publicación.
Rol: Gestiona los roles de los estudiantes (como "Admin" y "User").
Seguridad:
La API utiliza Spring Security con JWT (JSON Web Tokens) para la autenticación. Las rutas sensibles están protegidas y requieren que el usuario inicie sesión y posea un rol específico.

Roles y Permisos: La API puede gestionar múltiples roles. Un estudiante solo tiene un rol, pero las rutas pueden ser accesibles por múltiples roles.
CORS: Configurado para permitir todos los orígenes en desarrollo, pero restringible en producción.
CSRF: Deshabilitado ya que se utiliza JWT (sin estado).
Configuración de Seguridad:
El archivo de configuración principal de seguridad es SecurityConfig.java. Aquí puedes ver cómo se protegen las rutas y cómo se manejan los permisos:

java
Copiar código
.requestMatchers("/api/**").hasAnyAuthority("Admin", "User")
Esto permite que tanto los usuarios con rol "Admin" como "User" puedan acceder a las rutas bajo /api/**.

Endpoints Principales
Autenticación
POST /auth/login: Autenticar un estudiante y devolver un token JWT.
POST /auth/register: Registrar un nuevo estudiante.
POST /auth/logout: Cerrar sesión y eliminar el token JWT de la cookie.
Estudiantes
GET /api/students/{id}: Obtener la información de un estudiante por su ID.
PUT /api/students/{id}: Actualizar la información de un estudiante.
Publicaciones
GET /api/publications: Obtener todas las publicaciones disponibles.
POST /api/publications: Crear una nueva publicación (solo para estudiantes autenticados).
PUT /api/publications/{id}: Actualizar una publicación existente.
DELETE /api/publications/{id}: Eliminar una publicación (solo estudiantes propietarios o administradores).
Roles
POST /api/roles: Crear un nuevo rol (para administradores).
GET /api/roles: Obtener todos los roles disponibles.
Instalación y Configuración
Prerrequisitos
Java 17
Maven 3.x
MySQL (o cualquier otra base de datos de tu preferencia)
Pasos de Instalación
Clona este repositorio:

bash
Copiar código
git clone https://github.com/tuusuario/ecommerce-api.git
cd ecommerce-api
Configura el archivo application.properties con los detalles de tu base de datos:

properties
Copiar código
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Ejecuta la aplicación:

bash
Copiar código
mvn spring-boot:run
Accede a la documentación de la API en Swagger:

bash
Copiar código
http://localhost:8080/swagger-ui.html
Estructura del Código
plaintext
Copiar código
src/
 ├── main/
 │   ├── java/
 │   │   └── org/vato/ecommerce/
 │   │        ├── auth/        # Autenticación y manejo de JWT
 │   │        ├── config/      # Configuración de seguridad y CORS
 │   │        ├── Student/     # Entidades y lógica relacionada a estudiantes
 │   │        ├── Publication/ # Entidades y lógica de las publicaciones (productos)
 │   │        ├── Image/       # Lógica y almacenamiento de imágenes
 │   │        └── Rol/         # Entidades y gestión de roles
 │   └── resources/
 │       └── application.properties # Configuración de la base de datos y otros
 └── test/   # Tests unitarios
 
Funcionalidades Futuras
Gestión de un carrito de compras.
Procesamiento de pagos.
Soporte para comentarios en publicaciones.
Implementación de roles más detallados y permisos específicos.


