package com.raillylinker.springboot_mvc_template_private_java.aop_aspects;

import com.raillylinker.springboot_mvc_template_private_java.annotations.CustomMongoDbTransactional;
import com.raillylinker.springboot_mvc_template_private_java.classes.Pair;
import com.raillylinker.springboot_mvc_template_private_java.data_sources.const_objects.ProjectConst;
import jakarta.validation.Valid;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;

// [MongoDB @CustomMongoDbTransactional 어노테이션 함수 처리 AOP]
@Component
@Aspect
public class MongoDbTransactionAnnotationAspect {
    public MongoDbTransactionAnnotationAspect(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ApplicationContext applicationContext
    ) {
        this.applicationContext = applicationContext;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final ApplicationContext applicationContext;

    // MongoDb 트랜젝션용 어노테이션인 CustomMongoDbTransactional 파일의 프로젝트 경로
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private static final String MONGO_DB_TRANSACTION_ANNOTATION_PATH =
            "@annotation(" + ProjectConst.PACKAGE_NAME + ".annotations.CustomMongoDbTransactional)";


    // ---------------------------------------------------------------------------------------------
    // <AOP 작성 공간>
    // (@CustomMongoDbTransactional 를 입력한 함수 실행 전후에 MongoDB 트랜젝션 적용)
    @Around(MONGO_DB_TRANSACTION_ANNOTATION_PATH)
    public Object aroundMongoDbTransactionAnnotationFunction(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ProceedingJoinPoint joinPoint
    ) throws Throwable {
        Object proceed;

        // transactionManager and transactionStatus 리스트
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        List<Pair<PlatformTransactionManager, TransactionStatus>> transactionManagerAndTransactionStatusList = new ArrayList<>();

        try {
            // annotation 에 설정된 transaction 순차 실행 및 저장
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            CustomMongoDbTransactional customTransactional = signature.getMethod().getAnnotation(CustomMongoDbTransactional.class);
            for (@Valid @NotNull @org.jetbrains.annotations.NotNull String transactionManagerBeanName : customTransactional.transactionManagerBeanNameList()) {
                // annotation 에 저장된 transactionManager Bean 이름으로 Bean 객체 가져오기
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                MongoTransactionManager platformTransactionManager = (MongoTransactionManager) applicationContext.getBean(transactionManagerBeanName);

                // transaction 시작 및 정보 저장
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
                transactionManagerAndTransactionStatusList.add(new Pair<>(platformTransactionManager, transactionStatus));
            }

            // 함수 실행
            proceed = joinPoint.proceed();

            // annotation 에 설정된 transaction commit 역순 실행 및 저장
            for (@Valid @NotNull @org.jetbrains.annotations.NotNull Integer transactionManagerIdx = transactionManagerAndTransactionStatusList.size() - 1; transactionManagerIdx >= 0; transactionManagerIdx--) {
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Pair<PlatformTransactionManager, TransactionStatus> transactionManager = transactionManagerAndTransactionStatusList.get(transactionManagerIdx);
                transactionManager.first().commit(transactionManager.second());
            }
        } catch (@Valid @NotNull @org.jetbrains.annotations.NotNull Exception e) {
            // annotation 에 설정된 transaction rollback 역순 실행 및 저장
            for (@Valid @NotNull @org.jetbrains.annotations.NotNull Integer transactionManagerIdx = transactionManagerAndTransactionStatusList.size() - 1; transactionManagerIdx >= 0; transactionManagerIdx--) {
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Pair<PlatformTransactionManager, TransactionStatus> transactionManager = transactionManagerAndTransactionStatusList.get(transactionManagerIdx);
                transactionManager.first().rollback(transactionManager.second());
            }
            throw e;
        }

        return proceed; // 결과 리턴
    }
}
