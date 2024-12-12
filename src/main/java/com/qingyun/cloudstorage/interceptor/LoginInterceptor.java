package com.qingyun.cloudstorage.interceptor;

import com.qingyun.cloudstorage.utils.JwtUtils;
import com.qingyun.cloudstorage.utils.ThreadUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");

        if (authorization != null) {
            try {
                // Decode authorization token
                // Verify the token and get the claims
                Map<String, Object> claims = JwtUtils.decode(authorization);

                // !IMPORTANT
                // claims reuse for optimization
                ThreadUtils.set(claims);

                return true;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //remove data in thread local after request completed
        ThreadUtils.remove();
    }
}