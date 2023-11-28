package project.finalproject.prestamo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import project.finalproject.libro.Libro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "La fecha de prestamo no puede ser nula")
    @Column(name = "fecha_prestamo")
    private LocalDate fprestamo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha_devolucion")
    private LocalDate fdevolucion;

    @JoinTable(
            name = "prestamo_libro",
            joinColumns = @JoinColumn(name = "id_prestamo", nullable = false),
            inverseJoinColumns = @JoinColumn(name="id_libro", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.MERGE )
    private List<Libro> libros;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public LocalDate getFprestamo() {
        return fprestamo;
    }

    public void setFprestamo(LocalDate fprestamo) {
        this.fprestamo = fprestamo;
    }

    public LocalDate getFdevolucion() {
        return fdevolucion;
    }

    public void setFdevolucion(LocalDate fdevolucion) {
        this.fdevolucion = fdevolucion;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

}
