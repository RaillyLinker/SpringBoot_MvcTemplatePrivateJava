package com.raillylinker.springboot_mvc_template_private_java.annotations;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// [MongoDB 트랜젝션 어노테이션]
// MongoDB 에서 트랜젝션을 사용하려면 MongoDB Replica Set 설정을 하여 실행시키는 환경에서만 가능합니다.
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomMongoDbTransactional {
    @NotNull String[] transactionManagerBeanNameList();
}