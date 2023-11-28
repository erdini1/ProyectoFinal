package project.finalproject.error;


import org.springframework.http.HttpStatus;

public class ApiError extends RuntimeException{

    private final HttpStatus httpStatus;

    public ApiError(HttpStatus httpStatus, String mensaje) {
        super(mensaje);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}