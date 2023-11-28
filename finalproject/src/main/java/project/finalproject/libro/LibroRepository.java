package project.finalproject.libro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByEstado(String estado);
    List<Libro> findByTituloContainingIgnoreCaseOrderByTituloAsc(String titulo);
    List<Libro> findByGeneroContainingIgnoreCaseOrderByTituloAsc(String genero);
    List<Libro> findByAutorContainingIgnoreCaseOrderByTituloAsc(String autor);
}
