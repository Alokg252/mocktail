package com.flarecon.mocktail.security;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimiter {

    private static final long WINDOW_MS = 60L * 60L * 1000L;

    private final ConcurrentMap<String, Window> windows = new ConcurrentHashMap<>();

    public Result tryAcquire(String bucketKey, int limit) {
        long currentWindow = System.currentTimeMillis() / WINDOW_MS;
        Window w = windows.compute(bucketKey, (k, existing) ->
                (existing == null || existing.window != currentWindow)
                        ? new Window(currentWindow, new AtomicInteger(0))
                        : existing
        );
        int used = w.count.incrementAndGet();
        long retryAfterSec = ((currentWindow + 1) * WINDOW_MS - System.currentTimeMillis()) / 1000L;
        return new Result(used <= limit, limit, Math.max(0, limit - used), retryAfterSec);
    }

    public record Result(boolean allowed, int limit, int remaining, long retryAfterSeconds) {}

    private record Window(long window, AtomicInteger count) {}
}
