package co.edu.demoacademico.service;

import co.edu.demoacademico.repository.EstudianteRepository;
import co.edu.demoacademico.model.Estudiante;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
        // ============================
        // ZONA DE LÓGICA DE NEGOCIO:
        // Regla: email único
        // ============================
        if (repository.existsByEmail(estudiante.getEmail())) {
            throw new EmailDuplicadoException(estudiante.getEmail());
        }
        // ============================
        // ZONA DE ACCESO A LA BD:
        // Persistencia vía repositorio
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

    public Page<Estudiante> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Estudiante buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new EstudianteNoEncontradoException(email));
    }

}
