package com.raillylinker.springboot_mvc_template_private_java.annotations;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// [JPA 의 Transactional 을 여러 TransactionManager 으로 사용 가능하도록 개조한 annotation]
// 사용 예시 : @CustomTransactional([Db1MainConfig.TRANSACTION_NAME])
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomTransactional {
    @Valid @NotNull String[] transactionManagerBeanNameList();
}