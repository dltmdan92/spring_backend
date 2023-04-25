package com.seungmoo.backend.api.service.user.providers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seungmoo.backend.api.service.user.factories.UserSessionFactory;
import com.seungmoo.backend.api.service.user.models.SessionUser;
import com.seungmoo.backend.user.dtos.UserDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionProvider implements InitializingBean {
    private final UserSessionFactory userSessionFactory;

    @Value("${token.validity.lifetime-seconds}")
    private long tokenValidityLifetimeSeconds;

    @Value("${token.secret}")
    private String secret;

    private SecretKey key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public SessionUser getSessionUser(String token) throws JsonProcessingException {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return userSessionFactory.toSessionUser(claims);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public String createSessionToken(UserDTO userDTO) throws JsonProcessingException {

        return Jwts.builder()
                .setSubject(userDTO.getUsername())
                .addClaims(userSessionFactory.toClaims(userDTO))
                .setExpiration(Date.from(LocalDateTime.now().plusSeconds(tokenValidityLifetimeSeconds).atZone(ZoneOffset.systemDefault()).toInstant()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

}
