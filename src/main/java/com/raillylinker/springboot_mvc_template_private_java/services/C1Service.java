package com.raillylinker.springboot_mvc_template_private_java.services;

import com.raillylinker.springboot_mvc_template_private_java.controllers.C1Controller;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;

public interface C1Service {
    // (루트 홈페이지 반환 함수)
    ModelAndView api1GetRoot(@NotNull HttpServletResponse httpServletResponse);

    ////
    // (Project Runtime Config Redis Key-Value 모두 조회)
    C1Controller.Api2SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo api2SelectAllProjectRuntimeConfigsRedisKeyValue(@NotNull HttpServletResponse httpServletResponse);

    ////
    // (Redis Project Runtime Config actuatorAllowIpList 입력)
    void api3InsertProjectRuntimeConfigActuatorAllowIpList(@NotNull HttpServletResponse httpServletResponse, @NotNull C1Controller.Api3InsertProjectRuntimeConfigActuatorAllowIpListInputVo inputVo);

    ////
    // (Redis Project Runtime Config loggingDenyIpList 입력)
    void api4InsertProjectRuntimeConfigLoggingDenyIpList(@NotNull HttpServletResponse httpServletResponse, @NotNull C1Controller.Api4InsertProjectRuntimeConfigLoggingDenyIpListInputVo inputVo);
}