package com.raillylinker.springboot_mvc_template_private_java.util_components;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ActuatorWhiteList {
    // (Actuator 화이트 리스트 반환)
    @NotNull
    List<ActuatorAllowIpVo> getActuatorWhiteList();

    // (Actuator 화이트 리스트 설정)
    void setActuatorWhiteList(@NotNull List<ActuatorAllowIpVo> actuatorAllowIpVoList);

    record ActuatorAllowIpVo(@NotNull String ip, @NotNull String desc) {
    }
}
