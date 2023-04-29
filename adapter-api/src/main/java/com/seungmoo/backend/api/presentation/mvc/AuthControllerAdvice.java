package com.seungmoo.backend.api.presentation.mvc;

import com.seungmoo.backend.api.service.user.exceptions.TokenIsEmptyException;
import com.seungmoo.backend.api.service.common.protocols.response.Resource;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(TokenIsEmptyException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Resource<String> handleTokenIsEmptyException() {
        return Resource.<String>builder()
                .errorMessage("인증 토큰을 입력하지 않으셨습니다.")
                .errorType("token_is_empty_exception")
                .build();
    }

    @ExceptionHandler({io.jsonwebtoken.security.SignatureException.class, MalformedJwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Resource<String> handleTokenInvalidException(Exception e) {
        String errorType = "";

        if (e instanceof io.jsonwebtoken.security.SignatureException) {
            errorType = "jwt_signature_exception";
        }
        if (e instanceof MalformedJwtException) {
            errorType = "malformed_jwt_exception";
        }

        return Resource.<String>builder()
                .errorMessage("잘못된 JWT 서명입니다.")
                .errorType(errorType)
                .build();
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Resource<String> handleExpiredJwtException() {
        return Resource.<String>builder()
                .errorMessage("만료된 JWT 토큰입니다.")
                .errorType("expired_jwt_exception")
                .build();
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Resource<String> handleUnsupportedJwtException() {
        return Resource.<String>builder()
                .errorMessage("지원되지 않는 JWT 토큰입니다.")
                .errorType("unsupported_jwt_exception")
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Resource<String> handleIllegalArgumentException() {
        return Resource.<String>builder()
                .errorMessage("JWT 토큰이 잘못되었습니다.")
                .errorType("illegal_argument_exception")
                .build();
    }

}
