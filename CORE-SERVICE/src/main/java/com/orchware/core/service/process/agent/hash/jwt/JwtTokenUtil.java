package com.orchware.core.service.process.agent.hash.jwt;


import com.orchware.core.model.InterconnectSecKey;
import com.orchware.core.service.process.agent.hash.dto.InterconnectSecKeyDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtTokenUtil implements Serializable {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final long serialVersionUID = -2550185165626007488L;

    public String getUniqueKeyFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getTenantClientIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(JwtConstants.SIGNING_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /*public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", userDetails);
        return doGenerateToken(claims, userDetails.getUsername());
    }*/

    public String generateToken(InterconnectSecKey interconnectSecKey) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("secKey", InterconnectSecKeyDTO.builder()
                .uniqueKey(interconnectSecKey.getUniqueKey())
                .keyhash(interconnectSecKey.getKeyhash())
                .timeStamp(interconnectSecKey.getTimeStamp())
                .expiryDate(interconnectSecKey.getExpiryDate())
                .build());
        return doGenerateToken(claims, interconnectSecKey.getUniqueKey());
    }

    private String doGenerateToken(Map<String, Object> claims, String uniqueKey) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(uniqueKey) // Compared for validation
//                .setAudience(password) // Compared for validation
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(((InterconnectSecKeyDTO) claims.get("secKey")).getExpiryDate())
                .signWith(SignatureAlgorithm.HS512, JwtConstants.SIGNING_KEY).compact();
    }

    public Boolean validateToken(String token, InterconnectSecKey interconnectSecKey) {
        LOGGER.info("Validation for " + interconnectSecKey.getUniqueKey());
        final String uniqueKey = getUniqueKeyFromToken(token);
        return (uniqueKey.equals(interconnectSecKey.getUniqueKey()) && !isTokenExpired(token));
    }

    public static void main(String[] args) {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String token = jwtTokenUtil.generateToken(InterconnectSecKey.builder()
                        .uniqueKey("abc123")
                        .keyhash("1234567")
                        .timeStamp(System.currentTimeMillis())
                        .expiryDate(new Date())
                .build());
        System.out.println(token);
    }
}
