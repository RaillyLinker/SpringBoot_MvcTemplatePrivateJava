package com.raillylinker.springboot_mvc_template_private_java.configurations;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

// [JavaMail 설정]
@Configuration
public class MailConfig {
    public MailConfig(
            @Value("${custom-config.smtp.host}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String host,
            @Value("${custom-config.smtp.port}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer port,
            @Value("${custom-config.smtp.sender-name}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String senderName,
            @Value("${custom-config.smtp.sender-password}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String senderPassword,
            @Value("${custom-config.smtp.time-out-millis}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String timeOutMillis
    ) {
        this.host = host;
        this.port = port;
        this.senderName = senderName;
        this.senderPassword = senderPassword;
        this.timeOutMillis = timeOutMillis;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String host;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Integer port;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String senderName;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String senderPassword;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String timeOutMillis;

    @Bean
    public @Valid @NotNull @org.jetbrains.annotations.NotNull JavaMailSenderImpl javaMailSender() {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(senderName);
        mailSender.setPassword(senderPassword);

        @Valid @NotNull @org.jetbrains.annotations.NotNull
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.connectiontimeout", timeOutMillis);
        props.put("mail.smtp.timeout", timeOutMillis);
        props.put("mail.smtp.writetimeout", timeOutMillis);

        // SMTP 종류별 설정
        // 보안 설정이 필요없는 상황이라면 아래 코드를 주석처리 하면 됩니다.
        if (port == 587) {
            // port 587 일 경우
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");
        } else if (port == 465) {
            // port 465 일 경우
            props.put("mail.smtp.ssl.enable", "true"); // SSL 활성화
            props.put("mail.smtp.auth", "true"); // SMTP 인증 활성화
            props.put("mail.smtp.connectiontimeout", "10000");
            props.put("mail.smtp.timeout", "10000");
            props.put("mail.smtp.writetimeout", "10000");
            props.put("mail.smtp.ssl.checkserveridentity", "false");
            props.put("mail.smtp.ssl.trust", "*");
        }

        return mailSender;
    }
}
