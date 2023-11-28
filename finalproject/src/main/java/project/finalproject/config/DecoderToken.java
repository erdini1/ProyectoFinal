package project.finalproject.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import project.finalproject.error.ApiError;

@Component
public class DecoderToken {

    @org.springframework.beans.factory.annotation.Value("${jwt.secret}")
    private String claveSecreta;

    //Función para decodificar el token a partir de la clave secreta y el token
    public Claims decodificarToken(String StringToken) {
        try {
            if (StringToken == null || StringToken.isEmpty()) {
                throw new ApiError(HttpStatus.UNAUTHORIZED, "Se requiere un token de autorización");
            }
            String token = StringToken.substring(7);
            return Jwts.parser()
                    .setSigningKey(claveSecreta)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new ApiError(HttpStatus.UNAUTHORIZED ,"Token inválido");
        }
    }
}
