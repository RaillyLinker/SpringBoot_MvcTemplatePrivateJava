package com.raillylinker.springboot_mvc_template_private_java.util_components.impls;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.raillylinker.springboot_mvc_template_private_java.util_components.AwsS3UtilComponent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Component
public class AwsS3UtilComponentImpl implements AwsS3UtilComponent {
    public AwsS3UtilComponentImpl(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            AmazonS3 amazonS3Client
    ) {
        this.amazonS3Client = amazonS3Client;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final AmazonS3 amazonS3Client;


    // (S3 로 업로드하는 함수)
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public String upload(
            // S3 에 저장할 파일 객체
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartFile multipartFile,
            // S3 에 저장할 때 사용할 파일명 (ex : "test.png")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileSaveName,
            // 버킷 위치
            // S3 안에 가장 외곽 버킷은 직접 만들어 보안 설정 등을 거쳐야 합니다.
            // test 라는 버킷을 만들었고, 그 안에 my 디렉토리, 그 안에 bucket 디렉토리 안에 fileSaveName 이름의 파일을 저장하려면,
            // test/my/bucket 이라고 입력하면, 결과적으로 S3 -> test/my/bucket/test.png 라는 경로로 파일이 저장됩니다.
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String bucketName
    ) throws IOException {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        ObjectMetadata objMeta = new ObjectMetadata();
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        String extension = fileSaveName.contains(".") ? fileSaveName.substring(fileSaveName.lastIndexOf('.') + 1).toLowerCase() : "";

        if (Objects.equals(extension, "pdf")) {
            objMeta.setContentType("application/pdf");
        }

        try (var multipartFileInputStream = multipartFile.getInputStream()) {
            objMeta.setContentLength(multipartFileInputStream.available());
            amazonS3Client.putObject(bucketName, fileSaveName, multipartFileInputStream, objMeta);
        }

        return amazonS3Client.getUrl(bucketName, fileSaveName).toString();
    }

    // (S3 로 업로드하는 함수)
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public String upload(
            // S3 에 저장할 파일 객체
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            File file,
            // S3 에 저장할 때 사용할 파일명 (ex : "test.png")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileSaveName,
            // 버킷 위치 (ex : test/my/bucket)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String bucketName
    ) {
        amazonS3Client.putObject(bucketName, fileSaveName, file);
        return amazonS3Client.getUrl(bucketName, fileSaveName).toString();
    }

    // (S3 에 저장된 파일을 삭제하는 함수)
    @Override
    public void delete(
            // 버킷 위치 (ex : test/my/bucket)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String bucketName,
            // 읽을 파일명 (ex : "test.png")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileName
    ) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }

    // (S3 에 저장된 텍스트(Html 포함) 파일의 내용을 읽어오는 함수)
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public String getTextFileString(
            // 버킷 위치 (ex : test/my/bucket)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String bucketName,
            // 읽을 파일명 (ex : "test.png")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileName
    ) throws IOException {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        S3Object s3Object = amazonS3Client.getObject(bucketName, fileName);
        try (var inputStream = s3Object.getObjectContent()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    // (다운로드 주소로부터 파일을 다운로드하여 S3에 업로드하고 로컬 파일을 삭제하는 함수)
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public String downloadFileAndUpload(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileUrl,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileSaveName,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String bucketName
    ) throws IOException {
        Path tempFilePath;
        tempFilePath = Files.createTempFile("temp", ".tmp");
        try (var inputStream = URI.create(fileUrl).toURL().openStream()) {
            Files.copy(inputStream, tempFilePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        File tempFile = tempFilePath.toFile();
        try {
            // 로컬 파일을 S3에 업로드
            return upload(tempFile, fileSaveName, bucketName);
        } finally {
            // 임시 파일 삭제
            tempFile.delete();
        }
    }
}
