package co.edu.demoacademico.service;

import co.edu.demoacademico.repository.EstudianteRepository;
import co.edu.demoacademico.model.Estudiante;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// CAPA LÓGICA
// Esta clase contiene las reglas y procesos del negocio.

@Service
public class EstudianteService {

    private final EstudianteRepository repository;

    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
    }

    public Estudiante crear(Estudiante estudiante) {

        // ----------------------------
        // ZONA DE LÓGICA DE NEGOCIO:
        // Regla: email único
        // ----------------------------
        repository.findByEmail(estudiante.getEmail())
                .ifPresent(e -> {
                    throw new IllegalStateException("Email ya registrado");
                });

        // ============================
        // ZONA DE ACCESO A LA BD:
        // Persistencia vía Repository
        // ============================
        return repository.save(estudiante);
    }

    public List<Estudiante> listar() {
        // ============================
        // ZONA DE ACCESO A LA BD:
        // Consulta vía Repository
        // ============================
        return repository.findAll();
    }

    public Optional<Estudiante> buscar(String email) {

        return repository.findByEmail(email);
    }
}
