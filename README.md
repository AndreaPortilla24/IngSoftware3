# IngSoftware3

- Andrea Juliana Portilla Barrera
- Oscar Julián Rondón Rendón

# Laboratorio 3 parte 2 - demo-academico

Sistema académico: Programa → Asignatura → Grupo → Matrícula.

## Cómo ejecutar
1. Requisitos: Java 17 y Maven.
2. Ejecutar: `./mvnw spring-boot:run` (o correr `DemoAcademicoApplication` desde IntelliJ).

## URLs
- Swagger: http://localhost:8080/swagger-ui.html
- Consola H2: http://localhost:8080/h2-console

## Arquitectura por módulos
estudiantes, programas, asignaturas, grupos, matriculas y common.

## Ports creados
- `EstudianteQueryPort` (módulo estudiantes), implementado por `EstudianteServiceImpl`
- `GrupoQueryPort` (módulo grupos), implementado por `GrupoServiceImpl`

`matriculas` solo depende de estos puertos, no de los repositorios de otros módulos.