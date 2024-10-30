package com.raillylinker.springboot_mvc_template_private_java.util_components;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ActuatorWhiteList {
    // (Actuator 화이트 리스트 반환)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    List<ActuatorAllowIpVo> getActuatorWhiteList();

    // (Actuator 화이트 리스트 설정)
    void setActuatorWhiteList(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<ActuatorAllowIpVo> actuatorAllowIpVoList
    );

    record ActuatorAllowIpVo(@Valid @NotNull @org.jetbrains.annotations.NotNull
                             String ip,
                             @Valid @NotNull @org.jetbrains.annotations.NotNull
                             String desc
    ) {
    }
}
