package com.app.backend.backend_service.service.security;

import com.app.backend.backend_service.model.JwtSubjectResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;
    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    private final ObjectMapper objectMapper;

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

   public String extractAccount(String token){
        return extractClaim(token, Claims::getSubject);
   }

   public JwtSubjectResponse extractSubject(String token){
       try {
           return objectMapper.readValue(extractAccount(token), JwtSubjectResponse.class);
       } catch (JsonProcessingException e) {
           log.error("failed convert json subject claims !");
           throw new RuntimeException(e);
       }
   }

    public String generateToken(Map<String,Object> extractClaims, String subject){
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(String subject){
        return generateToken(new HashMap<>(), subject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final JwtSubjectResponse jwtSubjectResponse = extractSubject(token);
        log.info("username & roles from token = [{}] - [{}]",jwtSubjectResponse.getUsername(),jwtSubjectResponse.getRolesName());
        log.info("username & roles from userdetails = [{}] - [{}]",userDetails.getUsername(), userDetails.getAuthorities());
        return (jwtSubjectResponse.getUsername().equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public Date extractExpiredToken(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
