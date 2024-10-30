package com.raillylinker.springboot_mvc_template_private_java.configurations;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {
    public QueryDslConfig(@Valid @NotNull @org.jetbrains.annotations.NotNull EntityManager em) {
        this.em = em;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final EntityManager em;

    @Bean
    public @Valid @NotNull @org.jetbrains.annotations.NotNull JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(em);
    }
}