package project.finalproject.usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import project.finalproject.prestamo.Prestamo;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Debe indicar un nombre")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "Debe indicar un apellido")
    @Column(name = "apellido")
    private String apellido;

    @Email
    @NotBlank(message = "Debe indicar un email")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Debe indicar una contraseña")
    @Column(name = "contraseña")
    private String contrasenia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

}
