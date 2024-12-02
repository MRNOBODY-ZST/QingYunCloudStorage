package com.qingyun.cloudstorage.utils;

import io.netty.util.concurrent.FastThreadLocal;

import java.util.Map;

public class ThreadUtils {
    // Define the type parameter for FastThreadLocal to store Map<String, Object>
    private static final FastThreadLocal<Map<String, Object>> THREAD_LOCAL =
            new FastThreadLocal<>();

    /**
     * Retrieves the thread-local claims map.
     *
     * @return Map containing user claims
     */
    public static Map<String, Object> get() {
        return THREAD_LOCAL.get();
    }

    /**
     * Sets the thread-local claims map.
     *
     * @param claims Map containing user claims
     */
    public static void set(Map<String, Object> claims) {
        THREAD_LOCAL.set(claims);
    }

    /**
     * Removes the thread-local claims map.
     * Should be called when the thread is done processing to prevent memory leaks.
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }

    /**
     * Convenience method to get the user ID from the claims map.
     *
     * @return The user ID string, or null if not present
     */
    public static String getUserId() {
        Map<String, Object> claims = get();
        return claims != null ? (String) claims.get("id") : null;
    }
}
