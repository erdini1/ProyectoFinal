package project.finalproject.prestamo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByFdevolucionIsNotNull();


    List<Prestamo> findByFprestamoBetween(LocalDate fechaInicio, LocalDate fechaFin);


}
