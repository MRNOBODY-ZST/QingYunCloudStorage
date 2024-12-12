package com.qingyun.cloudstorage.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JwtUtils {

    // JWT 密钥
//    private static final String JWT_SECRET = IdUtil.fastSimpleUUID();
    private static final String JWT_SECRET = "123";

    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    private static final String TOKEN_PREFIX = "Bearer ";

    public static String encode(Map<String, Object> claims) {
        log.info("Using secret key: {}", JWT_SECRET);
        return JWT.create().withClaim("claims", claims).withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)).sign(Algorithm.HMAC512(JWT_SECRET));
    }

    public static Map<String, Object> decode(String token) {
        // Remove "Bearer " prefix if present
        String actualToken = token;
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            actualToken = token.substring(TOKEN_PREFIX.length());
        }
        assert actualToken != null;
        return JWT.require(Algorithm.HMAC512(JWT_SECRET)).build().verify(actualToken).getClaim("claims").asMap();
    }
}