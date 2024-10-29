package com.raillylinker.springboot_mvc_template_private_java.abstract_classes;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.UUID;

// RedisLock 의 Abstract 클래스
public abstract class BasicRedisLock {
    public BasicRedisLock(@NotNull RedisTemplate<String, String> redisTemplateObj, @NotNull String mapName) {
        this.redisTemplateObj = redisTemplateObj;
        this.mapName = mapName;
    }

    private final @NotNull RedisTemplate<String, String> redisTemplateObj;
    private final @NotNull String mapName;

    // 락 획득 메소드 - Lua 스크립트 적용
    public String tryLock(@NotNull Long expireTimeMs) {
        @NotNull String uuid = UUID.randomUUID().toString();

        Long scriptResult;
        if (expireTimeMs < 0) {
            // 만료시간 무한
            scriptResult = redisTemplateObj.execute(
                    RedisScript.of(
                            "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then " +
                                    "return 1 " +
                                    "else " +
                                    "return 0 " +
                                    "end",
                            Long.class
                    ),
                    Collections.singletonList(mapName),
                    uuid
            );
        } else {
            // 만료시간 유한
            scriptResult = redisTemplateObj.execute(
                    RedisScript.of(
                            "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then " +
                                    "redis.call('pexpire', KEYS[1], ARGV[2]) " +
                                    "return 1 " +
                                    "else " +
                                    "return 0 " +
                                    "end",
                            Long.class
                    ),
                    Collections.singletonList(mapName),
                    uuid,
                    String.valueOf(expireTimeMs)
            );
        }

        return (scriptResult != null && scriptResult == 1L) ? uuid : null;
    }

    // 락 해제 메소드 - Lua 스크립트 적용
    public void unlock(@NotNull String uuid) {
        redisTemplateObj.execute(
                RedisScript.of(
                        "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                                "return redis.call('del', KEYS[1]) " +
                                "else " +
                                "return 0 " +
                                "end",
                        Long.class
                ),
                Collections.singletonList(mapName),
                uuid
        );
    }

    // 락 강제 삭제
    public void deleteLock() {
        redisTemplateObj.delete(mapName);
    }
}