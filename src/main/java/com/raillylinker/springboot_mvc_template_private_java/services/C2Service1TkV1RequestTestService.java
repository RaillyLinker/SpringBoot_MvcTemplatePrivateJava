package com.raillylinker.springboot_mvc_template_private_java.services;

import com.raillylinker.springboot_mvc_template_private_java.controllers.C2Service1TkV1RequestTestController;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

public interface C2Service1TkV1RequestTestService {
    // (기본 요청 테스트 API)
    String api1BasicRequestTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    // (요청 Redirect 테스트)
    ModelAndView api2RedirectTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    // (요청 Forward 테스트)
    ModelAndView api3ForwardTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    // (Get 요청 테스트 (Query Parameter))
    C2Service1TkV1RequestTestController.Api4GetRequestTestOutputVo api4GetRequestTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String queryParamString,
            String queryParamStringNullable,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer queryParamInt,
            Integer queryParamIntNullable,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double queryParamDouble,
            Double queryParamDoubleNullable,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean queryParamBoolean,
            Boolean queryParamBooleanNullable,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> queryParamStringList,
            List<@Valid @NotNull String> queryParamStringListNullable
    );


    // (Get 요청 테스트 (Path Parameter))
    C2Service1TkV1RequestTestController.Api5GetRequestTestWithPathParamOutputVo api5GetRequestTestWithPathParam(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pathParamInt
    );


    // (Post 요청 테스트 (application-json))
    C2Service1TkV1RequestTestController.Api6PostRequestTestWithApplicationJsonTypeRequestBodyOutputVo api6PostRequestTestWithApplicationJsonTypeRequestBody(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            C2Service1TkV1RequestTestController.Api6PostRequestTestWithApplicationJsonTypeRequestBodyInputVo inputVo
    );


    // (Post 요청 테스트 (application-json, 객체 파라미터 포함))
    C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2InputVo inputVo
    );


    // (Post 요청 테스트 (입출력값 없음))
    void api6Dot2PostRequestTestWithNoInputAndOutput(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    // (Post 요청 테스트 (x-www-form-urlencoded))
    C2Service1TkV1RequestTestController.Api7PostRequestTestWithFormTypeRequestBodyOutputVo api7PostRequestTestWithFormTypeRequestBody(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            C2Service1TkV1RequestTestController.Api7PostRequestTestWithFormTypeRequestBodyInputVo inputVo
    );


    // (Post 요청 테스트 (multipart/form-data))
    C2Service1TkV1RequestTestController.Api8PostRequestTestWithMultipartFormTypeRequestBodyOutputVo api8PostRequestTestWithMultipartFormTypeRequestBody(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            C2Service1TkV1RequestTestController.Api8PostRequestTestWithMultipartFormTypeRequestBodyInputVo inputVo
    ) throws IOException;


//    // (Post 요청 테스트2 (multipart/form-data))
//    C2Service1TkV1RequestTestController.Api9PostRequestTestWithMultipartFormTypeRequestBody2OutputVo api9PostRequestTestWithMultipartFormTypeRequestBody2(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse,
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            C2Service1TkV1RequestTestController.Api9PostRequestTestWithMultipartFormTypeRequestBody2InputVo inputVo
//    );
//
//
//    // (Post 요청 테스트 (multipart/form-data - JsonString))
//    C2Service1TkV1RequestTestController.Api10PostRequestTestWithMultipartFormTypeRequestBody3OutputVo api10PostRequestTestWithMultipartFormTypeRequestBody3(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse,
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            C2Service1TkV1RequestTestController.Api10PostRequestTestWithMultipartFormTypeRequestBody3InputVo inputVo
//    );
//
//
//    // (인위적 에러 발생 테스트)
//    void api11GenerateErrorTest(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse
//    );
//
//
//    // (결과 코드 발생 테스트)
//    void api12ReturnResultCodeThroughHeaders(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse,
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            C2Service1TkV1RequestTestController.Api12ReturnResultCodeThroughHeadersErrorTypeEnum errorType
//    );
//
//
//    // (인위적 응답 지연 테스트)
//    void api13ResponseDelayTest(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse,
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            Long delayTimeSec
//    );
//
//
//    // (text/string 반환 샘플)
//    String api14ReturnTextStringTest(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse
//    );
//
//
//    // (text/html 반환 샘플)
//    ModelAndView api15ReturnTextHtmlTest(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse
//    );
//
//
//    // (byte 반환 샘플)
//    Resource api16ReturnByteDataTest(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse
//    );
//
//
//    // (비디오 스트리밍 샘플)
//    Resource api17VideoStreamingTest(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            C2Service1TkV1RequestTestController.Api17VideoStreamingTestVideoHeight videoHeight,
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse
//    );
//
//
//    // (오디오 스트리밍 샘플)
//    Resource api18AudioStreamingTest(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse
//    );
//
//
//    // (비동기 처리 결과 반환 샘플)
//    DeferredResult<C2Service1TkV1RequestTestController.Api19AsynchronousResponseTestOutputVo> api19AsynchronousResponseTest(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse
//    );
//
//
//    // (클라이언트가 특정 SSE 이벤트를 구독)
//    SseEmitter api20SseTestSubscribe(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse,
//            String lastSseEventId
//    );
//
//
//    // (SSE 이벤트 전송 트리거 테스트)
//    void api21SseTestEventTrigger(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse
//    );
//
//
//    // (빈 리스트 받기 테스트)
//    C2Service1TkV1RequestTestController.Api22EmptyListRequestTestOutputVo api22EmptyListRequestTest(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse,
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            List<@Valid @NotNull String> stringList,
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            C2Service1TkV1RequestTestController.Api22EmptyListRequestTestInputVo inputVo
//    );
}
