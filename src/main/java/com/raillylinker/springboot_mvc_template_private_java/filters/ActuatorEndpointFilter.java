package com.raillylinker.springboot_mvc_template_private_java.filters;

import com.raillylinker.springboot_mvc_template_private_java.abstract_classes.BasicRedisMap;
import com.raillylinker.springboot_mvc_template_private_java.data_sources.redis_map_components.redis1_main.Redis1_Map_RuntimeConfigIpList;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

// [민감한 정보를 지닌 actuator 접근 제한 필터]
// /actuator 로 시작되는 경로에 대한 모든 요청은,
// ApplicationRuntimeConfigs.runtimeConfigData.actuatorAllowIpList
// 위 변수에 담겨있는 IP 만을 허용하고, 나머지 접근은 404 를 반환하도록 처리하였습니다.
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ActuatorEndpointFilter implements Filter {
    public ActuatorEndpointFilter(
            @NotNull Redis1_Map_RuntimeConfigIpList redis1RuntimeConfigIpList
    ) {
        this.redis1RuntimeConfigIpList = redis1RuntimeConfigIpList;
    }

    // <멤버 변수 공간>
    private final @NotNull org.slf4j.Logger classLogger = LoggerFactory.getLogger(this.getClass());

    // (Redis Repository)
    private final @NotNull Redis1_Map_RuntimeConfigIpList redis1RuntimeConfigIpList;

    @Override
    public void doFilter(@NotNull ServletRequest request, @NotNull ServletResponse response, @NotNull FilterChain chain) throws IOException, ServletException {
        @NotNull HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        @NotNull HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // 요청자 Ip (ex : 127.0.0.1)
        @NotNull String clientAddressIp = httpServletRequest.getRemoteAddr();


        BasicRedisMap.RedisMapDataVo<Redis1_Map_RuntimeConfigIpList.ValueVo> actuatorAllowIpInfo = null;
        try {
            actuatorAllowIpInfo = redis1RuntimeConfigIpList.findKeyValue(Redis1_Map_RuntimeConfigIpList.KeyEnum.ACTUATOR_ALLOW_IP_LIST.name());
        } catch (Exception e) {
            classLogger.error("An error occurred: ", e); // 에러 레벨로 로깅
        }

        boolean actuatorAllow = false;
        if (actuatorAllowIpInfo != null) {
            for (@NotNull Redis1_Map_RuntimeConfigIpList.ValueVo.IpDescVo actuatorAllowIp : actuatorAllowIpInfo.value().ipInfoList) {
                if (clientAddressIp.equals(actuatorAllowIp.ip())) {
                    actuatorAllow = true;
                    break;
                }
            }
        }

        // 리퀘스트 URI (ex : /sample/test) 가 /actuator 로 시작되는지를 확인 후 블록
        if (httpServletRequest.getRequestURI().startsWith("/actuator") && !actuatorAllow) {
            // status 404 반환 및 무동작
            httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }

        chain.doFilter(request, response);
    }
}