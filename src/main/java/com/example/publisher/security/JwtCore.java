package com.example.publisher.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

@Component
public class JwtCore {
    @Value("{publisher.app.secret}")
    private String secret;
    @Value("{publisher.app.lifetime}")
    private int lifetime;

}
