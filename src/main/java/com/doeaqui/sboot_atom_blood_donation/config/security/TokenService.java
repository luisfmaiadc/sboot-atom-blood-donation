package com.doeaqui.sboot_atom_blood_donation.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.doeaqui.sboot_atom_blood_donation.domain.Login;
import com.doeaqui.sboot_atom_blood_donation.model.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret:secret}")
    private String secret;

    private static final String ISSUER = "API DoeAqui";

    public LoginResponse getToken(Login login) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Instant expirationInstant = expirationDate();
            OffsetDateTime expirationOffsetDateTime = expirationInstant.atOffset(ZoneOffset.of("-03:00"));
            String tokenJWT = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(login.getEmail())
                    .withExpiresAt(expirationInstant)
                    .sign(algorithm);
            return new LoginResponse(tokenJWT, expirationOffsetDateTime);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT: ", exception);
        }
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado.");
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00"));
    }
}