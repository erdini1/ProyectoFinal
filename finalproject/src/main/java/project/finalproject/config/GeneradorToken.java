package project.finalproject.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class GeneradorToken {

    @org.springframework.beans.factory.annotation.Value("${jwt.secret}")
    private String claveSecreta;

    @Value("${jwt.expiration}")
    private long expiracion;

    //Generador de Token a partir de los datos del usuario
    public String generarToken(String email, String nombre, String apellido) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiracion);

        return Jwts.builder()
                .setSubject(email)
                .claim("nombre", nombre)
                .claim("apellido", apellido)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, claveSecreta)
                .compact();
    }
}
