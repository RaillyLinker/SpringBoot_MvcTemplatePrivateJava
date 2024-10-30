package com.raillylinker.springboot_mvc_template_private_java.configurations;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

// [WebSocket STOMP 설정]
@EnableWebSocketMessageBroker
@Configuration
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            StompEndpointRegistry registry
    ) {
        registry
                // 접속 EndPoint
                // ex : var socket = new SockJS('http://localhost:8080/stomp');
                .addEndpoint("/stomp")
                // webSocket 연결 CORS 는 WebConfig 가 아닌 여기서 설정
                .setAllowedOriginPatterns("*")
                .withSockJS()
                .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.2/sockjs.js");
    }

    @Override
    public void configureMessageBroker(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MessageBrokerRegistry registry
    ) {
        /*
             WebSocketStompController 의 MessageMapping 연결 주소
             @MessageMapping("/test") 라고 되어있다면,
             stompClient.send("/app/test", {}, JSON.stringify({'chat': "sample Text"}));
             이처럼 요청 가능
         */
        registry.setApplicationDestinationPrefixes("/app");

        /*
             구독 주소
             stompClient.subscribe('/topic', function (topic) {
                 // 구독 콜백 : 구독된 채널에 메세지가 날아오면 여기서 받음
             });
             위와 같이 topic 이라는 것을 구독하면,
             @SendTo("/topic") 로 설정 된 메세지 함수 실행 혹은
             simpMessagingTemplate.convertAndSend("/topic", TopicVo("waiting..."))
             이렇게 메세지 전달시 그 메세지를 받을 수 있습니다.
         */
        registry.enableSimpleBroker("/topic");
    }

    @Override
    public void configureClientInboundChannel(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ChannelRegistration registration
    ) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public @Valid @NotNull @org.jetbrains.annotations.NotNull Message<?> preSend(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    Message<?> message,
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    MessageChannel channel
            ) {
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

                // 클라이언트 세션 아이디
                String sessionId = accessor.getSessionId();

                // 인증 토큰 (ex : "Bearer asdafdsaflkj123432")
                // String authorization = accessor.getFirstNativeHeader("Authorization");

                if (StompCommand.CONNECT == accessor.getCommand()) {
                    // 클라이언트 연결시
                    System.out.println("CONNECT");
                } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
                    // 구독시
                    String destination = accessor.getDestination();
                    System.out.println("SUBSCRIBE " + destination);
                } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
                    // 연결 해제시
                    // JavaScript 에서 stompClient.disconnect(); 실행시 이것이 두번 실행됩니다.
                    // sessionId 를 사용해서 중복 방지 처리를 하세요.
                    System.out.println("DISCONNECT " + sessionId);
                }
                return message;
            }
        });
    }

    @Override
    public void configureWebSocketTransport(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            WebSocketTransportRegistration registry
    ) {
        // WebSocket으로 전송되는 메시지의 최대 크기를 설정
        registry.setMessageSizeLimit(160 * 64 * 1024);
        // 메시지 전송에 대한 시간 제한을 설정
        registry.setSendTimeLimit(100 * 10000);
        // 송신 버퍼의 크기 제한을 설정
        registry.setSendBufferSizeLimit(3 * 512 * 1024);
    }
}
