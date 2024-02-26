package com.backend.backendleeyejin.backend.provider;

import com.backend.backendleeyejin.backend.controller.dto.ResponseLoginDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    public Key getSingKey(String secretKey){
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String userId, String secretKey, int tokenValidTime){
        ResponseLoginDTO responseLoginDTO = new ResponseLoginDTO();

        Claims claims = Jwts.claims().setSubject("accessToken");
        claims.put("userId",userId);

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(getSingKey(secretKey), SignatureAlgorithm.HS256)
                .compact();

        log.info("발급된 Access Token : {}", token);
        responseLoginDTO.setAccessToken(token);
        return responseLoginDTO.getAccessToken();
    }

    public String getUserId(String accessToken, String secretKey){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSingKey(secretKey))
                    .build().parseClaimsJws(accessToken).getBody().get("userId").toString();
        }catch (SignatureException e){
            return "";
        }

    }

    public boolean isExpired(String accessToken, String secretKey){
        try {
            return Jwts.parserBuilder().setSigningKey(getSingKey(secretKey)).build().parseClaimsJws(accessToken)
                    .getBody().getExpiration().before(new Date());
        } catch (SignatureException | ExpiredJwtException e) {
            return true;
        }
    }

}
