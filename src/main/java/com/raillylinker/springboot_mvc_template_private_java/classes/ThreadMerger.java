package com.raillylinker.springboot_mvc_template_private_java.classes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

// [스레드 병합 클래스]
/*
    사용 예시 :
    val threadMerger =
        ThreadMerger(
            3,
            onComplete = {
                screenDataSemaphoreMbr.release()
                onComplete()
            }
        )
    위와 같이 객체를 생성.
    threadMerger.threadComplete()
    위와 같이 각 스레드동작 완료시마다 호출하면 객체 생성시 설정한 갯수만큼 호출되면 onComplete 가 실행되고, 그 이상으로 호출하면 아무 반응 없음
 */
public class ThreadMerger {
    public ThreadMerger(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer threadTotalCount,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Runnable onComplete
    ) {
        this.threadTotalCount = threadTotalCount;
        this.onComplete = onComplete;
    }

    // 병합할 스레드 총개수
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Integer threadTotalCount;

    // 스레드 병합이 모두 끝나면 실행할 콜백 함수
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Runnable onComplete;

    // (스레드 풀)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    // (현재 병합된 스레드 개수 및 세마포어)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private Integer mergedThreadCount = 0;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Semaphore mergedThreadCountSemaphore = new Semaphore(1);


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    // (스레드 병합 개수 +1)
    public void mergeThread() {
        executorService.execute(() -> {
            try {
                mergedThreadCountSemaphore.acquire();
                // 오버플로우 방지
                if (mergedThreadCount < 0) {
                    mergedThreadCountSemaphore.release();
                    return;
                }
                // 스레드 병합 카운트 +1
                mergedThreadCount++;
                if (mergedThreadCount.equals(threadTotalCount)) {
                    // 병합 카운트가 스레드 총 개수에 다다랐을 때
                    onComplete.run();
                }
            } catch (Exception ignored) {
            } finally {
                mergedThreadCountSemaphore.release();
            }
        });
    }

    // (스레드 병합 개수 초기화)
    public void rewind() {
        try {
            mergedThreadCountSemaphore.acquire();
            mergedThreadCount = 0;
        } catch (InterruptedException ignored) {
        } finally {
            mergedThreadCountSemaphore.release();
        }
    }
}
