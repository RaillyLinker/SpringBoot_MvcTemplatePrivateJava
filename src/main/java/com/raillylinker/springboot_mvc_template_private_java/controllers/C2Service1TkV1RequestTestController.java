package com.raillylinker.springboot_mvc_template_private_java.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raillylinker.springboot_mvc_template_private_java.services.C2Service1TkV1RequestTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Tag(name = "/service1/tk/v1/request-test APIs", description = "C2 : 요청 / 응답에 대한 테스트 API 컨트롤러")
@Controller
@RequestMapping("/service1/tk/v1/request-test")
public class C2Service1TkV1RequestTestController {
    public C2Service1TkV1RequestTestController(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            C2Service1TkV1RequestTestService service
    ) {
        this.service = service;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final C2Service1TkV1RequestTestService service;


    // ---------------------------------------------------------------------------------------------
    // <매핑 함수 공간>
    @Operation(
            summary = "N1 : 기본 요청 테스트 API",
            description = "이 API 를 요청하면 현재 실행중인 프로필 이름을 반환합니다.\n\n"
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
            path = "",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ResponseBody
    public String api1BasicRequestTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.api1BasicRequestTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "N2 : 요청 Redirect 테스트 API",
            description = "이 API 를 요청하면 /service1/tk/v1/request-test 로 Redirect 됩니다.\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @GetMapping(
            path = "/redirect-to-blank",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    public ModelAndView api2RedirectTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.api2RedirectTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "N3 : 요청 Forward 테스트 API",
            description = "이 API 를 요청하면 /service1/tk/v1/request-test 로 Forward 됩니다.\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @GetMapping(
            path = "/forward-to-blank",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    public ModelAndView api3ForwardTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.api3ForwardTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "N4 : Get 요청 테스트 (Query Parameter)",
            description = "Query Parameter 를 받는 Get 메소드 요청 테스트\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @GetMapping(
            path = "/get-request",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Api4GetRequestTestOutputVo api4GetRequestTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "queryParamString", description = "String Query 파라미터", example = "testString")
            @RequestParam("queryParamString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String queryParamString,
            @Parameter(name = "queryParamStringNullable", description = "String Query 파라미터 Nullable", example = "testString")
            @RequestParam(value = "queryParamStringNullable", required = false)
            String queryParamStringNullable,
            @Parameter(name = "queryParamInt", description = "Int Query 파라미터", example = "1")
            @RequestParam("queryParamInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer queryParamInt,
            @Parameter(name = "queryParamIntNullable", description = "Int Query 파라미터 Nullable", example = "1")
            @RequestParam(value = "queryParamIntNullable", required = false)
            Integer queryParamIntNullable,
            @Parameter(name = "queryParamDouble", description = "Double Query 파라미터", example = "1.1")
            @RequestParam("queryParamDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double queryParamDouble,
            @Parameter(name = "queryParamDoubleNullable", description = "Double Query 파라미터 Nullable", example = "1.1")
            @RequestParam(value = "queryParamDoubleNullable", required = false)
            Double queryParamDoubleNullable,
            @Parameter(name = "queryParamBoolean", description = "Boolean Query 파라미터", example = "true")
            @RequestParam("queryParamBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean queryParamBoolean,
            @Parameter(name = "queryParamBooleanNullable", description = "Boolean Query 파라미터 Nullable", example = "true")
            @RequestParam(value = "queryParamBooleanNullable", required = false)
            Boolean queryParamBooleanNullable,
            @Parameter(name = "queryParamStringList", description = "StringList Query 파라미터", example = "[\"testString1\", \"testString2\"]")
            @RequestParam("queryParamStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> queryParamStringList,
            @Parameter(name = "queryParamStringListNullable", description = "StringList Query 파라미터 Nullable", example = "[\"testString1\", \"testString2\"]")
            @RequestParam(value = "queryParamStringListNullable", required = false)
            List<@Valid @NotNull String> queryParamStringListNullable
    ) {
        return service.api4GetRequestTest(
                httpServletResponse,
                queryParamString,
                queryParamStringNullable,
                queryParamInt,
                queryParamIntNullable,
                queryParamDouble,
                queryParamDoubleNullable,
                queryParamBoolean,
                queryParamBooleanNullable,
                queryParamStringList,
                queryParamStringListNullable
        );
    }

    public record Api4GetRequestTestOutputVo(
            @Schema(description = "입력한 String Query 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("queryParamString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String queryParamString,

            @Schema(description = "입력한 String Nullable Query 파라미터", example = "testString")
            @JsonProperty("queryParamStringNullable")
            String queryParamStringNullable,

            @Schema(description = "입력한 Int Query 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("queryParamInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer queryParamInt,

            @Schema(description = "입력한 Int Nullable Query 파라미터", example = "1")
            @JsonProperty("queryParamIntNullable")
            Integer queryParamIntNullable,

            @Schema(description = "입력한 Double Query 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("queryParamDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double queryParamDouble,

            @Schema(description = "입력한 Double Nullable Query 파라미터", example = "1.1")
            @JsonProperty("queryParamDoubleNullable")
            Double queryParamDoubleNullable,

            @Schema(description = "입력한 Boolean Query 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("queryParamBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean queryParamBoolean,

            @Schema(description = "입력한 Boolean Nullable Query 파라미터", example = "true")
            @JsonProperty("queryParamBooleanNullable")
            Boolean queryParamBooleanNullable,

            @Schema(description = "입력한 StringList Query 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("queryParamStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> queryParamStringList,

            @Schema(description = "입력한 StringList Nullable Query 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("queryParamStringListNullable")
            List<String> queryParamStringListNullable
    ) {
    }


    ////
    @Operation(
            summary = "N5 : Get 요청 테스트 (Path Parameter)",
            description = "Path Parameter 를 받는 Get 메소드 요청 테스트\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @GetMapping(
            path = "/get-request/{pathParamInt}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Api5GetRequestTestWithPathParamOutputVo api5GetRequestTestWithPathParam(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "pathParamInt", description = "Int Path 파라미터", example = "1")
            @PathVariable("pathParamInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pathParamInt
    ) {
        return service.api5GetRequestTestWithPathParam(httpServletResponse, pathParamInt);
    }

    public record Api5GetRequestTestWithPathParamOutputVo(
            @Schema(description = "입력한 Int Path 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("pathParamInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pathParamInt
    ) {
    }


    ////
    @Operation(
            summary = "N6 : Post 요청 테스트 (application-json)",
            description = "application-json 형태의 Request Body 를 받는 Post 메소드 요청 테스트\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @PostMapping(
            path = "/post-request-application-json",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Api6PostRequestTestWithApplicationJsonTypeRequestBodyOutputVo api6PostRequestTestWithApplicationJsonTypeRequestBody(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Api6PostRequestTestWithApplicationJsonTypeRequestBodyInputVo inputVo
    ) {
        return service.api6PostRequestTestWithApplicationJsonTypeRequestBody(httpServletResponse, inputVo);
    }

    public record Api6PostRequestTestWithApplicationJsonTypeRequestBodyInputVo(
            @Schema(description = "String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestBodyString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestBodyString,
            @Schema(description = "String Nullable Body 파라미터", example = "testString")
            @JsonProperty("requestBodyStringNullable")
            String requestBodyStringNullable,
            @Schema(description = "Int Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestBodyInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestBodyInt,
            @Schema(description = "Int Nullable Body 파라미터", example = "1")
            @JsonProperty("requestBodyIntNullable")
            Integer requestBodyIntNullable,
            @Schema(description = "Double Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestBodyDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestBodyDouble,
            @Schema(description = "Double Nullable Body 파라미터", example = "1.1")
            @JsonProperty("requestBodyDoubleNullable")
            Double requestBodyDoubleNullable,
            @Schema(description = "Boolean Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestBodyBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestBodyBoolean,
            @Schema(description = "Boolean Nullable Body 파라미터", example = "true")
            @JsonProperty("requestBodyBooleanNullable")
            Boolean requestBodyBooleanNullable,
            @Schema(description = "StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestBodyStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestBodyStringList,
            @Schema(description = "StringList Nullable Body 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestBodyStringListNullable")
            List<@Valid @NotNull String> requestBodyStringListNullable
    ) {
    }

    public record Api6PostRequestTestWithApplicationJsonTypeRequestBodyOutputVo(
            @Schema(description = "입력한 String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestBodyString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestBodyString,
            @Schema(description = "입력한 String Nullable Body 파라미터", example = "testString")
            @JsonProperty("requestBodyStringNullable")
            String requestBodyStringNullable,
            @Schema(description = "입력한 Int Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestBodyInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestBodyInt,
            @Schema(description = "입력한 Int Nullable Body 파라미터", example = "1")
            @JsonProperty("requestBodyIntNullable")
            Integer requestBodyIntNullable,
            @Schema(description = "입력한 Double Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestBodyDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestBodyDouble,
            @Schema(description = "입력한 Double Nullable Body 파라미터", example = "1.1")
            @JsonProperty("requestBodyDoubleNullable")
            Double requestBodyDoubleNullable,
            @Schema(description = "입력한 Boolean Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestBodyBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestBodyBoolean,
            @Schema(description = "입력한 Boolean Nullable Body 파라미터", example = "true")
            @JsonProperty("requestBodyBooleanNullable")
            Boolean requestBodyBooleanNullable,
            @Schema(description = "입력한 StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestBodyStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestBodyStringList,
            @Schema(description = "입력한 StringList Nullable Body 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestBodyStringListNullable")
            List<@Valid @NotNull String> requestBodyStringListNullable
    ) {
    }


    ////
    @Operation(
            summary = "N6.1 : Post 요청 테스트 (application-json, 객체 파라미터 포함)",
            description = "application-json 형태의 Request Body(객체 파라미터 포함) 를 받는 Post 메소드 요청 테스트\n\n"
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
            path = "/post-request-application-json-with-object-param",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2InputVo inputVo) {
        return service.api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2(httpServletResponse, inputVo);
    }

    public record Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2InputVo(
            @Schema(description = "객체 타입 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("objectVo")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ObjectVo objectVo,
            @Schema(description = "객체 타입 리스트 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("objectVoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull ObjectVo> objectVoList
    ) {
        public record ObjectVo(
                @Schema(description = "String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
                @JsonProperty("requestBodyString")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String requestBodyString,
                @Schema(description = "StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
                @JsonProperty("requestBodyStringList")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                List<@Valid @NotNull String> requestBodyStringList,
                @Schema(description = "서브 객체 타입 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("subObjectVo")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                SubObjectVo subObjectVo,
                @Schema(description = "서브 객체 타입 리스트 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("subObjectVoList")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                List<@Valid @NotNull SubObjectVo> subObjectVoList
        ) {
            public record SubObjectVo(
                    @Schema(description = "String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
                    @JsonProperty("requestBodyString")
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String requestBodyString,
                    @Schema(description = "StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
                    @JsonProperty("requestBodyStringList")
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    List<@Valid @NotNull String> requestBodyStringList
            ) {
            }
        }
    }

    public record Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo(
            @Schema(description = "객체 타입 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("objectVo")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ObjectVo objectVo,
            @Schema(description = "객체 타입 리스트 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("objectVoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<ObjectVo> objectVoList
    ) {
        @Schema(description = "객체 타입 파라미터 VO")
        public record ObjectVo(
                @Schema(description = "String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
                @JsonProperty("requestBodyString")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String requestBodyString,
                @Schema(description = "StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
                @JsonProperty("requestBodyStringList")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                List<@Valid @NotNull String> requestBodyStringList,
                @Schema(description = "서브 객체 타입 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("subObjectVo")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                SubObjectVo subObjectVo,
                @Schema(description = "서브 객체 타입 리스트 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("subObjectVoList")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                List<@Valid @NotNull SubObjectVo> subObjectVoList
        ) {
            @Schema(description = "서브 객체 타입 파라미터 VO")
            public record SubObjectVo(
                    @Schema(description = "String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
                    @JsonProperty("requestBodyString")
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String requestBodyString,
                    @Schema(description = "StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
                    @JsonProperty("requestBodyStringList")
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    List<String> requestBodyStringList
            ) {
            }
        }
    }

    @Operation(
            summary = "N6.2 : Post 요청 테스트 (입출력값 없음)",
            description = "입출력값이 없는 Post 메소드 요청 테스트\n\n"
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
            path = "/post-request-application-json-with-no-param",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void api6Dot2PostRequestTestWithNoInputAndOutput(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.api6Dot2PostRequestTestWithNoInputAndOutput(httpServletResponse);
    }


    ////
    @Operation(
            summary = "N7 : Post 요청 테스트 (x-www-form-urlencoded)",
            description = "x-www-form-urlencoded 형태의 Request Body 를 받는 Post 메소드 요청 테스트\n\n"
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
            path = "/post-request-x-www-form-urlencoded",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Api7PostRequestTestWithFormTypeRequestBodyOutputVo api7PostRequestTestWithFormTypeRequestBody(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Api7PostRequestTestWithFormTypeRequestBodyInputVo inputVo
    ) {
        return service.api7PostRequestTestWithFormTypeRequestBody(httpServletResponse, inputVo);
    }

    public record Api7PostRequestTestWithFormTypeRequestBodyInputVo(
            @Schema(description = "String Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,

            @Schema(description = "String Nullable Form 파라미터", example = "testString")
            @JsonProperty("requestFormStringNullable")
            String requestFormStringNullable,

            @Schema(description = "Int Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,

            @Schema(description = "Int Nullable Form 파라미터", example = "1")
            @JsonProperty("requestFormIntNullable")
            Integer requestFormIntNullable,

            @Schema(description = "Double Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,

            @Schema(description = "Double Nullable Form 파라미터", example = "1.1")
            @JsonProperty("requestFormDoubleNullable")
            Double requestFormDoubleNullable,

            @Schema(description = "Boolean Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,

            @Schema(description = "Boolean Nullable Form 파라미터", example = "true")
            @JsonProperty("requestFormBooleanNullable")
            Boolean requestFormBooleanNullable,

            @Schema(description = "StringList Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,

            @Schema(description = "StringList Nullable Form 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringListNullable")
            List<@Valid @NotNull String> requestFormStringListNullable
    ) {
    }

    public record Api7PostRequestTestWithFormTypeRequestBodyOutputVo(
            @Schema(description = "입력한 String Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,

            @Schema(description = "입력한 String Nullable Form 파라미터", example = "testString")
            @JsonProperty("requestFormStringNullable")
            String requestFormStringNullable,

            @Schema(description = "입력한 Int Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,

            @Schema(description = "입력한 Int Nullable Form 파라미터", example = "1")
            @JsonProperty("requestFormIntNullable")
            Integer requestFormIntNullable,

            @Schema(description = "입력한 Double Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,

            @Schema(description = "입력한 Double Nullable Form 파라미터", example = "1.1")
            @JsonProperty("requestFormDoubleNullable")
            Double requestFormDoubleNullable,

            @Schema(description = "입력한 Boolean Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,

            @Schema(description = "입력한 Boolean Nullable Form 파라미터", example = "true")
            @JsonProperty("requestFormBooleanNullable")
            Boolean requestFormBooleanNullable,

            @Schema(description = "입력한 StringList Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<String> requestFormStringList,

            @Schema(description = "입력한 StringList Nullable Form 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringListNullable")
            List<String> requestFormStringListNullable
    ) {
    }


    ////
    @Operation(
            summary = "N8 : Post 요청 테스트 (multipart/form-data)",
            description = "multipart/form-data 형태의 Request Body 를 받는 Post 메소드 요청 테스트\n\n" +
                    "MultipartFile 파라미터가 null 이 아니라면 저장\n\n"
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
            path = "/post-request-multipart-form-data",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Api8PostRequestTestWithMultipartFormTypeRequestBodyOutputVo api8PostRequestTestWithMultipartFormTypeRequestBody(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Api8PostRequestTestWithMultipartFormTypeRequestBodyInputVo inputVo
    ) throws IOException {
        return service.api8PostRequestTestWithMultipartFormTypeRequestBody(httpServletResponse, inputVo);
    }

    public record Api8PostRequestTestWithMultipartFormTypeRequestBodyInputVo(
            @Schema(description = "String Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,

            @Schema(description = "String Nullable Form 파라미터", example = "testString")
            @JsonProperty("requestFormStringNullable")
            String requestFormStringNullable,

            @Schema(description = "Int Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,

            @Schema(description = "Int Nullable Form 파라미터", example = "1")
            @JsonProperty("requestFormIntNullable")
            Integer requestFormIntNullable,

            @Schema(description = "Double Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,

            @Schema(description = "Double Nullable Form 파라미터", example = "1.1")
            @JsonProperty("requestFormDoubleNullable")
            Double requestFormDoubleNullable,

            @Schema(description = "Boolean Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,

            @Schema(description = "Boolean Nullable Form 파라미터", example = "true")
            @JsonProperty("requestFormBooleanNullable")
            Boolean requestFormBooleanNullable,

            @Schema(description = "StringList Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,

            @Schema(description = "StringList Nullable Form 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringListNullable")
            List<@Valid @NotNull String> requestFormStringListNullable,

            @Schema(description = "멀티 파트 파일", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("multipartFile")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartFile multipartFile,

            @Schema(description = "멀티 파트 파일 Nullable")
            @JsonProperty("multipartFileNullable")
            MultipartFile multipartFileNullable
    ) {
    }

    public record Api8PostRequestTestWithMultipartFormTypeRequestBodyOutputVo(
            @Schema(description = "입력한 String Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,

            @Schema(description = "입력한 String Nullable Form 파라미터", example = "testString")
            @JsonProperty("requestFormStringNullable")
            String requestFormStringNullable,

            @Schema(description = "입력한 Int Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,

            @Schema(description = "입력한 Int Nullable Form 파라미터", example = "1")
            @JsonProperty("requestFormIntNullable")
            Integer requestFormIntNullable,

            @Schema(description = "입력한 Double Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,

            @Schema(description = "입력한 Double Nullable Form 파라미터", example = "1.1")
            @JsonProperty("requestFormDoubleNullable")
            Double requestFormDoubleNullable,

            @Schema(description = "입력한 Boolean Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,

            @Schema(description = "입력한 Boolean Nullable Form 파라미터", example = "true")
            @JsonProperty("requestFormBooleanNullable")
            Boolean requestFormBooleanNullable,

            @Schema(description = "입력한 StringList Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,

            @Schema(description = "입력한 StringList Nullable Form 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringListNullable")
            List<@Valid @NotNull String> requestFormStringListNullable
    ) {
    }
}