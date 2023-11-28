package project.finalproject.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import project.finalproject.respuestas.RespuestaError;

@ControllerAdvice
public class ControladorErrores {

    //HandlerError para los errores de ApiError.
    @ExceptionHandler(ApiError.class)
    public ResponseEntity<?> ControladorApiError(ApiError ex) {
        return new ResponseEntity<>(new RespuestaError(ex.getMessage()), ex.getHttpStatus());
    }

    //HandlerError para los errores externos. Por ejemplo la Base de datos
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> ControladorErroresGenericos(Exception ex) {
        return new ResponseEntity<>(new RespuestaError("Error interno del servidor. Error: " + ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
