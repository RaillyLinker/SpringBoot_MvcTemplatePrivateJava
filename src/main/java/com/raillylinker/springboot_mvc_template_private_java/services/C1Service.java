package com.raillylinker.springboot_mvc_template_private_java.services;

import com.raillylinker.springboot_mvc_template_private_java.controllers.C1Controller;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.servlet.ModelAndView;

public interface C1Service {
    // (루트 홈페이지 반환 함수)
    ModelAndView api1GetRoot(@Valid @NotNull HttpServletResponse httpServletResponse);

    ////
    // (Project Runtime Config Redis Key-Value 모두 조회)
    C1Controller.Api2SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo api2SelectAllProjectRuntimeConfigsRedisKeyValue(@Valid @NotNull HttpServletResponse httpServletResponse);

    ////
    // (Redis Project Runtime Config actuatorAllowIpList 입력)
    void api3InsertProjectRuntimeConfigActuatorAllowIpList(@Valid @NotNull HttpServletResponse httpServletResponse, @Valid @NotNull C1Controller.Api3InsertProjectRuntimeConfigActuatorAllowIpListInputVo inputVo);

    ////
    // (Redis Project Runtime Config loggingDenyIpList 입력)
    void api4InsertProjectRuntimeConfigLoggingDenyIpList(@Valid @NotNull HttpServletResponse httpServletResponse, @Valid @NotNull C1Controller.Api4InsertProjectRuntimeConfigLoggingDenyIpListInputVo inputVo);
}