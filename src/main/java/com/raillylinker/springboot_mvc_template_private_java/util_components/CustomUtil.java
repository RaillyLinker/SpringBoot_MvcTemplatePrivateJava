package com.raillylinker.springboot_mvc_template_private_java.util_components;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipOutputStream;

// [커스텀 유틸 함수 모음]
public interface CustomUtil {
    // (디렉토리 내 파일들을 ZipOutputStream 으로 추가)
    void compressDirectoryToZip(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            File directory,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String path,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ZipOutputStream zipOutputStream
    ) throws IOException;

    // (파일들을 ZipOutputStream 으로 추가)
    void addToZip(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            File file,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileName,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ZipOutputStream zipOutputStream
    ) throws IOException;

    // (zip 파일을 압축 풀기)
    void unzipFile(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String zipFilePath,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Path destDirectory
    ) throws IOException;

    // (랜덤 영문 대소문자 + 숫자 문자열 생성)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String getRandomString(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer length
    );

    // (이메일 적합성 검증)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Boolean isValidEmail(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email
    );

    // (ThymeLeaf 엔진으로 랜더링 한 HTML String 을 반환)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String parseHtmlFileToHtmlString(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String justHtmlFileNameWithOutSuffix,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Map<String, Object> variableDataMap
    );

    // (byteArray 를 Hex String 으로 반환)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String bytesToHex(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            byte[] bytes
    );

    // (degree 를 radian 으로)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Double deg2rad(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double deg
    );

    // (radian 을 degree 로)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Double rad2deg(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double rad
    );
}
