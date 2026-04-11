package com.ash.GenericTracker.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    private final String SECRET = "My-super-secret-key-my-super-secret-key-yy";
    private final long Expiration = 1000*60*60*1;

    public String generateToken(UUID userId){
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Expiration))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }
    public String extractUserId( String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean isValid(String token){
        try{
            extractUserId(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
