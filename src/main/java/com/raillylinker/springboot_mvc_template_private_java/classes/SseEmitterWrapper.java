package com.raillylinker.springboot_mvc_template_private_java.classes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// [SseEmitter 래핑 클래스]
public class SseEmitterWrapper {
    // SSE Emitter의 만료시간 Milli Sec
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Long sseEmitterExpireTimeMs = 1000L * 10L;

    // SSE Emitter의 만료시간 이후 생존시간 Milli Sec
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Long sseEmitterSurviveTimeMs = 5000L;

    /*
        (SSE Emitter 를 고유값과 함께 모아둔 맵)
         map key = EmitterId
         map value = Pair(createTimeMillis, SseEmitter)
     */
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final ConcurrentHashMap<String, Pair<Long, SseEmitter>> emitterMap = new ConcurrentHashMap<>();

    /*
        (발행 이벤트 맵)
         map key = EmitterId
         map value = ArrayList(Pair(createTimeMillis, EventBuilder))

         key 는 emitterMap 과 동일한 값을 사용.
         value 의 ArrayList 는 이벤트 발행시간과 SseEventBuilder 객체의 Pair 로 이루어짐
     */
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final ConcurrentHashMap<String, ArrayList<Pair<Long, SseEmitter.SseEventBuilder>>> eventHistoryMap =
            new ConcurrentHashMap<>();

    // (발행 시퀀스)
    // emitter 고유성 보장을 위한 값으로 사용되며, 유한한 값이지만, 현재 날짜와 같이 사용됩니다.
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private long emitterIdSequence = 0L;

