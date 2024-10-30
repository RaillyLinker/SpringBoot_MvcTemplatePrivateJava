package com.raillylinker.springboot_mvc_template_private_java.util_components.impls;

import com.raillylinker.springboot_mvc_template_private_java.util_components.CustomUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

// [커스텀 유틸 함수 모음]
@Component
public class CustomUtilImpl implements CustomUtil {
    // (디렉토리 내 파일들을 ZipOutputStream 으로 추가)
    @Override
    public void compressDirectoryToZip(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            File directory,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String path,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ZipOutputStream zipOutputStream
    ) throws IOException {
        File[] listFiles = directory.listFiles();

        for (@Valid @NotNull @org.jetbrains.annotations.NotNull File file : listFiles != null ? listFiles : new File[0]) {
            if (file.isDirectory()) {
                compressDirectoryToZip(file, path + "/" + file.getName(), zipOutputStream);
            } else {
                addToZip(file, path + "/" + file.getName(), zipOutputStream);
            }
        }
    }

    // (파일들을 ZipOutputStream 으로 추가)
    @Override
    public void addToZip(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            File file,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileName,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ZipOutputStream zipOutputStream
    ) throws IOException {
        try (@Valid @NotNull @org.jetbrains.annotations.NotNull FileInputStream fileInputStream = new FileInputStream(file)) {
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOutputStream.putNextEntry(zipEntry);
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) > 0) {
                zipOutputStream.write(buffer, 0, length);
            }
            zipOutputStream.closeEntry();
        }
    }

    // (zip 파일을 압축 풀기)
    @Override
    public void unzipFile(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String zipFilePath,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Path destDirectory
    ) throws IOException {
        try (@Valid @NotNull @org.jetbrains.annotations.NotNull FileInputStream fileInputStream = new FileInputStream(zipFilePath);
             @Valid @NotNull @org.jetbrains.annotations.NotNull
             ZipInputStream zipInputStream = new ZipInputStream(fileInputStream)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                File newFile = destDirectory.resolve(entry.getName()).toFile();
                if (entry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    newFile.getParentFile().mkdirs();
                    try (@Valid @NotNull @org.jetbrains.annotations.NotNull FileOutputStream fos = new FileOutputStream(newFile)) {
                        @Valid @NotNull @org.jetbrains.annotations.NotNull
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
                zipInputStream.closeEntry();
            }
        }
    }

    // (랜덤 영문 대소문자 + 숫자 문자열 생성)
    @Override
    public @Valid @NotNull @org.jetbrains.annotations.NotNull String getRandomString(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer length
    ) {
        StringBuilder charset = new StringBuilder();
        for (char c = 'a'; c <= 'z'; c++) charset.append(c);
        for (char c = 'A'; c <= 'Z'; c++) charset.append(c);
        for (char c = '0'; c <= '9'; c++) charset.append(c);

        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * charset.length());
            randomString.append(charset.charAt(randomIndex));
        }
        return randomString.toString();
    }

    // (이메일 적합성 검증)
    @Override
    public @Valid @NotNull @org.jetbrains.annotations.NotNull Boolean isValidEmail(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email
    ) {
        return Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$").matcher(email).matches();
    }

    // (ThymeLeaf 엔진으로 랜더링 한 HTML String 을 반환)
    @Override
    public @Valid @NotNull @org.jetbrains.annotations.NotNull String parseHtmlFileToHtmlString(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String justHtmlFileNameWithOutSuffix,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Map<String, Object> variableDataMap
    ) {
        // 타임리프 resolver 설정
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/"); // static/templates 경로 아래에 있는 파일을 읽는다
        templateResolver.setSuffix(".html"); // .html로 끝나는 파일을 읽는다
        templateResolver.setTemplateMode(TemplateMode.HTML); // 템플릿은 html 형식

        // 스프링 template 엔진을 thymeleafResolver 를 사용하도록 설정
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        // 템플릿 엔진에서 사용될 변수 입력
        Context context = new Context();
        context.setVariables(variableDataMap);

        // 지정한 html 파일과 context 를 읽어 String 으로 반환
        return templateEngine.process(justHtmlFileNameWithOutSuffix, context);
    }

    // (byteArray 를 Hex String 으로 반환)
    @Override
    public @Valid @NotNull @org.jetbrains.annotations.NotNull String bytesToHex(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            byte[] bytes
    ) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    // (degree 를 radian 으로)
    @Override
    public @Valid @NotNull @org.jetbrains.annotations.NotNull Double deg2rad(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double deg
    ) {
        return deg * Math.PI / 180.0;
    }

    // (radian 을 degree 로)
    @Override
    public @Valid @NotNull @org.jetbrains.annotations.NotNull Double rad2deg(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double rad
    ) {
        return rad * 180 / Math.PI;
    }
}