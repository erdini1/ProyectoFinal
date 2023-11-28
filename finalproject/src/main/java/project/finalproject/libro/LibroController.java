package project.finalproject.libro;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import project.finalproject.config.DecoderToken;
import project.finalproject.error.ApiError;
import project.finalproject.respuestas.RespuestaApi;
import project.finalproject.usuario.Usuario;

import java.util.*;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private DecoderToken decoderToken;

    //Endpoint para listar Libros - GET /api/libros
    @GetMapping
    public ResponseEntity<List<Libro>> obtenerLibros(
            @RequestHeader(name = "Authorization", required = false) String token,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String autor) {
        List<Libro> libros;
        if (titulo != null) {
            libros = libroRepository.findByTituloContainingIgnoreCaseOrderByTituloAsc(titulo);
        } else if (estado != null) {
            libros = libroRepository.findByEstado(estado);
        } else if (genero != null) {
            libros = libroRepository.findByGeneroContainingIgnoreCaseOrderByTituloAsc(genero);
        } else if (autor != null) {
            libros = libroRepository.findByAutorContainingIgnoreCaseOrderByTituloAsc(autor);
        } else {
            libros = libroRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }

    //Endpoint para crear Libros - POST /api/libros
    @PostMapping
    public ResponseEntity<?> nuevoLibro(@Valid @RequestBody Libro libro, @RequestHeader(name = "Authorization", required = false) String token){
        Claims usuario = decoderToken.decodificarToken(token);

        libro.setEstado("disponible");
        libroRepository.save(libro);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RespuestaApi("Libro creado correctamente"));
    }

    //Endpoint para editar Libros - PUT /api/libros/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarLibro(@RequestBody Libro libro, @PathVariable long id, @RequestHeader(name = "Authorization", required = false) String token){
        Claims usuario = decoderToken.decodificarToken(token);

        Optional<Libro> libroOpcional = libroRepository.findById(id);
        if(libroOpcional.isPresent()){
            Libro libroExistente = libroOpcional.get();

            if(libro.getTitulo() != null) {
                libroExistente.setTitulo(libro.getTitulo());
            }
            if(libro.getAutor() != null) {
                libroExistente.setAutor(libro.getAutor());
            }
            if(libro.getGenero() != null) {
                libroExistente.setGenero(libro.getGenero());
            }
            if(libro.getNum_paginas() != 0) {
                libroExistente.setNum_paginas(libro.getNum_paginas());
            }
            if(libro.getSinopsis() != null) {
                libroExistente.setSinopsis(libro.getSinopsis());
            }

            libroRepository.save(libroExistente);
        } else {
            throw new ApiError(HttpStatus.BAD_REQUEST, "El id indicado no pertenece a un libro");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new RespuestaApi("Libro actualizado correctamente"));
    }

    //Endpoint para eliminar Libros - DELETE /api/libros/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLibro (@PathVariable long id, @RequestHeader(name = "Authorization", required = false) String token) {
        Claims usuario = decoderToken.decodificarToken(token);

        Optional<Libro> libroOpcional = libroRepository.findById(id);
        if(libroOpcional.isPresent()) {
            Libro libroExistente = libroOpcional.get();
            if(libroExistente.getEstado().equals("disponible")){
                libroRepository.deleteById(id);
            } else {
                throw new ApiError(HttpStatus.BAD_REQUEST, "El libro indicado no se puede eliminar debido a que se encuentra asociado a un prestamo");
            }
        } else {
            throw new ApiError(HttpStatus.BAD_REQUEST, "El id indicado no pertenece a un libro");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new RespuestaApi("Libro eliminado correctamente"));
    }

    //Endpoint para listar Libros prestados - GET /api/libros/prestados
    @GetMapping("/prestados")
    public List<Libro> obetenerLibrosPrestados (@RequestHeader(name = "Authorization", required = false) String token) {
        Claims usuario = decoderToken.decodificarToken(token);

        return libroRepository.findByEstado("prestado");
    }

    //Endpoint para listar Libros disponibles - GET /api/libros/disponibles
    @GetMapping("/disponibles")
    public List<Libro> obetenerLibrosDisponibles (@RequestHeader(name = "Authorization", required = false) String token) {
        Claims usuario = decoderToken.decodificarToken(token);

        return libroRepository.findByEstado("disponible");
    }


    //HandlerError para los campos que no se indiquen en el POST de libros. Para que los campos no sean nulos
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
