package com.raillylinker.springboot_mvc_template_private_java.web_socket_handlers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 1:1 웹 소켓 테스팅 핸들러
// configurations/WebSocketConfig 에서 핸들러 설정 시 사용됩니다.
// 텍스트 데이터 양방향 연결
public class TestWebSocketHandler extends TextWebSocketHandler {
    // <멤버 변수 공간>
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(this.getClass());

    // (현재 웹 소켓에 연결된 세션 리스트)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final ConcurrentHashMap<String, WebSocketSession> webSocketSessionHashMap = new ConcurrentHashMap<>();

    // (스레드 풀)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final ExecutorService executorService = Executors.newCachedThreadPool();


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    // (Client 연결 콜백)
    @Override
    public void afterConnectionEstablished(@Valid @NotNull @org.jetbrains.annotations.NotNull WebSocketSession webSocketSession) {
        // 웹 소켓 세션을 추가
        webSocketSessionHashMap.put(webSocketSession.getId(), webSocketSession);
    }

    // (Client 해제 콜백)
    @SuppressWarnings("resource")
    @Override
    public void afterConnectionClosed(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            WebSocketSession webSocketSession,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            CloseStatus status
    ) {
        // 세션을 리스트에서 제거
        webSocketSessionHashMap.remove(webSocketSession.getId());
    }

    // (텍스트 메세지 수신 콜백)
    @Override
    public void handleTextMessage(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            WebSocketSession webSocketSession,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            TextMessage message
    ) {
        // 보내온 String 메세지를 객체로 해석
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        Type messageType = new TypeToken<MessagePayloadVo>() {
        }.getType();
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        MessagePayloadVo messagePayloadVo = new Gson().fromJson(message.getPayload(), messageType);

        classLogger.info("messagePayloadVo : {}", messagePayloadVo);

        // 메세지 수신 후 몇 초 후 서버 사이드에서 메세지 전송
        executorService.execute(() -> {
            try {
                // 범위 랜덤 밀리초 대기
                long randomMs = (long) (Math.random() * 2000);
                Thread.sleep(randomMs);

                // 메세지를 보낸 클라이언트에게 메세지 전송
                webSocketSession.sendMessage(new TextMessage(new Gson().toJson(
                        new MessagePayloadVo("Server", randomMs + " 밀리초 대기 후 전송함")
                )));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                classLogger.error("Error sending message", e);
            }
        });
    }


    // ---------------------------------------------------------------------------------------------
    // <비공개 메소드 공간>


    // ---------------------------------------------------------------------------------------------
    // <중첩 클래스 공간>
    // (메세지 Vo)
    /*
         고도화시에는 아래 VO 에 더 많은 정보를 저장하여 이를 이용하여 기능을 구현하세요.
         일반적으로 양방향 연결이 필요한 기능인 채팅에 관련하여,
         필요한 기능들에 필요한 형식을 미리 만들어서 제공해주는 프로토콜인 STOMP 를 사용할 수도 있습니다.
     */
    public record MessagePayloadVo(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String sender,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String message
    ) {
        @Override
        public String toString() {
            return "MessagePayloadVo{" +
                    "sender='" + sender + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}