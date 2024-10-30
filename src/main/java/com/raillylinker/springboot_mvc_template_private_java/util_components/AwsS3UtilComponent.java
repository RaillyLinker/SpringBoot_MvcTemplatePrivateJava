package com.raillylinker.springboot_mvc_template_private_java.util_components;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

// [AWS S3 유틸]
public interface AwsS3UtilComponent {
    // (S3 로 업로드하는 함수)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String upload(
            // S3 에 저장할 파일 객체
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartFile multipartFile,
            // S3 에 저장할 때 사용할 파일명 (ex : "test.png")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileSaveName,
            // 버킷 위치 (ex : test/my/bucket)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String bucketName
    ) throws IOException;

    // (S3 로 업로드하는 함수)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String upload(
            // S3 에 저장할 파일 객체
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            File file,
            // S3 에 저장할 때 사용할 파일명 (ex : "test.png")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileSaveName,
            // 버킷 위치 (ex : test/my/bucket)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String bucketName
    );

    // (S3 에 저장된 파일을 삭제하는 함수)
    void delete(
            // 버킷 위치 (ex : test/my/bucket)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String bucketName,
            // 삭제할 파일명 (ex : "test.png")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileName
    );

    // (S3 에 저장된 텍스트(Html 포함) 파일의 내용을 읽어오는 함수)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String getTextFileString(
            // 버킷 위치 (ex : test/my/bucket)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String bucketName,
            // 읽을 파일명 (ex : "test.png")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileName
    ) throws IOException;

    // (다운로드 주소로부터 파일을 다운로드하여 S3에 업로드하고 로컬 파일을 삭제하는 함수)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String downloadFileAndUpload(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileUrl,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileSaveName,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String bucketName
    ) throws IOException;
}
