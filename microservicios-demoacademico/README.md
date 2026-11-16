# Laboratorio 04 — Microservicios con JWT, Swagger, OpenFeign y Docker

Ingeniería de Software III. Dos microservicios con H2 embebido:

- **auth-service** (puerto 8081): autentica usuarios, valida contraseñas con BCrypt y genera/valida tokens JWT.
- **consulta-service** (puerto 8082): CRUD de estudiantes protegido por token. Un filtro intercepta `/api/estudiantes` y valida el token contra auth-service mediante OpenFeign.

El proyecto base (`demo-academico`, puerto 8080) está en la raíz del repositorio.

## Ejecución local

Requisitos: Java 21 y Maven.

1. Iniciar primero **auth-service**:
   ```bash
   cd auth-service
   mvn spring-boot:run
   ```
2. En otra terminal, iniciar **consulta-service**:
   ```bash
   cd consulta-service
   mvn spring-boot:run
   ```

`consulta-service` apunta a `http://localhost:8081` (propiedad `services.auth.url`).

## Ejecución con Docker Compose

Requisitos: Docker Desktop en ejecución.

```bash
cd auth-service && mvn clean package -DskipTests && cd ..
cd consulta-service && mvn clean package -DskipTests && cd ..
docker compose up --build
```

Dentro de Docker, consulta-service usa `SERVICES_AUTH_URL=http://auth-service:8081` (nombre del servicio y puerto interno). Para apagar: `docker compose down`.

## URLs

| Recurso | URL |
|---|---|
| Swagger auth-service | http://localhost:8081/swagger-ui.html |
| Swagger consulta-service | http://localhost:8082/swagger-ui.html |
| H2 Console auth-service | http://localhost:8081/h2-console (JDBC URL: `jdbc:h2:mem:authdb`, usuario `sa`, sin contraseña) |
| H2 Console consulta-service | http://localhost:8082/h2-console (JDBC URL: `jdbc:h2:mem:demoacademico`, usuario `sa`, sin contraseña) |

## Usuarios de prueba

| Usuario | Contraseña | Rol |
|---|---|---|
| admin | Admin2026* | ADMIN |
| docente | Docente2026* | DOCENTE |
| estudiante1 | Estu2026* | ESTUDIANTE |

## Flujo correcto del botón Authorize

1. En el Swagger de auth-service, ejecutar `POST /auth/login` con un usuario de prueba.
2. Copiar **solo** el valor del campo `token` (sin comillas, sin la palabra `token` y sin el prefijo `Bearer`).
3. En el Swagger de consulta-service, pulsar **Authorize**, pegar el token tal cual y pulsar **Authorize**.
4. Ejecutar `GET /api/estudiantes`. Swagger envía automáticamente `Authorization: Bearer <token>`.

Resultados esperados: con token válido → 200; sin token → 401 `Token no enviado o formato inválido`; con token inválido o vencido → 401 `Token inválido o expirado`.
