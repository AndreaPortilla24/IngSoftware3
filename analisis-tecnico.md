# Proyecto final – Análisis técnico y funcional

**Asignatura:** Ingeniería del Software III  
**Grupo:** G1  
**Integrantes:**
- 2211852 – Andrea Juliana Portilla Barrera
- 2211854 – Oscar Julián Rondón Rendón

**Presentado a:** Darío Alejandro Riaño Velandia  
**Universidad Industrial de Santander**  
Escuela de Ingeniería de Sistemas e Informática  
Bucaramanga, 2026

---

## 1. Identificación de microservicios y tecnologías utilizadas

El proyecto implementa una arquitectura basada en microservicios. Está compuesto por cinco servicios independientes, cada uno organizado alrededor de una responsabilidad del sistema académico. Estos servicios son autenticación, estudiantes, cursos, matrículas y calificaciones. La intención de esta arquitectura es separar las funcionalidades del sistema en componentes que puedan desarrollarse, mantenerse y evolucionar de manera independiente.

Los cinco microservicios identificados son:

| Microservicio | Responsabilidad principal |
|---|---|
| `auth-service` | Gestiona la autenticación de usuarios y la validación de tokens. |
| `estudiantes-service` | Administra la información de los estudiantes. |
| `cursos-service` | Gestiona los cursos y sus cupos máximos. |
| `matriculas-service` | Gestiona el registro y la anulación de matrículas. |
| `calificaciones-service` | Administra las notas por corte y calcula promedios ponderados. |

Esta distribución permite separar las operaciones académicas según su función. Por ejemplo, el registro de una matrícula no se realiza directamente desde el servicio de estudiantes o cursos, sino que está centralizado en `matriculas-service`.

### Tecnologías utilizadas

| Tecnología | Función dentro del proyecto |
|---|---|
| Java 21 | Lenguaje de programación. |
| Spring Boot 3.2.5 | Desarrollo de los microservicios. |
| Spring Data JPA | Persistencia y acceso a datos. |
| H2 | Bases de datos en memoria. |
| Spring Security | Implementación de mecanismos de seguridad. |
| JWT | Autenticación mediante tokens. |
| OpenFeign | Comunicación entre microservicios. |
| Swagger / OpenAPI | Documentación y pruebas de endpoints. |
| Maven | Gestión de dependencias y compilación. |
| Docker Compose | Ejecución coordinada de los servicios. |

El proyecto utiliza una base de datos H2 independiente para cada microservicio. Esto permite separar los datos de cada componente, aunque tiene una limitación: al trabajar en memoria, la información se pierde cuando se reinicia el servicio.

## 2. Responsabilidades y organización de los microservicios

Cada microservicio tiene una responsabilidad específica dentro de la plataforma académica. Aunque trabajan conjuntamente para ofrecer las funcionalidades del sistema, cada uno administra sus propias operaciones y datos.

- **Auth-service:** se encarga de autenticar a los usuarios mediante sus credenciales y generar tokens JWT. También permite validar los tokens utilizados por los demás servicios.
- **Estudiantes-service:** permite registrar, consultar, actualizar y eliminar estudiantes.
- **Cursos-service:** permite administrar los cursos disponibles, incluyendo la definición de sus cupos máximos.
- **Matrículas-service:** gestiona el proceso de inscripción de estudiantes a cursos, permite registrar y anular matrículas y consultar matrículas activas.
- **Calificaciones-service:** permite registrar y corregir las notas de los estudiantes, organizadas por cortes, y consultar los promedios ponderados.

Una característica importante es que los microservicios no funcionan completamente aislados. Algunas operaciones necesitan consultar información de otros servicios. Por ejemplo, para registrar una matrícula es necesario comprobar que el estudiante exista y que el curso tenga disponibilidad.

## 3. Arquitectura y organización del sistema

El sistema utiliza una arquitectura de microservicios desarrollada con Spring Boot. Cada servicio tiene su propia aplicación, configuración y base de datos, lo que permite separar las responsabilidades del sistema académico.

Para la comunicación entre servicios se utiliza OpenFeign, una herramienta de Spring Cloud que facilita realizar solicitudes HTTP a otras aplicaciones mediante interfaces Java. Además, cada servicio expone sus propias API REST, documentadas mediante Swagger, permitiendo consultar y probar las operaciones disponibles.

