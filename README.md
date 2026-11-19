# Documentación técnica – Proyecto de Ingeniería de Software III

## 1. Descripción

Este directorio contiene la documentación correspondiente al análisis técnico y funcional del proyecto desarrollado para la asignatura **Ingeniería de Software III**.

El proyecto consiste en una plataforma académica implementada mediante una arquitectura de microservicios. Su propósito es gestionar diferentes procesos académicos, como la autenticación de usuarios, la administración de estudiantes y cursos, las matrículas y las calificaciones.

La documentación presenta el análisis de la arquitectura, las responsabilidades de cada microservicio, sus mecanismos de comunicación, la gestión de datos, la seguridad y las oportunidades de mejora identificadas.

## 2. Arquitectura del sistema

El sistema está compuesto por cinco microservicios independientes:

| Microservicio | Puerto | Responsabilidad |
|---|---:|---|
| Auth Service | 8081 | Autenticación y validación de usuarios mediante JWT. |
| Estudiantes Service | 8082 | Gestión de la información de los estudiantes. |
| Cursos Service | 8083 | Administración de cursos y cupos disponibles. |
| Matrículas Service | 8084 | Registro, consulta y anulación de matrículas. |
| Calificaciones Service | 8085 | Registro y consulta de calificaciones académicas. |

Los servicios se comunican mediante API REST y utilizan OpenFeign para realizar llamadas entre ellos.

### Informe técnico

Contiene el análisis del sistema a partir de los siguientes aspectos:

1. Identificación de microservicios y tecnologías.
2. Responsabilidades de cada microservicio.
3. Arquitectura, capas y dependencias.
4. Funcionalidades y participación de los servicios.
5. Roles y permisos.
6. Mecanismos de comunicación.
7. Modelo de datos y persistencia.
8. Calidad del código y buenas prácticas.
9. Seguridad, manejo de errores y pruebas.
10. Propuestas de mejora.

### Diagramas

Incluye las representaciones gráficas utilizadas para explicar la arquitectura y el funcionamiento del sistema:

- **Arquitectura general:** muestra los cinco microservicios, sus responsabilidades, puertos y mecanismos de comunicación.
- **Secuencia de matrícula:** representa la interacción entre los servicios durante el proceso de registro de una matrícula.
- **Modelo de persistencia:** presenta las entidades principales y las bases de datos utilizadas por cada microservicio.

Los diagramas están elaborados en formato Mermaid (`.mmd`).

## 4. Tecnologías identificadas

Durante el análisis del proyecto se identificaron las siguientes tecnologías y herramientas:

- **Java 21:** lenguaje de programación.
- **Spring Boot 3.2.5:** framework para el desarrollo de los microservicios.
- **Spring Data JPA:** persistencia y acceso a datos.
- **Spring Security:** mecanismos de seguridad y autorización.
- **JWT:** autenticación mediante tokens.
- **OpenFeign:** comunicación entre microservicios.
- **H2:** sistema de gestión de bases de datos utilizado por los servicios.
- **Maven:** gestión de dependencias y construcción del proyecto.
- **Swagger / OpenAPI:** documentación de las API REST.
- **Docker Compose:** herramienta para facilitar la ejecución de los servicios.

## 5. Alcance del análisis

El trabajo se centra en el análisis técnico y funcional del proyecto existente. Se examinan sus componentes, responsabilidades, interacciones, persistencia y mecanismos de seguridad, con el propósito de comprender su funcionamiento e identificar oportunidades de mejora.

Las propuestas presentadas en la documentación corresponden a recomendaciones derivadas del análisis y no implican que hayan sido implementadas en el código fuente.
