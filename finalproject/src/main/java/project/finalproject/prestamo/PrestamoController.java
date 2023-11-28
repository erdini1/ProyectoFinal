package project.finalproject.prestamo;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import project.finalproject.config.DecoderToken;
import project.finalproject.error.ApiError;
import project.finalproject.libro.Libro;
import project.finalproject.libro.LibroRepository;
import project.finalproject.respuestas.RespuestaApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private DecoderToken decoderToken;

    //Endpoint para listar Prestamos - GET /api/prestamos
    @GetMapping
    public ResponseEntity<List<Prestamo>> obtenerPrestamos(
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            @RequestHeader(name = "Authorization", required = false) String token) {
        try {
            List<Prestamo> prestamos;

            if (fechaInicio != null && fechaFin != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
                LocalDate fin = LocalDate.parse(fechaFin, formatter);
                prestamos = prestamoRepository.findByFprestamoBetween(inicio, fin);
            } else {
                prestamos = prestamoRepository.findAll();
            }
            return ResponseEntity.ok(prestamos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //Endpoint para crear un Prestamo - POST /api/prestamos
    @PostMapping
    public ResponseEntity<?> nuevoPrestamo(@RequestBody Prestamo prestamo, @RequestHeader(name = "Authorization", required = false) String token) {
        Claims usuario = decoderToken.decodificarToken(token);

        if (prestamo.getFprestamo() == null) {
            prestamo.setFprestamo(LocalDate.now());
        }
        List<Libro> libros = prestamo.getLibros().stream()
                .map(libro -> {
                    Libro libroActualizado = libroRepository.findById(libro.getId()).orElse(null);
                    if (libroActualizado != null && "disponible".equals(libroActualizado.getEstado())) {
                        libroActualizado.setEstado("prestado");
                        return libroActualizado;
                    } else {
                        throw new ApiError(HttpStatus.BAD_REQUEST, "El libro con id: " + libro.getId() +" no se encuentra para prestar");
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (libros.isEmpty()) {
            throw new ApiError(HttpStatus.BAD_REQUEST,"Debe indicar los libros que desea prestar");
        }
        prestamo.setLibros(libros);
        prestamoRepository.save(prestamo);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RespuestaApi("Prestamo creado correctamente"));
    }

    //Endpoint para editar un Prestamo - PUT /api/prestamos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> editarPrestamo(@RequestBody Prestamo prestamo, @PathVariable long id, @RequestHeader(name = "Authorization", required = false) String token) {
        Claims usuario = decoderToken.decodificarToken(token);

        Optional<Prestamo> prestamoOpcional = prestamoRepository.findById(id);
        if (prestamoOpcional.isPresent()) {
            Prestamo prestamoExistente = prestamoOpcional.get();

            if (prestamo.getFprestamo() != null) {
                prestamoExistente.setFprestamo(prestamo.getFprestamo());
            }

            if (prestamo.getFdevolucion() != null) {
                prestamoExistente.setFdevolucion(prestamo.getFdevolucion());
            }

            List<Libro> librosNuevos = new ArrayList<>();
            List<Libro> librosExistente = prestamoExistente.getLibros();

            for (Libro libroNuevo : prestamo.getLibros()) {
                Optional<Libro> libroExistenteOptional = libroRepository.findById(libroNuevo.getId());

                if (libroExistenteOptional.isPresent()) {
                    Libro libroExistente = libroExistenteOptional.get();

                    if (librosExistente.contains(libroExistente)) {
                        librosNuevos.add(libroExistente);
                    } else {
                        if ("disponible".equals(libroExistente.getEstado())) {
                            libroExistente.setEstado("prestado");
                            librosNuevos.add(libroExistente);
                        } else {
                            throw new ApiError(HttpStatus.BAD_REQUEST, "El libro con id: " + libroExistente.getId() + " no está disponible para prestar");
                        }
                    }
                } else {
                    throw new ApiError(HttpStatus.BAD_REQUEST, "El libro con id: " + libroNuevo.getId() + " no existe");
                }
            }

            for (Libro libroExistente : librosExistente) {
                if (!librosNuevos.contains(libroExistente)) {
                    libroExistente.setEstado("disponible");
                    libroRepository.save(libroExistente);
                }
            }

            if (librosNuevos.isEmpty()) {
                throw new ApiError(HttpStatus.BAD_REQUEST, "Debe indicar los libros que desea prestar");
            }

            prestamoExistente.setLibros(librosNuevos);
            prestamoRepository.save(prestamoExistente);

            return ResponseEntity.status(HttpStatus.OK).body(new RespuestaApi("Préstamo modificado correctamente"));
        } else {
            throw new ApiError(HttpStatus.BAD_REQUEST, "El id indicado no pertenece a un préstamo");
        }
    }


    //Endpoint para eliminar un Prestamo - DELETE /api/prestamos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPrestamo(@PathVariable long id, @RequestHeader(name = "Authorization", required = false) String token) {
        Claims usuario = decoderToken.decodificarToken(token);

        Optional<Prestamo> prestamoOpcional = prestamoRepository.findById(id);

        if (prestamoOpcional.isPresent()) {
            Prestamo prestamoExistente = prestamoOpcional.get();
            prestamoExistente.getLibros().forEach(libro -> libro.setEstado("disponible"));
            prestamoRepository.deleteById(id);

            return ResponseEntity.status(HttpStatus.OK).body(new RespuestaApi("Préstamo eliminado correctamente"));
        } else {
            throw new ApiError(HttpStatus.BAD_REQUEST, "El id indicado no pertenece a un préstamo");
        }
    }

    //Endpoint para editar la fecha de devolución de un Prestamo - PUT /api/prestamos/devolucion/{id}
    @PutMapping("/devolucion/{id}")
    public ResponseEntity<?> editarDevolucion(@RequestBody Prestamo prestamo, @PathVariable long id, @RequestHeader(name = "Authorization", required = false) String token) {
        Claims usuario = decoderToken.decodificarToken(token);

        Optional<Prestamo> prestamoOpcional = prestamoRepository.findById(id);
        if (prestamoOpcional.isPresent()) {
            Prestamo prestamoExistente = prestamoOpcional.get();

            if (prestamo.getFdevolucion() == null) {
                throw new ApiError(HttpStatus.BAD_REQUEST, "Debe indicar un fecha de devolución");
            }
            prestamoExistente.setFdevolucion(prestamo.getFdevolucion());
            prestamoRepository.save(prestamoExistente);

            return ResponseEntity.status(HttpStatus.OK).body(new RespuestaApi("Fecha de devolución modificada correctamente"));
        } else {
            throw new ApiError(HttpStatus.BAD_REQUEST, "El id indicado no pertenece a un préstamo");
        }
    }

    //Endpoint para listar los prestamos que ya fueron devueltos - PUT /api/prestamos/devueltos/
    @GetMapping("/devueltos")
    public List<Prestamo> obtenerPrestamosDevueltos(@RequestHeader(name = "Authorization", required = false) String token) {
        Claims usuario = decoderToken.decodificarToken(token);

        return prestamoRepository.findByFdevolucionIsNotNull();
    }

    //HandlerError para los campos que no se indiquen en el POST de prestamos. Para que los campos no sean nulos
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