## 4. Funcionalidades principales y flujo de ejecución

El sistema ofrece diferentes funcionalidades relacionadas con la administración académica. Entre las principales se encuentran:

| Funcionalidad | Microservicio responsable |
|---|---|
| Inicio de sesión | Auth |
| Registro y consulta de estudiantes | Estudiantes |
| Administración de cursos | Cursos |
| Registro y anulación de matrículas | Matrículas |
| Registro y corrección de notas | Calificaciones |
| Consulta de notas y promedios | Calificaciones |

## 5. Roles y permisos

El sistema contempla tres tipos de usuarios: administrador, docente y estudiante.

| Funcionalidad | ADMIN | DOCENTE | ESTUDIANTE |
|---|---:|---:|---:|
| Consultar estudiantes | Sí | Sí | No |
| Crear, actualizar y eliminar estudiantes | Sí | No | No |
| Consultar cursos | Sí | Sí | Sí |
| Administrar cursos | Sí | No | No |
| Consultar matrículas generales | Sí | Sí | No |
| Registrar y anular matrículas | Sí | No | No |
| Consultar sus matrículas | No | No | Sí |
| Registrar y corregir calificaciones | Sí | Sí | No |
| Consultar sus calificaciones | No | No | Sí |

El mecanismo de seguridad utiliza JWT. Cuando un usuario inicia sesión, el servicio de autenticación verifica sus credenciales y genera un token que contiene información asociada a su identidad y rol.

Posteriormente, el usuario utiliza ese token para acceder a las operaciones permitidas. De esta manera, el sistema diferencia entre autenticación, que permite identificar al usuario, y autorización, que determina qué operaciones puede realizar.

## 6. Comunicación entre microservicios

La comunicación interna se realiza mediante solicitudes HTTP utilizando OpenFeign. Esto permite que un microservicio solicite información o ejecute operaciones expuestas por otro.

Las principales relaciones identificadas son:

| Servicio que solicita | Servicio consultado | Propósito |
|---|---|---|
| Estudiantes | Auth | Validación de tokens. |
| Cursos | Auth | Validación de tokens. |
| Matrículas | Auth | Validación de tokens. |
| Calificaciones | Auth | Validación de tokens. |
| Matrículas | Estudiantes | Verificar la existencia del estudiante. |
| Matrículas | Cursos | Verificar la existencia del curso y su disponibilidad. |
| Estudiantes | Matrículas | Consultar matrículas activas. |
| Cursos | Matrículas | Consultar matrículas activas. |
| Calificaciones | Matrículas | Consultar información de la matrícula. |

Una característica importante es que la comunicación es síncrona: el servicio que realiza la solicitud espera una respuesta del servicio consultado.

Esto facilita la validación de información, pero también genera dependencias. Si un servicio necesario no está disponible, una operación que dependa de él puede fallar.

## 7. Gestión y persistencia de datos

El proyecto utiliza H2 como sistema de gestión de bases de datos. Cada microservicio tiene su propia base de datos, lo que permite mantener separados los datos de estudiantes, cursos, matrículas, calificaciones y autenticación.

La persistencia se implementa utilizando Spring Data JPA, que facilita las operaciones de almacenamiento y consulta de información mediante entidades y repositorios.

El análisis del código fuente permitió identificar cinco entidades principales, cada una perteneciente a un microservicio:

| Microservicio | Entidad | Tabla |
|---|---|---|
| Auth | Usuario | `usuarios` |
| Estudiantes | Estudiante | `estudiante` |
| Cursos | Curso | `curso` |
| Matrículas | Matricula | `matricula` |
| Calificaciones | Calificacion | `calificacion` |

Un aspecto importante es que la búsqueda realizada no encontró anotaciones de relaciones JPA como `@ManyToOne`, `@OneToMany` o `@ManyToMany`. Esto indica que las relaciones entre los datos de diferentes microservicios no se representan mediante asociaciones directas de JPA. En su lugar, el sistema utiliza identificadores y consultas entre servicios para obtener la información necesaria.

## 8. Calidad del código y buenas prácticas

La organización del proyecto permite identificar una separación entre los cinco microservicios, cada uno encargado de funcionalidades específicas. El uso de Spring Boot, Spring Data JPA y Spring Security proporciona herramientas para organizar las operaciones, la persistencia y los mecanismos de seguridad.

