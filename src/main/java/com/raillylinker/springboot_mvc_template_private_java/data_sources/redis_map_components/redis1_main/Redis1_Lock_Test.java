package com.raillylinker.springboot_mvc_template_private_java.data_sources.redis_map_components.redis1_main;

import com.raillylinker.springboot_mvc_template_private_java.abstract_classes.BasicRedisLock;
import com.raillylinker.springboot_mvc_template_private_java.configurations.redis_configs.Redis1MainConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class Redis1_Lock_Test extends BasicRedisLock {
    public Redis1_Lock_Test(
            // !!!RedisConfig 종류 변경!!!
            @Qualifier(Redis1MainConfig.REDIS_TEMPLATE_NAME)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            RedisTemplate<String, String> redisTemplate
    ) {
        super(redisTemplate, MAP_NAME);
    }

    // <멤버 변수 공간>
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private static final String MAP_NAME = "Redis1_Lock_Test";
}
