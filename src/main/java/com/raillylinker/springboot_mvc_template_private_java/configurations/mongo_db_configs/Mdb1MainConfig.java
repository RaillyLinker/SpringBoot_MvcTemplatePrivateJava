package com.raillylinker.springboot_mvc_template_private_java.configurations.mongo_db_configs;

import com.raillylinker.springboot_mvc_template_private_java.data_sources.const_objects.ProjectConst;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = ProjectConst.PACKAGE_NAME + ".data_sources.mongo_db_beans." + Mdb1MainConfig.MONGO_DB_DIRECTORY_NAME + ".repositories",
        mongoTemplateRef = Mdb1MainConfig.MONGO_DB_DIRECTORY_NAME
)
public class Mdb1MainConfig {
    // !!!application.yml 의 datasource-mongodb 안에 작성된 이름 할당하기!!!
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static final String MONGO_DB_CONFIG_NAME = "mdb1-main";

    // !!!data_sources/mongo_db_sources 안의 서브 폴더(documents, repositories 를 가진 폴더)의 이름 할당하기!!!
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static final String MONGO_DB_DIRECTORY_NAME = "mdb1_main";

    // Database 트랜젝션 이름 변수
    // 트랜젝션을 적용할 함수 위에, @CustomMongoDbTransactional 어노테이션과 결합하여,
    // @CustomMongoDbTransactional([MongoDbConfig.TRANSACTION_NAME])
    // 위와 같이 적용하세요.
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static final String TRANSACTION_NAME = MONGO_DB_DIRECTORY_NAME + "_PlatformTransactionManager";

    // ---------------------------------------------------------------------------------------------
    @Value("${datasource-mongodb." + MONGO_DB_CONFIG_NAME + ".uri}")
    private String mongoDbUri;

    private SimpleMongoClientDatabaseFactory mongoClientFactory;

    @PostConstruct
    public void init() {
        mongoClientFactory = new SimpleMongoClientDatabaseFactory(mongoDbUri);
    }

    @Bean(name = MONGO_DB_DIRECTORY_NAME)
    public @Valid @NotNull @org.jetbrains.annotations.NotNull MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClientFactory);
    }

    @Bean(TRANSACTION_NAME)
    public @Valid @NotNull @org.jetbrains.annotations.NotNull MongoTransactionManager customTransactionManager() {
        return new MongoTransactionManager(mongoClientFactory);
    }
}