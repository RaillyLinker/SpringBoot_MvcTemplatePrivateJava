package com.raillylinker.springboot_mvc_template_private_java.util_components.impls;

import com.raillylinker.springboot_mvc_template_private_java.abstract_classes.BasicRedisMap;
import com.raillylinker.springboot_mvc_template_private_java.data_sources.redis_map_components.redis1_main.Redis1_Map_RuntimeConfigIpList;
import com.raillylinker.springboot_mvc_template_private_java.util_components.ActuatorWhiteList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActuatorWhiteListImpl implements ActuatorWhiteList {
    public ActuatorWhiteListImpl(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Redis1_Map_RuntimeConfigIpList redis1RuntimeConfigIpList
    ) {
        this.redis1RuntimeConfigIpList = redis1RuntimeConfigIpList;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Redis1_Map_RuntimeConfigIpList redis1RuntimeConfigIpList;

    @Override
    public @Valid @NotNull @org.jetbrains.annotations.NotNull List<ActuatorWhiteList.ActuatorAllowIpVo> getActuatorWhiteList() {
        BasicRedisMap.RedisMapDataVo<Redis1_Map_RuntimeConfigIpList.ValueVo> keyValue = redis1RuntimeConfigIpList.findKeyValue(Redis1_Map_RuntimeConfigIpList.KeyEnum.ACTUATOR_ALLOW_IP_LIST.name());

        @Valid @NotNull @org.jetbrains.annotations.NotNull
        List<ActuatorWhiteList.ActuatorAllowIpVo> actuatorAllowIpList = new ArrayList<>();
        if (keyValue != null) {
            for (Redis1_Map_RuntimeConfigIpList.ValueVo.IpDescVo vl : keyValue.value().ipInfoList) {
                actuatorAllowIpList.add(new ActuatorWhiteList.ActuatorAllowIpVo(vl.ip(), vl.desc()));
            }
        }

        return actuatorAllowIpList;
    }

    @Override
    public void setActuatorWhiteList(@Valid @NotNull @org.jetbrains.annotations.NotNull List<ActuatorWhiteList.ActuatorAllowIpVo> actuatorAllowIpVoList) {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        List<Redis1_Map_RuntimeConfigIpList.ValueVo.IpDescVo> ipDescVoList = new ArrayList<>();

        for (@Valid @NotNull @org.jetbrains.annotations.NotNull ActuatorAllowIpVo ipDescInfo : actuatorAllowIpVoList) {
            ipDescVoList.add(new Redis1_Map_RuntimeConfigIpList.ValueVo.IpDescVo(ipDescInfo.ip(), ipDescInfo.desc()));
        }

        redis1RuntimeConfigIpList.saveKeyValue(
                Redis1_Map_RuntimeConfigIpList.KeyEnum.ACTUATOR_ALLOW_IP_LIST.name(),
                new Redis1_Map_RuntimeConfigIpList.ValueVo(ipDescVoList),
                null
        );
    }
}
