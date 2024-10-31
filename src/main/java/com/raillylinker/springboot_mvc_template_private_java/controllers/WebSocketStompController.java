package com.raillylinker.springboot_mvc_template_private_java.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raillylinker.springboot_mvc_template_private_java.services.WebSocketStompService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

// [WebSocket STOMP 컨트롤러]
// api1 은 external_files/files_for_api_test/html_file_sample/websocket-stomp.html 파일로 테스트 가능
@Controller
public class WebSocketStompController {
    public WebSocketStompController(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            WebSocketStompService service
    ) {
        this.service = service;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final WebSocketStompService service;

    // 메세지 함수 호출 경로 (WebSocketStompConfig 의 setApplicationDestinationPrefixes 설정과 합쳐서 호출 : /app/test)
    @MessageMapping("/test")
    // 이 함수의 리턴값 반환 위치(/topic 을 구독중인 유저에게 return 값을 반환)
    @SendTo("/topic")
    public TopicVo api1SendToTopicTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Api1SendToTopicTestInputVo inputVo
    ) {
        return service.api1SendToTopicTest(inputVo);
    }

    public record Api1SendToTopicTestInputVo(
            @JsonProperty("chat")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String chat
    ) {
    }

    // ---------------------------------------------------------------------------------------------
    // <채팅 데이터 VO 선언 공간>
    public record TopicVo(
            @JsonProperty("content")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String content
    ) {
    }
}