package com.raillylinker.springboot_mvc_template_private_java.controllers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.raillylinker.springboot_mvc_template_private_java.services.C1Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Tag(name = "root APIs", description = "C1 : Root 경로에 대한 API 컨트롤러")
@Controller
public class C1Controller {
    public C1Controller(@NotNull C1Service service) {
        this.service = service;
    }

    private final @NotNull C1Service service;


    // ---------------------------------------------------------------------------------------------
    // <매핑 함수 공간>
    @Operation(
            summary = "N1 : 홈페이지",
            description = "루트 홈페이지를 반환합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @GetMapping(
            path = {"", "/"},
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public ModelAndView api1GetRoot(
            @Parameter(hidden = true) @NotNull HttpServletResponse httpServletResponse
    ) {
        return service.api1GetRoot(httpServletResponse);
    }

    ////
    @Operation(
            summary = "N2 : Project Runtime Config Redis Key-Value 모두 조회 테스트",
            description = "Project 의 런타임 설정 저장용 Redis Table 에 저장된 모든 Key-Value 를 조회합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @GetMapping(
            path = "/project-runtime-configs",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Api2SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo api2SelectAllProjectRuntimeConfigsRedisKeyValue(
            @Parameter(hidden = true) @NotNull HttpServletResponse httpServletResponse
    ) {
        return service.api2SelectAllProjectRuntimeConfigsRedisKeyValue(httpServletResponse);
    }

    public static class Api2SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo {
        public Api2SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo(@NotNull List<KeyValueVo> keyValueList) {
            this.keyValueList = keyValueList;
        }

        @Schema(description = "Key-Value 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonProperty("keyValueList")
        public @NotNull List<KeyValueVo> keyValueList;

        @Schema(description = "Key-Value 객체", requiredMode = Schema.RequiredMode.REQUIRED)
        public static class KeyValueVo {
            public KeyValueVo(@NotNull String key, @NotNull List<IpDescVo> ipInfoList) {
                this.key = key;
                this.ipInfoList = ipInfoList;
            }

            @Schema(description = "Key", example = "testing", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("key")
            public @NotNull String key;

            @Schema(description = "설정 IP 정보 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("ipInfoList")
            public @NotNull List<IpDescVo> ipInfoList;

            public static class IpDescVo {
                public IpDescVo(@NotNull String ip, @NotNull String desc) {
                    this.ip = ip;
                    this.desc = desc;
                }

                @Schema(description = "설정 ip", example = "testing", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("ip")
                public @NotNull String ip;
                @Schema(description = "ip 설명", example = "testing", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("desc")
                public @NotNull String desc;
            }
        }
    }

    ////
    @Operation(
            summary = "N3 : Redis Project Runtime Config actuatorAllowIpList 입력 테스트",
            description = "Redis 의 Project Runtime Config actuatorAllowIpList 를 입력합니다.\n\n" +
                    "이 정보는 본 프로젝트 actuator 접근 허용 IP 를 뜻합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @PostMapping(
            path = "/project-runtime-config-actuator-allow-ip-list",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void api3InsertProjectRuntimeConfigActuatorAllowIpList(
            @Parameter(hidden = true) @NotNull HttpServletResponse httpServletResponse,
            @RequestBody @NotNull Api3InsertProjectRuntimeConfigActuatorAllowIpListInputVo inputVo
    ) {
        service.api3InsertProjectRuntimeConfigActuatorAllowIpList(httpServletResponse, inputVo);
    }

    public static class Api3InsertProjectRuntimeConfigActuatorAllowIpListInputVo {
        @JsonCreator
        public Api3InsertProjectRuntimeConfigActuatorAllowIpListInputVo(@NotNull List<IpDescVo> ipInfoList) {
            this.ipInfoList = ipInfoList;
        }

        @Schema(
                description = "설정 IP 정보 리스트",
                example = "[{\"ip\":\"127.0.0.1\",\"desc\":\"localHost\"}]",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty("ipInfoList")
        public @NotNull List<IpDescVo> ipInfoList;

        public static class IpDescVo {
            @JsonCreator
            public IpDescVo(@NotNull String ip, @NotNull String desc) {
                this.ip = ip;
                this.desc = desc;
            }

            @Schema(description = "설정 ip", example = "testing", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("ip")
            public @NotNull String ip;
            @Schema(description = "ip 설명", example = "testing", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("desc")
            public @NotNull String desc;
        }
    }

    ////
    @Operation(
            summary = "N4 : Redis Project Runtime Config loggingDenyIpList 입력 테스트",
            description = "Redis 의 Project Runtime Config loggingDenyIpList 를 입력합니다.\n\n" +
                    "이 정보는 본 프로젝트에서 로깅하지 않을 IP 를 뜻합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @PostMapping(
            path = "/project-runtime-config-logging-deny-ip-list",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void api4InsertProjectRuntimeConfigLoggingDenyIpList(
            @Parameter(hidden = true) @NotNull HttpServletResponse httpServletResponse,
            @RequestBody @NotNull Api4InsertProjectRuntimeConfigLoggingDenyIpListInputVo inputVo
    ) {
        service.api4InsertProjectRuntimeConfigLoggingDenyIpList(httpServletResponse, inputVo);
    }

    public static class Api4InsertProjectRuntimeConfigLoggingDenyIpListInputVo {
        @JsonCreator
        public Api4InsertProjectRuntimeConfigLoggingDenyIpListInputVo(@NotNull List<IpDescVo> ipInfoList) {
            this.ipInfoList = ipInfoList;
        }

        @Schema(
                description = "설정 IP 정보 리스트",
                example = "[{\"ip\":\"127.0.0.1\",\"desc\":\"localHost\"}]",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty("ipInfoList")
        public @NotNull List<IpDescVo> ipInfoList;

        public static class IpDescVo {
            @JsonCreator
            public IpDescVo(@NotNull String ip, @NotNull String desc) {
                this.ip = ip;
                this.desc = desc;
            }

            @Schema(description = "설정 ip", example = "testing", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("ip")
            public @NotNull String ip;
            @Schema(description = "ip 설명", example = "testing", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("desc")
            public @NotNull String desc;
        }
    }
}
