package com.raillylinker.springboot_mvc_template_private_java.data_sources.redis_map_components.redis1_main;

import com.raillylinker.springboot_mvc_template_private_java.abstract_classes.BasicRedisMap;
import com.raillylinker.springboot_mvc_template_private_java.configurations.redis_configs.Redis1MainConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

// [RedisMap 컴포넌트]
// 프로젝트 내부에서 사용할 IP 관련 설정 저장 타입입니다.
@Component
public class Redis1_Map_RuntimeConfigIpList extends BasicRedisMap<Redis1_Map_RuntimeConfigIpList.ValueVo> {
    public Redis1_Map_RuntimeConfigIpList(
            // !!!RedisConfig 종류 변경!!!
            @Qualifier(Redis1MainConfig.REDIS_TEMPLATE_NAME) @Valid @NotNull RedisTemplate<String, String> redisTemplate
    ) {
        super(redisTemplate, MAP_NAME, ValueVo.class);
    }

    // <멤버 변수 공간>
    // !!!중복되지 않도록, 본 클래스명을 MAP_NAME 으로 설정하기!!!
    private static final @Valid @NotNull String MAP_NAME = "Redis1_Map_RuntimeConfigIpList";

    // !!!본 RedisMAP 의 Value 클래스 설정!!!
    public static class ValueVo {
        public ValueVo(@Valid @NotNull List<IpDescVo> ipInfoList) {
            this.ipInfoList = ipInfoList;
        }

        public @Valid @NotNull List<IpDescVo> ipInfoList;

        public record IpDescVo(
                @Valid @NotNull String ip,
                @Valid @NotNull String desc
        ) {
        }
    }

    public enum KeyEnum {
        // Actuator 정보 접근 허용 IP 리스트
        ACTUATOR_ALLOW_IP_LIST,

        // Logging Filter 의 로깅 대상에서 제외할 IP 리스트
        LOGGING_DENY_IP_LIST
    }
}
