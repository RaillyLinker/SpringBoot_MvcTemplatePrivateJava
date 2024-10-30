package com.raillylinker.springboot_mvc_template_private_java.kafka_components.consumers;

import com.raillylinker.springboot_mvc_template_private_java.configurations.kafka_configs.Kafka1MainConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Component
public class Kafka1MainConsumer {
    // <멤버 변수 공간>
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(Kafka1MainConsumer.class);

    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    // (testTopic1 에 대한 리스너)
    @KafkaListener(
            topics = "testTopic1",
            groupId = "group_1",
            containerFactory = Kafka1MainConfig.CONSUMER_BEAN_NAME
    )
    public void testTopic1Group0Listener(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ConsumerRecord<String, TestTopic1Group0ListenerInputVo> data
    ) {
        classLogger.info(">> testTopic1 group_1 : {}", data);
    }

    // 내포된 클래스 (TestTopic1Group0ListenerInputVo)
    public record TestTopic1Group0ListenerInputVo(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String test,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer test1
    ) {
    }

    // (testTopic2 에 대한 리스너)
    @KafkaListener(
            topics = "testTopic2",
            groupId = "group_1",
            containerFactory = Kafka1MainConfig.CONSUMER_BEAN_NAME
    )
    public void testTopic2Group0Listener(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ConsumerRecord<String, String> data
    ) {
        classLogger.info(">> testTopic2 group_1 : {}", data);
    }

    // (testTopic2 에 대한 동일 그룹 테스트 리스너)
    // 동일 topic 에 동일 group 을 설정할 경우, 리스너는 한개만을 선택하고 다른 하나는 침묵합니다.
    @KafkaListener(
            topics = "testTopic2",
            groupId = "group_1",
            containerFactory = Kafka1MainConfig.CONSUMER_BEAN_NAME
    )
    public void testTopic2Group0Listener2(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ConsumerRecord<String, String> data
    ) {
        classLogger.info(">> testTopic2 group_1 2 : {}", data);
    }

    // (testTopic2 에 대한 리스너 - 그룹 변경)
    @KafkaListener(
            topics = "testTopic2",
            groupId = "group_2",
            containerFactory = Kafka1MainConfig.CONSUMER_BEAN_NAME
    )
    public void testTopic2Group1Listener(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ConsumerRecord<String, String> data
    ) {
        classLogger.info(">> testTopic2 group_2 : {}", data);
    }
}
