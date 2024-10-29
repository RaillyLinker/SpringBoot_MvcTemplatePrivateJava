package com.raillylinker.springboot_mvc_template_private_java.data_sources.redis_map_components.redis1_main;

import com.raillylinker.springboot_mvc_template_private_java.abstract_classes.BasicRedisLock;
import com.raillylinker.springboot_mvc_template_private_java.configurations.redis_configs.Redis1MainConfig;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class Redis1_Lock_Test extends BasicRedisLock {
    public Redis1_Lock_Test(
            // !!!RedisConfig 종류 변경!!!
            @Qualifier(Redis1MainConfig.REDIS_TEMPLATE_NAME) @NotNull RedisTemplate<String, String> redisTemplate
    ) {
        super(redisTemplate, MAP_NAME);
    }

    // <멤버 변수 공간>
    private static final @NotNull String MAP_NAME = "Redis1_Lock_Test";
}
