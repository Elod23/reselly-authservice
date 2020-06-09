package com.reselly.authorizationservice.service;

import com.reselly.authorizationservice.entity.Credentials;
import com.reselly.authorizationservice.repository.CredentialRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtIssuerService implements Serializable {

    @Value("${jwt.JWT_TOKEN_VALIDITY}")
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    private CredentialService credentialService;

    @Autowired
    public JwtIssuerService(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    public Boolean validateToken(String token){
        final String email = extractUsernameFromToken(token);
        Credentials credentialsFromDb = credentialService.getCredentialsAfterEmail(email);
        if(credentialsFromDb != null){
            return (email.equals(credentialsFromDb.getEmail()) && !isTokenExpired(token));
        } else {
            return false;
        }
    }

    public String generateToken(Credentials credentials){
        Map<String, Object> claims = new HashMap<>();
        if (credentialService.getCredentialsAfterEmail(credentials.getEmail()) != null) {
            return doGenerateToken(claims, credentials.getEmail());
        } else {
            Credentials newUserCreds = credentialService.persistNewCredentials(credentials);
            if (newUserCreds != null) {
                return doGenerateToken(claims, newUserCreds.getEmail());
            }
        }
        return null;
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private Boolean isTokenExpired(String token) {
        final LocalDateTime expiration = getExpirationDateFromToken(token);
        return expiration.isBefore(LocalDateTime.now());
    }

    private LocalDateTime getExpirationDateFromToken(String token) {
        Date expDate = getClaimFromToken(token, Claims::getExpiration);
        return expDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private String extractUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

}

