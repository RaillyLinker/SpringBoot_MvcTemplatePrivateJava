package com.raillylinker.springboot_mvc_template_private_java.services.impls;

import com.raillylinker.springboot_mvc_template_private_java.controllers.WebSocketStompController;
import com.raillylinker.springboot_mvc_template_private_java.services.WebSocketStompService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketStompServiceImpl implements WebSocketStompService {
    public WebSocketStompServiceImpl(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SimpMessagingTemplate simpMessagingTemplate
    ) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(WebSocketStompServiceImpl.class);


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public WebSocketStompController.TopicVo api1SendToTopicTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            WebSocketStompController.Api1SendToTopicTestInputVo inputVo
    ) {
        // 이렇게 SimpMessagingTemplate 객체로 메세지를 전달할 수 있습니다.
        // /topic 을 구독하는 모든 유저에게 메시지를 전달하였습니다.
        simpMessagingTemplate.convertAndSend(
                "/topic",
                new WebSocketStompController.TopicVo(inputVo + " : SimpMessagingTemplate Test")
        );

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 스레드 중단 상태 복원
            classLogger.error("Thread was interrupted", e);
        }

        // 이렇게 @SendTo 함수 결과값으로 메세지를 전달할 수도 있습니다.
        // 앞서 @SendTo 에 설정한 /topic 을 구독하는 모든 유저에게 마시지를 전달하였습니다.
        return new WebSocketStompController.TopicVo(inputVo + " : @SendTo Test");
    }
}
