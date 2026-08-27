package co.edu.demoacademico.controller;

import co.edu.demoacademico.service.EstudianteService;
import jakarta.validation.Valid;
import co.edu.demoacademico.model.Estudiante;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// CAPA DE PRESENTACIÓN
// Esta clase recibe las solicitudes HTTP del cliente y devuelve
// las respuestas correspondientes. Se comunica con la capa de lógica de negocio (Service)

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService service;

    public EstudianteController(EstudianteService service) {
        this.service = service;
    }

    @PostMapping
    public Estudiante crear(@Valid @RequestBody Estudiante estudiante) {
        return service.crear(estudiante);
    }

    @GetMapping
    public List<Estudiante> listar() {
        return service.listar();
    }

    @GetMapping("/buscar")
    public Optional<Estudiante> buscar( String email) {
        return service.buscar(email);
    }
}
