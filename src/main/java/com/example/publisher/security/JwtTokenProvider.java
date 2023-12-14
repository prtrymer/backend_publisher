package com.example.publisher.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.publisher.models.Role;
import com.example.publisher.models.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    public String generateToken(Long id, String username, List<Role> roles) {
        return JWT.create()
                .withIssuer(jwtIssuer)
                .withSubject(username)
                .withClaim("id", id)
                .withClaim("roles", roles.stream().map(Role::getName).toList())
                .withExpiresAt(LocalDate.now()
                        .plusDays(15)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant())
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public Optional<DecodedJWT> toDecodedJWT(String token) {
        try {
            return Optional.of(JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withIssuer(jwtIssuer)
                    .build()
                    .verify(token));
        } catch (JWTVerificationException exception) {
            return Optional.empty();
        }
    }

    public String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(jwtSecret))
                .withIssuer(jwtIssuer)
                .build()
                .verify(token)
                .getSubject();
    }
    public UserEntity getUserInfo(String jwtToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();

            // Extract user information from the decoded token
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(claims.get("user_id", Long.class));
            userInfo.setUsername(claims.get("username", String.class));
            userInfo.setEmail(claims.get("email", String.class));
            // Add more fields as needed

            return userInfo;
        } catch (ExpiredJwtException e) {
            // Handle token expiration
            System.out.println("Token has expired.");
            return null;
        } catch (Exception e) {
            // Handle invalid token or other exceptions
            System.out.println("Invalid token or other exception.");
            return null;
        }
}