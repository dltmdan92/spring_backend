package com.seungmoo.backend.api.presentation.mvc;

import com.seungmoo.backend.api.exceptions.TokenIsEmptyException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(TokenIsEmptyException.class)
    public String handleTokenIsEmptyException() {
        return "인증 토큰을 입력하지 않으셨습니다.";
    }

    @ExceptionHandler({io.jsonwebtoken.security.SignatureException.class, MalformedJwtException.class})
    public String handleTokenInvalidException() {
        return "잘못된 JWT 서명입니다.";
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public String handleExpiredJwtException() {
        return "만료된 JWT 토큰입니다.";
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public String handleUnsupportedJwtException() {
        return "지원되지 않는 JWT 토큰입니다.";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException() {
        return "JWT 토큰이 잘못되었습니다.";
    }

}
