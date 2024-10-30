package com.raillylinker.springboot_mvc_template_private_java.configurations;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// [AWS S3 설정]
@Configuration
public class AwsS3Config {
    public AwsS3Config(
            @Value("${cloud.aws.credentials.access-key}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String accessKey,
            @Value("${cloud.aws.credentials.secret-key}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String secretKey,
            @Value("${cloud.aws.region.static}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String region
    ) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String accessKey;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String secretKey;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String region;

    @Bean
    public @Valid @NotNull @org.jetbrains.annotations.NotNull AmazonS3 amazonS3Client() {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }
}
