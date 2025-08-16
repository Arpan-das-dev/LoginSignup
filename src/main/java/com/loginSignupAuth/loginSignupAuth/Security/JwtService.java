package com.loginSignupAuth.loginSignupAuth.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    private Key getKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
    public String token(UserDetails details){
        return Jwts.builder()
                .setSubject(details.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+3600*1000*10))
                .signWith(SignatureAlgorithm.HS256,getKey())
                .compact();

    }
    public String extractUsername(String token){
        return getAllClaims(token).getSubject();
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean isValid(UserDetails details, String token){
        String username = extractUsername(token);
        Date expiration = getAllClaims(token).getExpiration();
        return (username.equals(details.getUsername()) && expiration.after(new Date()));
    }

}
