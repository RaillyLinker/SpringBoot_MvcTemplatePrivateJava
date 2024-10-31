package com.raillylinker.springboot_mvc_template_private_java.services.impls;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raillylinker.springboot_mvc_template_private_java.controllers.C2Service1TkV1RequestTestController;
import com.raillylinker.springboot_mvc_template_private_java.services.C2Service1TkV1RequestTestService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class C2Service1TkV1RequestTestServiceImpl implements C2Service1TkV1RequestTestService {
    public C2Service1TkV1RequestTestServiceImpl(
            @Value("${spring.profiles.active:default}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String activeProfile
    ) {
        this.activeProfile = activeProfile;
    }

    // (프로젝트 실행시 사용 설정한 프로필명 (ex : dev8080, prod80, local8080, 설정 안하면 default 반환))
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String activeProfile;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(this.getClass());

    // (스레드 풀)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @Override
    public String api1BasicRequestTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return activeProfile;
    }

    @Override
    public ModelAndView api2RedirectTest(@org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/service1/tk/v1/request-test");
        return mv;
    }

    @Override
    public ModelAndView api3ForwardTest(@org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("forward:/service1/tk/v1/request-test");
        return mv;
    }

    @Override
    public C2Service1TkV1RequestTestController.Api4GetRequestTestOutputVo api4GetRequestTest(@org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse, @org.jetbrains.annotations.NotNull String queryParamString, String queryParamStringNullable, @org.jetbrains.annotations.NotNull Integer queryParamInt, Integer queryParamIntNullable, @org.jetbrains.annotations.NotNull Double queryParamDouble, Double queryParamDoubleNullable, @org.jetbrains.annotations.NotNull Boolean queryParamBoolean, Boolean queryParamBooleanNullable, @org.jetbrains.annotations.NotNull List<@Valid @NotNull String> queryParamStringList, List<@Valid @NotNull String> queryParamStringListNullable) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new C2Service1TkV1RequestTestController.Api4GetRequestTestOutputVo(
                queryParamString,
                queryParamStringNullable,
                queryParamInt,
                queryParamIntNullable,
                queryParamDouble,
                queryParamDoubleNullable,
                queryParamBoolean,
                queryParamBooleanNullable,
                queryParamStringList,
                queryParamStringListNullable
        );
    }

    @Override
    public C2Service1TkV1RequestTestController.Api5GetRequestTestWithPathParamOutputVo api5GetRequestTestWithPathParam(@org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse, @org.jetbrains.annotations.NotNull Integer pathParamInt) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new C2Service1TkV1RequestTestController.Api5GetRequestTestWithPathParamOutputVo(pathParamInt);
    }

    @Override
    public C2Service1TkV1RequestTestController.Api6PostRequestTestWithApplicationJsonTypeRequestBodyOutputVo api6PostRequestTestWithApplicationJsonTypeRequestBody(@org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse, @org.jetbrains.annotations.NotNull C2Service1TkV1RequestTestController.Api6PostRequestTestWithApplicationJsonTypeRequestBodyInputVo inputVo) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new C2Service1TkV1RequestTestController.Api6PostRequestTestWithApplicationJsonTypeRequestBodyOutputVo(
                inputVo.requestBodyString(),
                inputVo.requestBodyStringNullable(),
                inputVo.requestBodyInt(),
                inputVo.requestBodyIntNullable(),
                inputVo.requestBodyDouble(),
                inputVo.requestBodyDoubleNullable(),
                inputVo.requestBodyBoolean(),
                inputVo.requestBodyBooleanNullable(),
                inputVo.requestBodyStringList(),
                inputVo.requestBodyStringListNullable()
        );
    }

    @Override
    public C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2(@org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse, @org.jetbrains.annotations.NotNull C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2InputVo inputVo) {
        List<C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo> objectList = new ArrayList<>();

        for (C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2InputVo.ObjectVo objectVo : inputVo.objectVoList()) {
            List<C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo.SubObjectVo> subObjectVoList = new ArrayList<>();
            for (C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2InputVo.ObjectVo.SubObjectVo subObject : objectVo.subObjectVoList()) {
                subObjectVoList.add(new C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo.SubObjectVo(
                        subObject.requestBodyString(),
                        subObject.requestBodyStringList()
                ));
            }

            objectList.add(new C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo(
                    objectVo.requestBodyString(),
                    objectVo.requestBodyStringList(),
                    new C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo.SubObjectVo(
                            objectVo.subObjectVo().requestBodyString(),
                            objectVo.subObjectVo().requestBodyStringList()
                    ),
                    subObjectVoList
            ));
        }

        List<C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo.SubObjectVo> subObjectVoList = new ArrayList<>();
        for (C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2InputVo.ObjectVo.SubObjectVo subObject : inputVo.objectVo().subObjectVoList()) {
            subObjectVoList.add(new C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo.SubObjectVo(
                    subObject.requestBodyString(),
                    subObject.requestBodyStringList()
            ));
        }

        C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo outputVo = new C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo(
                new C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo(
                        inputVo.objectVo().requestBodyString(),
                        inputVo.objectVo().requestBodyStringList(),
                        new C2Service1TkV1RequestTestController.Api6Dot1PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo.SubObjectVo(
                                inputVo.objectVo().subObjectVo().requestBodyString(),
                                inputVo.objectVo().subObjectVo().requestBodyStringList()
                        ),
                        subObjectVoList
                ),
                objectList
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return outputVo;
    }

    @Override
    public void api6Dot2PostRequestTestWithNoInputAndOutput(@org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
    }

    @Override
    public C2Service1TkV1RequestTestController.Api7PostRequestTestWithFormTypeRequestBodyOutputVo api7PostRequestTestWithFormTypeRequestBody(@org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse, @org.jetbrains.annotations.NotNull C2Service1TkV1RequestTestController.Api7PostRequestTestWithFormTypeRequestBodyInputVo inputVo) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new C2Service1TkV1RequestTestController.Api7PostRequestTestWithFormTypeRequestBodyOutputVo(
                inputVo.requestFormString(),
                inputVo.requestFormStringNullable(),
                inputVo.requestFormInt(),
                inputVo.requestFormIntNullable(),
                inputVo.requestFormDouble(),
                inputVo.requestFormDoubleNullable(),
                inputVo.requestFormBoolean(),
                inputVo.requestFormBooleanNullable(),
                inputVo.requestFormStringList(),
                inputVo.requestFormStringListNullable()
        );
    }

    @Override
    public C2Service1TkV1RequestTestController.Api8PostRequestTestWithMultipartFormTypeRequestBodyOutputVo api8PostRequestTestWithMultipartFormTypeRequestBody(@org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse, @org.jetbrains.annotations.NotNull C2Service1TkV1RequestTestController.Api8PostRequestTestWithMultipartFormTypeRequestBodyInputVo inputVo) throws IOException {
        // 파일 저장 기본 디렉토리 경로
        Path saveDirectoryPath = Paths.get("./by_product_files/test").toAbsolutePath().normalize();

        // 파일 저장 기본 디렉토리 생성
        Files.createDirectories(saveDirectoryPath);

        // 원본 파일명(with suffix)
        String multiPartFileNameString = inputVo.multipartFile().getOriginalFilename();
        if (multiPartFileNameString == null) {
            throw new IOException("MultipartFile의 원본 파일명이 null입니다.");
        }

        multiPartFileNameString = org.springframework.util.StringUtils.cleanPath(multiPartFileNameString);

        // 파일 확장자 구분 위치
        int fileExtensionSplitIdx = multiPartFileNameString.lastIndexOf('.');

        // 확장자가 없는 파일명 및 확장자
        String fileNameWithOutExtension;
        String fileExtension;

        if (fileExtensionSplitIdx == -1) {
            fileNameWithOutExtension = multiPartFileNameString;
            fileExtension = "";
        } else {
            fileNameWithOutExtension = multiPartFileNameString.substring(0, fileExtensionSplitIdx);
            fileExtension = multiPartFileNameString.substring(fileExtensionSplitIdx + 1);
        }

        // multipartFile을 targetPath에 저장
        inputVo.multipartFile().transferTo(
                saveDirectoryPath.resolve(
                        String.format("%s(%s).%s",
                                fileNameWithOutExtension,
                                LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                                fileExtension
                        )
                ).normalize()
        );

        if (inputVo.multipartFileNullable() != null) {
            // 원본 파일명(with suffix)
            String multiPartFileNullableNameString = inputVo.multipartFileNullable().getOriginalFilename();
            if (multiPartFileNullableNameString == null) {
                throw new IOException("Nullable MultipartFile의 원본 파일명이 null입니다.");
            }

            multiPartFileNullableNameString = org.springframework.util.StringUtils.cleanPath(multiPartFileNullableNameString);

            // 파일 확장자 구분 위치
            int nullableFileExtensionSplitIdx = multiPartFileNullableNameString.lastIndexOf('.');

            // 확장자가 없는 파일명 및 확장자
            String nullableFileNameWithOutExtension;
            String nullableFileExtension;

            if (nullableFileExtensionSplitIdx == -1) {
                nullableFileNameWithOutExtension = multiPartFileNullableNameString;
                nullableFileExtension = "";
            } else {
                nullableFileNameWithOutExtension = multiPartFileNullableNameString.substring(0, nullableFileExtensionSplitIdx);
                nullableFileExtension = multiPartFileNullableNameString.substring(nullableFileExtensionSplitIdx + 1);
            }

            // multipartFile을 targetPath에 저장
            inputVo.multipartFileNullable().transferTo(
                    saveDirectoryPath.resolve(
                            String.format("%s(%s).%s",
                                    nullableFileNameWithOutExtension,
                                    LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                                    nullableFileExtension
                            )
                    ).normalize()
            );
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new C2Service1TkV1RequestTestController.Api8PostRequestTestWithMultipartFormTypeRequestBodyOutputVo(
                inputVo.requestFormString(),
                inputVo.requestFormStringNullable(),
                inputVo.requestFormInt(),
                inputVo.requestFormIntNullable(),
                inputVo.requestFormDouble(),
                inputVo.requestFormDoubleNullable(),
                inputVo.requestFormBoolean(),
                inputVo.requestFormBooleanNullable(),
                inputVo.requestFormStringList(),
                inputVo.requestFormStringListNullable()
        );
    }

}