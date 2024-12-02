package com.qingyun.cloudstorage.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JwtUtils {

    // JWT 密钥
    private static final String JWT_SECRET = IdUtil.fastSimpleUUID();

    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    /**
     * 生成 JWT Token
     *
     * @param userId   用户ID
     * @param username 用户名
     * @return JWT Token字符串
     */
    public String generateToken(String userId, String username) {
        Map<String, Object> payload = new HashMap<>();

        // 签发时间
        payload.put(JWTPayload.ISSUED_AT, DateUtil.current());
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, DateUtil.current() + EXPIRE_TIME);
        // JWT ID
        payload.put(JWTPayload.JWT_ID, UUID.randomUUID().toString());
        // 用户信息
        payload.put("userId", userId);
        payload.put("username", username);

        // 创建 JWT

        return JWT.create().addPayloads(payload).setKey(JWT_SECRET.getBytes()).sign();
    }

    /**
     * 验证 Token
     *
     * @param token JWT Token字符串
     * @return 是否有效
     */
    public boolean verifyToken(String token) {
        try {
            JWT jwt = JWTUtil.parseToken(token);
            // 验证签名
            boolean verify = jwt.setKey(JWT_SECRET.getBytes()).verify();
            // 验证过期时间
            boolean notExpired = !jwt.getPayload().getClaimsJson().containsKey(JWTPayload.EXPIRES_AT) || DateUtil.current() <= (Long) jwt.getPayload().getClaim(JWTPayload.EXPIRES_AT);

            return verify && notExpired;
        } catch (Exception e) {
            log.error("JWT Token验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 从 Token 中获取用户信息
     *
     * @param token JWT Token字符串
     * @return 用户信息Map
     */
    public Map<String, Object> getUserInfo(String token) {
        try {
            JWT jwt = JWTUtil.parseToken(token);
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userId", jwt.getPayload().getClaim("userId"));
            userInfo.put("username", jwt.getPayload().getClaim("username"));
            return userInfo;
        } catch (Exception e) {
            log.error("获取用户信息失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 刷新 Token
     *
     * @param token 原Token
     * @return 新Token
     */
    public String refreshToken(String token) {
        try {
            JWT jwt = JWTUtil.parseToken(token);
            Map<String, Object> userInfo = getUserInfo(token);
            return generateToken((String) userInfo.get("userId"), (String) userInfo.get("username"));
        } catch (Exception e) {
            log.error("刷新Token失败: {}", e.getMessage());
            return null;
        }
    }
}