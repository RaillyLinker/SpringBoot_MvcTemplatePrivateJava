package com.raillylinker.springboot_mvc_template_private_java.configurations;

import com.raillylinker.springboot_mvc_template_private_java.web_socket_handlers.TestWebSocketHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(@NotNull WebSocketHandlerRegistry registry) {
        // (Websocket 연결 url 과 핸들러 연결)
        /*
             addHandler 에서 paths 를 /ws/test 로 설정했다면,
             JavaScript 에서는,
             var websocket = new SockJS('http://localhost:8080/ws/test');
             이렇게 연결하면 됩니다.
         */
        registry.addHandler(
                        // 아래 주소로 접속하면 실행될 핸들러
                        new TestWebSocketHandler(),
                        // "ws://localhost:8080/ws/test" 로 접속
                        "/ws/test"
                )
                // webSocket 연결 CORS 는 WebConfig 가 아닌 여기서 설정
                .setAllowedOriginPatterns("*")
                .withSockJS() // 이것을 사용하면 "ws://localhost:8080/ws/test/websocket" 로 접속
                .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.2/sockjs.js");
    }

    // websocket 관련 설정
    @Bean
    public @NotNull ServletServerContainerFactoryBean createWebSocketContainer() {
        @NotNull ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }
}