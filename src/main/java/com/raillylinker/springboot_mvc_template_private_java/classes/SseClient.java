package com.raillylinker.springboot_mvc_template_private_java.classes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import okhttp3.*;
import okio.BufferedSource;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

// [SseClient 클래스]
public class SseClient {
    public SseClient(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestUrl
    ) {
        this.requestUrl = requestUrl;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String requestUrl;
    private Request originalRequest;
    private Call callObject;
    private String lastEventId;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final StringBuilder dataStringBuilder = new StringBuilder();
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private String eventName = "message";
    private BufferedSource bufferedSource;


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    // (SSE 구독 연결)
    public void connect(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long readTimeOutMs, // 수신 타임아웃 밀리초
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ListenerCallback listenerCallback // 리스너 콜백
    ) {
        // 비동기 실행
        // request 객체 생성
        originalRequest = new Request.Builder().url(requestUrl).build();

        // request 객체 첫 설정 시점을 멤버에게 패스
        listenerCallback.onConnectRequestFirstTime(this, originalRequest);

        // request 객체 첫 설정 시점을 멤버에게 패스
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(readTimeOutMs, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();

        callObject = okHttpClient.newCall(
                // SSE 연결에 필요한 request 객체 설정
                originalRequest.newBuilder()
                        .header("Accept-Encoding", "")
                        .header("Accept", "text/event-stream")
                        .header("Cache-Control", "no-cache")
                        .build()
        );

        // SSE 구독 요청하기
        callObject.enqueue(new Callback() {
            @Override
            public void onFailure(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    Call call,
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    IOException e
            ) {
                if (!retry(readTimeOutMs, okHttpClient, listenerCallback, e, null)) {
                    listenerCallback.onDisconnected(SseClient.this);
                    disconnect();
                }
            }

            @Override
            public void onResponse(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    Call call,
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    Response response
            ) throws IOException {
                if (response.isSuccessful()) {
                    // 구독 요청 성공시
                    try (@Valid @NotNull @org.jetbrains.annotations.NotNull ResponseBody body = Objects.requireNonNull(response.body())) {
                        bufferedSource = body.source();
                        bufferedSource.timeout().timeout(readTimeOutMs, TimeUnit.MILLISECONDS);
                        listenerCallback.onConnect(SseClient.this, response);
                        while (true) {
                            if (callObject == null ||
                                    callObject.isCanceled() ||
                                    !read(readTimeOutMs, okHttpClient, listenerCallback)) {
                                break;
                            }
                        }
                    }
                } else {
                    if (!retry(readTimeOutMs, okHttpClient, listenerCallback, new IOException(response.message()), response)) {
                        listenerCallback.onDisconnected(SseClient.this);
                        disconnect();
                    }
                }
            }
        });
    }

    // (SSE 구독 해제)
    public void disconnect() {
        if (callObject != null && !callObject.isCanceled()) {
            callObject.cancel();
            callObject = null;
        }
        lastEventId = null;
        bufferedSource = null;
        dataStringBuilder.setLength(0);
        eventName = "message";
        originalRequest = null;
    }

    // ---------------------------------------------------------------------------------------------
    // <비공개 메소드 공간>
    private boolean retry(long readTimeoutMsMbr, OkHttpClient clientMbr, ListenerCallback listenerCallback, Throwable throwable, Response response) {
        if (!Thread.currentThread().isInterrupted() &&
                !callObject.isCanceled() &&
                listenerCallback.onPreRetry(this, originalRequest, throwable, response)) {
            Request.Builder requestBuilder = originalRequest.newBuilder()
                    .header("Accept-Encoding", "")
                    .header("Accept", "text/event-stream")
                    .header("Cache-Control", "no-cache");
            if (lastEventId != null) {
                requestBuilder.header("Last-Event-Id", lastEventId);
            }
            callObject = clientMbr.newCall(requestBuilder.build());
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException ignored) {
                return false;
            }
            if (!Thread.currentThread().isInterrupted() && !callObject.isCanceled()) {
                callObject.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (!retry(readTimeoutMsMbr, clientMbr, listenerCallback, e, null)) {
                            listenerCallback.onDisconnected(SseClient.this);
                            disconnect();
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            try (ResponseBody body = response.body()) {
                                bufferedSource = body.source();
                                bufferedSource.timeout().timeout(readTimeoutMsMbr, TimeUnit.MILLISECONDS);
                                listenerCallback.onConnect(SseClient.this, response);
                                while (true) {
                                    if (callObject == null || callObject.isCanceled() || !read(readTimeoutMsMbr, clientMbr, listenerCallback)) {
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (!retry(readTimeoutMsMbr, clientMbr, listenerCallback, new IOException(response.message()), response)) {
                                listenerCallback.onDisconnected(SseClient.this);
                                disconnect();
                            }
                        }
                    }
                });
                return true;
            }
        }
        return false;
    }

    private boolean read(long readTimeoutMsMbr, OkHttpClient clientMbr, ListenerCallback listenerCallback) {
        try {
            String line = bufferedSource.readUtf8LineStrict();
            if (line.isEmpty()) {
                if (dataStringBuilder.length() > 0) {
                    String dataString = dataStringBuilder.toString();
                    if (dataString.endsWith("\n")) {
                        dataString = dataString.substring(0, dataString.length() - 1);
                    }
                    listenerCallback.onMessageReceive(SseClient.this, lastEventId, eventName, dataString);
                    dataStringBuilder.setLength(0);
                    eventName = "message";
                }
            } else {
                int colonIndex = line.indexOf(':');
                if (colonIndex == 0) {
                    listenerCallback.onCommentReceive(SseClient.this, line.substring(1).trim());
                } else if (colonIndex != -1) {
                    String field = line.substring(0, colonIndex);
                    String value = "";
                    int valueIndex = colonIndex + 1;
                    if (valueIndex < line.length()) {
                        if (line.charAt(valueIndex) == ' ') {
                            valueIndex++;
                        }
                        value = line.substring(valueIndex);
                    }
                    switch (field) {
                        case "data" -> dataStringBuilder.append(value).append('\n');
                        case "id" -> lastEventId = value;
                        case "event" -> eventName = value;
                    }
                } else {
                    switch (line) {
                        case "data" -> dataStringBuilder.append("").append('\n');
                        case "id" -> lastEventId = "";
                        case "event" -> eventName = "";
                    }
                }
            }
        } catch (IOException e) { // SSE 타임아웃 등
            if (!retry(readTimeoutMsMbr, clientMbr, listenerCallback, e, null)) {
                listenerCallback.onDisconnected(SseClient.this);
                disconnect();
            }
            return false;
        }
        return true;
    }

    // ---------------------------------------------------------------------------------------------
    // <중첩 클래스 공간>
    public interface ListenerCallback {
        // 처음 SSE 구독시 request 객체가 처음 생성 되었을 때(connectAsync 메소드 실행시) 한번 실행
        void onConnectRequestFirstTime(SseClient sse, Request originalRequest);

        // SSE 연결, 재연결 되었을 때마다
        void onConnect(SseClient sse, Response response);

        // SSE 메세지 수신시
        void onMessageReceive(SseClient sse, String eventId, String event, String message);

        // SSE Comment 수신시
        void onCommentReceive(SseClient sse, String comment);

        // 재 연결을 신청할 때마다 실행 (반환 값으로 재연결 여부를 반환)
        boolean onPreRetry(SseClient sse, Request originalRequest, Throwable throwable, Response response);

        // SSE 구독 연결이 끊겼을 때
        void onDisconnected(SseClient sse);
    }
}
