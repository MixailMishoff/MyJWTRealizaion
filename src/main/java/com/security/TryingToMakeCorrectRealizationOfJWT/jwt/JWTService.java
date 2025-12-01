package com.security.TryingToMakeCorrectRealizationOfJWT.jwt;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import com.auth0.jwt.JWT;


@Component
public class JWTService {
    @Value("${jwt-secret}")
    private String secret;

    /**
     * Способ подписи и создания JWT с использованием введенного секрета
     *
     * @param email {@link User#getEmail()}
     * @return {@link String} генерация токена по секрету
     * @throws IllegalArgumentException некорректность аргументов
     * @throws JWTCreationException     проблема сборки jwt
     */
    public String generateToken(String email) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("AllMine")
                .withClaim("email", email)
                .withIssuedAt(new Date())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60*24))
                .withIssuer("MIQ")
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * Способ проверки JWT,
     * а затем декодирования и извлечения электронной почты пользователя,
     * хранящейся в payload токена
     *
     * @param token {@link String} токен
     * @return {@link String} email пользователя
     */

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException, JsonProcessingException{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return  jwt.getClaim("email").asString();
    }
}