    // (스레드 풀)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    // (SSE Emitter 객체 발행)
    // !! 주의 : 함수 사용시 꼭 이 클래스 멤버변수인 emitterMapSemaphore, emitterEventMapSemaphore 로 감쌀것. !!
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public SseEmitter getSseEmitter(
            // 멤버고유번호(비회원은 null)
            Long memberUid,
            // 마지막으로 클라이언트가 수신했던 이벤트 아이디 ({EmitterId}/{발송시간})
            String lastSseEventId
    ) {
        List<String> lastSseEventIdSplit;

        // SSE Emitter ID 결정
        String sseEmitterId;
        if (lastSseEventId == null) {
            lastSseEventIdSplit = null;
            // 첫 발행시 Emitter ID 생성
            // sseEmitter 아이디 (멤버고유번호(비회원은 "null")_객체 아이디 발행일_발행총개수)
            sseEmitterId = memberUid + "_" + System.currentTimeMillis() + "_" + emitterIdSequence++;
        } else {
            // 기존 Emitter ID 재활용
            lastSseEventIdSplit = List.of(lastSseEventId.split("/"));
            sseEmitterId = lastSseEventIdSplit.getFirst(); // 지난번 발행된 emitter id
        }

        // sseEmitter 생성
        SseEmitter sseEmitter = new SseEmitter(sseEmitterExpireTimeMs);

        // 생성된 sseEmitter 및 생성시간 저장
        emitterMap.put(sseEmitterId, new Pair<>(System.currentTimeMillis(), sseEmitter));

        // SSE Emitter 콜백 설정
        sseEmitter.onTimeout(sseEmitter::complete);

        sseEmitter.onError(throwable -> {
            // 에러 발생 시 실행
            // 대표적으로 클라이언트가 연결을 끊었을 때 실행됨.
            // 이후 바로 onCompletion 이 실행되고, 함수는 재실행되지 않음.
        });

        sseEmitter.onCompletion(() -> {
            // sseEmitter 가 종료되었을 때 공통적, 최종적으로 실행
        });

        // 503 에러를 방지하기 위해, 처음 이미터 생성 시 빈 메시지라도 발송해야 함
        try {
            sseEmitter.send(SseEmitter.event().name("system").data("SSE Connected!"));
        } catch (Exception ignored) {
        }

        if (lastSseEventIdSplit != null) {
            // 마지막으로 이벤트를 수신한 시간
            long lastEventTimeMillis = Long.parseLong(lastSseEventIdSplit.get(1));

            // lastSseEventId 로 식별하여 다음에 발송해야할 이벤트들을 전송하기
            // 쌓인 event 처리
            if (eventHistoryMap.containsKey(sseEmitterId)) {
                // 지난 이벤트 리스트 가져오기
                ArrayList<Pair<Long, SseEmitter.SseEventBuilder>> eventHistoryList = eventHistoryMap.get(sseEmitterId);
                // 지난 이미터 정보를 이벤트 맵에서 제거
                eventHistoryMap.remove(sseEmitterId);

                for (Pair<Long, SseEmitter.SseEventBuilder> eventHistory : eventHistoryList) {
                    long pastEventTimeMillis = eventHistory.first();
                    SseEmitter.SseEventBuilder pastEvent = eventHistory.second();

                    if (pastEventTimeMillis > lastEventTimeMillis) {
                        // pastEventTimeMillis 이 lastEventTimeMillis 이후
                        // 밀린 이벤트 재전송
                        try {
                            sseEmitter.send(pastEvent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        // 시간 지난 이미터, 이벤트 판별 및 제거
        List<String> removeEmitterIdList = new ArrayList<>();
        for (var emitter : emitterMap.entrySet()) {
            // 이미터 생성시간
            long emitterCreateTimeMillis = emitter.getValue().first();
            // 현재시간
            long nowTimeMillis = System.currentTimeMillis();
            // 이미터 생성시간으로부터 몇 ms 지났는지
            long diffMs = nowTimeMillis - emitterCreateTimeMillis;

            // 이미터 생성 시간이 타임아웃 시간(+n 밀리초) 을 초과했을 때 = 타임아웃이 되었는데도 갱신할 의지가 없다고 판단될 때
            if (diffMs > sseEmitterExpireTimeMs + sseEmitterSurviveTimeMs) {
                // 삭제 목록에 포함
                removeEmitterIdList.add(emitter.getKey());
            }
        }

        // 삭제 목록에 있는 이미터와 이벤트 삭제
        for (String removeEmitterId : removeEmitterIdList) {
            emitterMap.remove(removeEmitterId);
            eventHistoryMap.remove(removeEmitterId);
        }

        return sseEmitter;
    }

    // (저장된 모든 emitter 에 이벤트 발송)
    public void broadcastEvent(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String eventName,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String eventMessage
    ) {
        for (var emitter : emitterMap.entrySet()) { // 저장된 모든 emitter 에 발송 (필터링 하려면 emitter.key 에 저장된 정보로 필터링 가능)
            executorService.execute(() -> {
                // 발송 시간
                long eventTimeMillis = System.currentTimeMillis();
                // 이벤트 고유값 생성 (이미터고유값/발송시간)
                String eventId = emitter.getKey() + "/" + eventTimeMillis;

                // 이벤트 빌더 생성
                SseEmitter.SseEventBuilder sseEventBuilder = SseEmitter.event()
                        .id(eventId)
                        .name(eventName)
                        .data(eventMessage);

                // 이벤트 누락 방지 처리를 위해 이벤트 빌더 기록
                eventHistoryMap.computeIfAbsent(emitter.getKey(), k -> new ArrayList<>())
                        .add(new Pair<>(eventTimeMillis, sseEventBuilder));

                // 이벤트 발송
                try {
                    emitter.getValue().second().send(sseEventBuilder);
                } catch (Exception ignored) {
                }
            });
        }
    }

    // (memberUidSet 에 속하는 모든 emitter 에 이벤트 발송)
    public void sendEventToMemberSet(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String eventName,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String eventMessage,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Set<Long> memberUidSet
    ) {
        for (var emitter : emitterMap.entrySet()) { // 저장된 모든 emitter 에 발송 (필터링 하려면 emitter.key 에 저장된 정보로 필터링 가능)
            executorService.execute(() -> {
                // emitterId (멤버고유번호(비회원은 "null")_객체 아이디 발행일_발행총개수) 에서 memberUid 추출
                String[] emitterIdSplit = emitter.getKey().split("_");
                String emitterMemberUid = emitterIdSplit[0]; // 멤버고유번호(비회원은 "null")

                for (Long memberUid : memberUidSet) {
                    if (emitterMemberUid.equals(String.valueOf(memberUid))) {
                        // 발송 시간
                        long eventTimeMillis = System.currentTimeMillis();
                        // 이벤트 고유값 생성 (이미터고유값/발송시간)
                        String eventId = emitter.getKey() + "/" + eventTimeMillis;

                        // 이벤트 빌더 생성
                        SseEmitter.SseEventBuilder sseEventBuilder = SseEmitter.event()
                                .id(eventId)
                                .name(eventName)
                                .data(eventMessage);

                        // 이벤트 누락 방지 처리를 위하여 이벤트 빌더 기록
                        eventHistoryMap.computeIfAbsent(emitter.getKey(), k -> new ArrayList<>())
                                .add(new Pair<>(eventTimeMillis, sseEventBuilder));

                        // 이벤트 발송
                        try {
                            emitter.getValue().second().send(sseEventBuilder);
                        } catch (Exception ignored) {
                        }

                        break;
                    }
                }
            });
        }
    }

    // (memberUid(비회원은 null) 에 속하는 emitter 에 이벤트 발송)
    public void sendEventToMember(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String eventName,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String eventMessage,
            Long memberUid
    ) {
        for (var emitter : emitterMap.entrySet()) { // 저장된 모든 emitter 에 발송 (필터링 하려면 emitter.key 에 저장된 정보로 필터링 가능)
            executorService.execute(() -> {
                // emitterId (멤버고유번호(비회원은 "null")_객체 아이디 발행일_발행총개수) 에서 memberUid 추출
                String[] emitterIdSplit = emitter.getKey().split("_");
                String emitterMemberUid = emitterIdSplit[0]; // 멤버고유번호(비회원은 "null")

                if (emitterMemberUid.equals(String.valueOf(memberUid))) {
                    // 발송 시간
                    long eventTimeMillis = System.currentTimeMillis();
                    // 이벤트 고유값 생성 (이미터고유값/발송시간)
                    String eventId = emitter.getKey() + "/" + eventTimeMillis;

                    // 이벤트 빌더 생성
                    SseEmitter.SseEventBuilder sseEventBuilder = SseEmitter.event()
                            .id(eventId)
                            .name(eventName)
                            .data(eventMessage);

                    // 이벤트 누락 방지 처리를 위하여 이벤트 빌더 기록
                    eventHistoryMap.computeIfAbsent(emitter.getKey(), k -> new ArrayList<>())
                            .add(new Pair<>(eventTimeMillis, sseEventBuilder));

                    // 이벤트 발송
                    try {
                        emitter.getValue().second().send(sseEventBuilder);
                    } catch (Exception ignored) {
                    }
                }
            });
        }
    }
}