package com.raillylinker.springboot_mvc_template_private_java.configurations;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// [Hibernate5Module 이 지연로딩 되는 객체의 프로퍼티 값이 비어있더라도 직렬화를 가능하게 하는 설정]
// 이 설정이 없다면, hibernateLazyInitializer 라는 하이버네이트 프록시 객체의 프로퍼티를 직렬화 하려다가 에러 발생
@Configuration
public class JacksonConfig {
    @Bean
    public @NotNull Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }
}