package com.raillylinker.springboot_mvc_template_private_java.aop_aspects;

import com.raillylinker.springboot_mvc_template_private_java.annotations.CustomMongoDbTransactional;
import com.raillylinker.springboot_mvc_template_private_java.data_sources.const_objects.ProjectConst;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.NotNull;
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
    public MongoDbTransactionAnnotationAspect(@NotNull ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private final @NotNull ApplicationContext applicationContext;

    // MongoDb 트랜젝션용 어노테이션인 CustomMongoDbTransactional 파일의 프로젝트 경로
    private static final @NotNull String MONGO_DB_TRANSACTION_ANNOTATION_PATH =
            "@annotation(" + ProjectConst.PACKAGE_NAME + ".annotations.CustomMongoDbTransactional)";


    // ---------------------------------------------------------------------------------------------
    // <AOP 작성 공간>
    // (@CustomMongoDbTransactional 를 입력한 함수 실행 전후에 MongoDB 트랜젝션 적용)
    @Around(MONGO_DB_TRANSACTION_ANNOTATION_PATH)
    public Object aroundMongoDbTransactionAnnotationFunction(@NotNull ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed;

        // transactionManager and transactionStatus 리스트
        @NotNull List<Pair<PlatformTransactionManager, TransactionStatus>> transactionManagerAndTransactionStatusList = new ArrayList<>();

        try {
            // annotation 에 설정된 transaction 순차 실행 및 저장
            @NotNull MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            @NotNull CustomMongoDbTransactional customTransactional = signature.getMethod().getAnnotation(CustomMongoDbTransactional.class);
            for (@NotNull String transactionManagerBeanName : customTransactional.transactionManagerBeanNameList()) {
                // annotation 에 저장된 transactionManager Bean 이름으로 Bean 객체 가져오기
                @NotNull MongoTransactionManager platformTransactionManager = (MongoTransactionManager) applicationContext.getBean(transactionManagerBeanName);

                // transaction 시작 및 정보 저장
                @NotNull TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
                transactionManagerAndTransactionStatusList.add(new Pair<>(platformTransactionManager, transactionStatus));
            }

            // 함수 실행
            proceed = joinPoint.proceed();

            // annotation 에 설정된 transaction commit 역순 실행 및 저장
            for (int transactionManagerIdx = transactionManagerAndTransactionStatusList.size() - 1; transactionManagerIdx >= 0; transactionManagerIdx--) {
                @NotNull Pair<PlatformTransactionManager, TransactionStatus> transactionManager = transactionManagerAndTransactionStatusList.get(transactionManagerIdx);
                transactionManager.first().commit(transactionManager.second());
            }
        } catch (@NotNull Exception e) {
            // annotation 에 설정된 transaction rollback 역순 실행 및 저장
            for (int transactionManagerIdx = transactionManagerAndTransactionStatusList.size() - 1; transactionManagerIdx >= 0; transactionManagerIdx--) {
                @NotNull Pair<PlatformTransactionManager, TransactionStatus> transactionManager = transactionManagerAndTransactionStatusList.get(transactionManagerIdx);
                transactionManager.first().rollback(transactionManager.second());
            }
            throw e;
        }

        return proceed; // 결과 리턴
    }


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>


    // ---------------------------------------------------------------------------------------------
    // <비공개 메소드 공간>


    // ---------------------------------------------------------------------------------------------
    // <중첩 클래스 공간>
    // Pair 클래스 정의
    private record Pair<K, V>(K first, V second) {
    }
}
