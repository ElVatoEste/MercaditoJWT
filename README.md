# Ecommerce API

Este proyecto es una API de un sistema de ecommerce desarrollado con **Spring Boot** y **Spring Security**, donde los estudiantes pueden crear publicaciones (productos) con imágenes, gestionar su información personal y realizar autenticación basada en **JWT**.

## Funcionalidades Principales

- **Autenticación y autorización** con JWT.
- Los **estudiantes** pueden gestionar sus **publicaciones** (productos).
- Las **publicaciones** contienen información como **descripción**, **precio**, **stock**, y pueden tener múltiples imágenes.
- **Roles** para controlar el acceso a diferentes partes de la API.
- **Swagger** para la documentación de la API.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot** 
- **Spring Security** (con JWT)
- **JPA/Hibernate** para la persistencia de datos
- **Sql Server** 
- **Lombok** para simplificar el código
- **Swagger** para la documentación interactiva

## Instalación y Configuración

### Prerrequisitos

- Java 17
- JDK 20
- SQL SERVER
- Tener la base de datos ya creada

### Pasos de Instalación

1. Clona el repositorio:

   ```bash
   git clone https://github.com/ElVatoEste/MercaditoJWT.git ecommerce
   cd ecommerce
   
2. Configura el archivo application.properties con los detalles de tu base de datos:

   ```bash
   spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=*Nombre BD*;encrypt=true;trustServerCertificate=true
   spring.datasource.username=user
   spring.datasource.password=***
   spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

3. Compila y ejecuta la aplicación:

   ```bash
   mvn spring-boot:run

4. Accede a la documentación de la API en Swagger:

   ```bash
   http://localhost:8080/swagger-ui.html

## Estructura del Proyecto

```plaintext
    src/
     ├── main/
     │   ├── java/
     │   │   └── org/vato/ecommerce/
     │   │        ├── auth/        # Autenticación y JWT
     │   │        ├── config/      # Configuración de seguridad
     │   │        ├── Student/     # Entidades y lógica relacionada con estudiantes
     │   │        ├── Publication/ # Entidades y lógica de publicaciones (productos)
     │   │        ├── Image/       # Entidades y lógica de imágenes
     │   │        └── Rol/         # Entidades y lógica de roles
     │   └── resources/
     │       └── application.properties # Configuración de la base de datos
     └── test/   # Pruebas unitarias
```

## Funcionalidades Futuras
1. Gestión de carrito de compras.
2. Procesamiento de pagos.
3. Soporte para reseñas y comentarios en publicaciones.
4. Ampliación del sistema de roles y permisos.