package project.finalproject.libro;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import project.finalproject.prestamo.Prestamo;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Debe indicar un titulo")
    @Column(name = "titulo")
    private String titulo;

    @NotBlank(message = "Debe indicar un autor")
    @Column(name = "autor")
    private String autor;

    @NotBlank(message = "Debe indicar un genero")
    @Column(name = "genero")
    private String genero;

    @NotNull(message = "El número de páginas no puede ser nulo")
    @Column(name = "num_paginas")
    private int num_paginas;

    @NotBlank(message = "Debe indicar una sinopsis")
    @Column(name = "sinopsis")
    private String sinopsis;

    @Column(name = "estado", columnDefinition = "VARCHAR(255) default 'disponible'")
    private String estado;

    @JsonIgnore
    @ManyToMany(mappedBy = "libros")
    private List<Prestamo> prestamos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getNum_paginas() {
        return num_paginas;
    }

    public void setNum_paginas(int num_paginas) {
        this.num_paginas = num_paginas;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
}
