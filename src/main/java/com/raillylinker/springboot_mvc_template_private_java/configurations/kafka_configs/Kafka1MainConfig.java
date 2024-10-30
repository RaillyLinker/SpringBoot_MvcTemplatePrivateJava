package com.raillylinker.springboot_mvc_template_private_java.configurations.kafka_configs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class Kafka1MainConfig {
    // !!!application.yml 의 kafka-cluster 안에 작성된 이름 할당하기!!!
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private static final String KAFKA_CONFIG_NAME = "kafka1-main";

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static final String CONSUMER_BEAN_NAME = KAFKA_CONFIG_NAME + "_ConsumerFactory";
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static final String PRODUCER_BEAN_NAME = KAFKA_CONFIG_NAME + "_ProducerFactory";

    @Value("${kafka-cluster." + KAFKA_CONFIG_NAME + ".uri}")
    private String uri;

    @Value("${kafka-cluster." + KAFKA_CONFIG_NAME + ".consumer.username}")
    private String userName;

    @Value("${kafka-cluster." + KAFKA_CONFIG_NAME + ".consumer.password}")
    private String password;

    @Bean(CONSUMER_BEAN_NAME)
    public @Valid @NotNull @org.jetbrains.annotations.NotNull ConcurrentKafkaListenerContainerFactory<String, Object> kafkaConsumer() {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        Map<String, Object> config = new HashMap<>();
        // Kafka 브로커에 연결하기 위한 주소를 설정합니다. 여러 개의 브로커가 있을 경우, 콤마로 구분하여 나열합니다.
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, uri);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        // SASL/SCRAM 인증 설정 추가
        config.put("security.protocol", "SASL_PLAINTEXT");
        config.put("sasl.mechanism", "PLAIN");
        config.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + userName + "\" password=\"" + password + "\";");

        @Valid @NotNull @org.jetbrains.annotations.NotNull
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(config));

        return factory;
    }

    @Bean(PRODUCER_BEAN_NAME)
    public @Valid @NotNull @org.jetbrains.annotations.NotNull KafkaTemplate<String, Object> kafkaProducer() {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        Map<String, Object> config = new HashMap<>();
        // Kafka 브로커에 연결하기 위한 주소를 설정합니다. 여러 개의 브로커가 있을 경우, 콤마로 구분하여 나열합니다.
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, uri);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // SASL/SCRAM 인증 설정 추가
        config.put("security.protocol", "SASL_PLAINTEXT");
        config.put("sasl.mechanism", "PLAIN");
        config.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + userName + "\" password=\"" + password + "\";");

        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(config));
    }
}