También se evidencia una separación de los datos mediante bases de datos independientes para cada servicio. Esto permite mantener una organización distribuida de la información.

Sin embargo, existen dependencias entre los microservicios debido a las consultas que realizan entre sí. Por ejemplo, el servicio de matrículas necesita consultar estudiantes y cursos, mientras que estos últimos también consultan información de matrículas.

Esta situación puede aumentar el acoplamiento entre los servicios y dificultar el mantenimiento cuando alguno de ellos presenta cambios.

Por otra parte, la comunicación síncrona implica que la disponibilidad de algunos servicios puede afectar las operaciones de otros componentes.

Por lo tanto, aunque la arquitectura permite separar las responsabilidades, es importante evaluar las dependencias de comunicación y la organización interna de las clases para mejorar la mantenibilidad del sistema.

## 9. Seguridad, manejo de errores y pruebas

El sistema implementa autenticación mediante JWT y utiliza Spring Security para controlar el acceso a las funcionalidades. Se identifican tres roles principales: administrador, docente y estudiante, cada uno con diferentes permisos.

El proyecto contempla diferentes respuestas HTTP para identificar problemas durante la ejecución:

| Código HTTP | Significado |
|---:|---|
| 200 | Solicitud exitosa. |
| 201 | Recurso creado correctamente. |
| 400 | Error de validación o regla de negocio. |
| 401 | Falta de autenticación o token inválido. |
| 403 | El usuario no tiene permisos suficientes. |
| 404 | Recurso o ruta no encontrada. |
| 409 | Conflicto con los datos existentes. |
| 502 | Error en la comunicación con otro servicio. |

También se contempla una estructura uniforme para las respuestas de error, que incluye información como el mensaje, el código del error, el estado HTTP, la ruta solicitada y la fecha de ocurrencia.

Adicionalmente, el proyecto incluye pruebas automatizadas mediante Maven. Estas permiten verificar el comportamiento de las funcionalidades y detectar errores durante el desarrollo.

## 10. Propuestas de mejora

A partir del análisis de la arquitectura y de las tecnologías utilizadas, se identifican las siguientes oportunidades de mejora. Estas son recomendaciones y no funcionalidades que se hayan implementado como parte de este análisis.

### 10.1. Incorporar persistencia permanente

Actualmente, los microservicios utilizan bases de datos H2 en memoria, por lo que la información se pierde al reiniciar los servicios. Se propone utilizar motores de bases de datos con persistencia en disco, de acuerdo con las necesidades de cada servicio. Esto permitiría conservar la información entre ejecuciones.

### 10.2. Fortalecer la tolerancia a fallos en la comunicación

La comunicación entre microservicios es síncrona. Por esta razón, cuando un servicio consultado no está disponible, las operaciones que dependen de él pueden verse afectadas. Se propone incorporar mecanismos como tiempos de espera controlados (*timeouts*), reintentos limitados y patrones de circuit breaker, evaluando cuidadosamente cuáles solicitudes pueden repetirse sin generar efectos secundarios.

### 10.3. Controlar la concurrencia en la asignación de cupos

El proceso de matrícula requiere comprobar la disponibilidad de cupos de un curso. Si varias solicitudes intentan registrar estudiantes al mismo tiempo, podría existir el riesgo de superar el cupo máximo si la comprobación y la actualización no se realizan de manera segura. Se propone implementar mecanismos de control de concurrencia y operaciones atómicas para proteger esta regla de negocio.

### 10.4. Reducir el acoplamiento entre microservicios

Algunos servicios realizan consultas entre sí para obtener información necesaria para sus operaciones. Estas dependencias pueden dificultar la evolución independiente de los componentes. Se propone revisar las interacciones existentes y evaluar alternativas, como eventos o mecanismos de comunicación asíncrona, cuando el proceso de negocio lo permita.

### 10.5. Ampliar las pruebas automatizadas

Se propone fortalecer las pruebas existentes mediante pruebas unitarias, de integración y de comunicación entre microservicios. También sería conveniente incluir escenarios de error, indisponibilidad de servicios y solicitudes concurrentes para verificar el comportamiento del sistema ante condiciones distintas a las habituales.


