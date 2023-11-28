package project.finalproject.usuario;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import project.finalproject.config.DecoderToken;
import project.finalproject.config.GeneradorToken;
import project.finalproject.error.ApiError;
import project.finalproject.libro.Libro;
import project.finalproject.prestamo.Prestamo;
import project.finalproject.respuestas.RespuestaApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    private GeneradorToken generadorToken;

    @Autowired
    private DecoderToken decoderToken;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //Endpoint para listar Usuarios - GET /api/usuarios
    @GetMapping
    public List<Usuario> obtenerUsuarios () {
        return usuarioRepository.findAll();
    }

    //Endpoint para crear Usuarios - POST /api/usuarios
    @PostMapping
    public ResponseEntity<?> nuevoUsuario(@Valid @RequestBody Usuario usuario){
        if(usuarioRepository.findByEmail(usuario.getEmail()) != null){
            throw new ApiError(HttpStatus.UNAUTHORIZED, "El correo ya esta en uso");
        }

        String hashedPassword = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(hashedPassword);

        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RespuestaApi("Usuario creado correctamente"));
    }

    //Endpoint para modificar al usuario - PUT /api/usuarios/{id}}
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarUsuario(@RequestBody Usuario usuario, @PathVariable long id) {
        Optional<Usuario> usuarioOpcional = usuarioRepository.findById(id);

        if (usuarioOpcional.isPresent()) {
            Usuario usuarioExistente = usuarioOpcional.get();

            if (usuario.getNombre() != null) {
                usuarioExistente.setNombre(usuario.getNombre());
            }
            if (usuario.getApellido() != null) {
                usuarioExistente.setApellido(usuario.getApellido());
            }
            if (usuario.getEmail() != null && !usuario.getEmail().equals(usuarioExistente.getEmail())) {
                if (usuarioRepository.findByEmail(usuario.getEmail()) == null) {
                    usuarioExistente.setEmail(usuario.getEmail());
                } else {
                    throw new ApiError(HttpStatus.BAD_REQUEST, "El email ya pertenece a otro usuario");
                }
            }
            if (usuario.getContrasenia() != null) {
                String hashedPassword = passwordEncoder.encode(usuario.getContrasenia());
                usuarioExistente.setContrasenia(hashedPassword);
            }
            usuarioRepository.save(usuarioExistente);
        } else {
            throw new ApiError(HttpStatus.BAD_REQUEST, "El id indicado no pertenece a un usuario");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new RespuestaApi("Usuario actualizado correctamente"));
    }

    //Endpoint para eliminar al usuario - DELETE /api/usuarios/{id}}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable long id) {
        Optional<Usuario> usuarioOpcional = usuarioRepository.findById(id);
        if (usuarioOpcional.isPresent()) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new RespuestaApi("Usuario eliminado correctamente"));
        } else {
            throw new ApiError(HttpStatus.BAD_REQUEST, "El id indicado no pertenece a un usuario");
        }
    }

    //Endpoint para listar los datos del usarios pasado con el token de JWT - GET /api/usuarios/perfil
    @GetMapping("/perfil")
    public ResponseEntity<?> obtenerUnUsuario ( @RequestHeader(name = "Authorization") String token) {
        Claims usuario = decoderToken.decodificarToken(token);

        String email = usuario.getSubject();
        String nombre = usuario.get("nombre", String.class);
        String apellido = usuario.get("apellido", String.class);

        String respuestaFormateada = String.format("Usuario: %s |---| Nombre: %s |---| Apellido: %s", email, nombre, apellido);
        return ResponseEntity.status(HttpStatus.OK).body(new RespuestaApi(respuestaFormateada));
    }

    //Endpoint para loguear al usario - POST /api/usuarios/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Usuario usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioEncontrado != null && passwordEncoder.matches(usuario.getContrasenia(), usuarioEncontrado.getContrasenia())) {
            return ResponseEntity.status(HttpStatus.OK).body(new RespuestaApi(generadorToken.generarToken(
                    usuarioEncontrado.getEmail(),
                    usuarioEncontrado.getNombre(),
                    usuarioEncontrado.getApellido()
            )));
        } else {
            throw new ApiError(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }
    }

    //HandlerError para los campos que no se indiquen en el POST de usuarios. Para que los campos no sean nulos
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
