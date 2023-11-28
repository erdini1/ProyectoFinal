package project.finalproject.respuestas;

public class RespuestaApi {

    private String mensaje;

    public RespuestaApi(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
